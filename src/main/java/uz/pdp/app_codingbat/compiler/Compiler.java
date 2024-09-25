package uz.pdp.app_codingbat.compiler;

import uz.pdp.app_codingbat.payload.compile.CaseDTO;
import uz.pdp.app_codingbat.payload.compile.CompilerResult;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.*;

public class Compiler {

    public static List<CompilerResult> compileCode(String userSolutionCode, String problemName, List<CaseDTO> caseList) {
        String classNameA = "UserSolution";
        String classNameB = "Test";

        String userCode = "public class UserSolution {\n" + userSolutionCode + "\n}";
        List<CompilerResult> results = new ArrayList<>();

        String returnType = getMethodReturnType(userSolutionCode);

        for (CaseDTO testCase : caseList) {
            String testCode =
                    "public class Test {\n" +
                            "    public static " + returnType + " runTest() {\n" +
                            "        UserSolution userSolution = new UserSolution();\n" +
                            "        return userSolution." + problemName + "(" + testCase.getArgs() + ");\n" +
                            "    }\n" +
                            "}";

            RuntimeCompiler runtimeCompiler = new RuntimeCompiler();
            runtimeCompiler.addClass(classNameA, userCode);
            runtimeCompiler.addClass(classNameB, testCode);

            String compilationResult = runtimeCompiler.compile();
            if (!"true".equals(compilationResult)) {
                throw new IllegalArgumentException(compilationResult);
            }

            try {
                Object result = MethodInvocationUtils.invokeStaticMethod(runtimeCompiler.getCompiledClass(classNameB), "runTest");
                boolean isCorrect = Objects.equals(result.toString(), testCase.getExpected());
                results.add(new CompilerResult(testCase, problemName, result.toString(), isCorrect));
            } catch (RuntimeException e) {
                results.add(new CompilerResult(testCase, problemName, e.getCause().getCause().toString(), false));
            }
        }
        return results;
    }

    private static String getMethodReturnType(String methodSignature) {
        int index = methodSignature.indexOf("public");
        return methodSignature.substring(index + 7, methodSignature.indexOf(' ', index + 8)).trim();
    }

    static class RuntimeCompiler {
        private final JavaCompiler javaCompiler;
        private final Map<String, byte[]> compiledClasses;
        private final MapClassLoader mapClassLoader;
        private final ClassDataFileManager classDataFileManager;
        private final List<JavaFileObject> compilationUnits;

        public RuntimeCompiler() {
            this.javaCompiler = ToolProvider.getSystemJavaCompiler();
            if (javaCompiler == null) {
                throw new NullPointerException("Java Compiler not found. Ensure you're running with a JDK.");
            }
            this.compiledClasses = new LinkedHashMap<>();
            this.mapClassLoader = new MapClassLoader();
            this.classDataFileManager = new ClassDataFileManager(javaCompiler.getStandardFileManager(null, null, null));
            this.compilationUnits = new ArrayList<>();
        }

        public void addClass(String className, String sourceCode) {
            JavaFileObject javaFileObject = new MemoryJavaSourceFileObject(className + ".java", sourceCode);
            compiledClasses.put(className, className.getBytes());
            compilationUnits.add(javaFileObject);
        }

        public String compile() {
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            JavaCompiler.CompilationTask task = javaCompiler.getTask(null, classDataFileManager, diagnostics, null, null, compilationUnits);

            boolean success = task.call();
            compilationUnits.clear();

            if (success) {
                return "true";
            } else {
                StringBuilder errorMessage = new StringBuilder();
                for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                    errorMessage.append(diagnostic.getMessage(null))
                            .append(" (Line ").append(diagnostic.getLineNumber() - 1).append(")\n");
                }
                return errorMessage.toString();
            }
        }

        public Class<?> getCompiledClass(String className) {
            return mapClassLoader.findClass(className);
        }

        private class MapClassLoader extends ClassLoader {
            @Override
            protected Class<?> findClass(String name) {
                byte[] classData = compiledClasses.get(name);
                return defineClass(name, classData, 0, classData.length);
            }
        }

        private class ClassDataFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
            public ClassDataFileManager(StandardJavaFileManager standardFileManager) {
                super(standardFileManager);
            }

            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
                return new MemoryJavaClassFileObject(className);
            }
        }

        private static class MemoryJavaSourceFileObject extends SimpleJavaFileObject {
            private final String code;

            public MemoryJavaSourceFileObject(String fileName, String code) {
                super(URI.create("string:///" + fileName), Kind.SOURCE);
                this.code = code;
            }

            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return code;
            }
        }

        private class MemoryJavaClassFileObject extends SimpleJavaFileObject {
            private final String className;

            public MemoryJavaClassFileObject(String className) {
                super(URI.create("string:///" + className + ".class"), Kind.CLASS);
                this.className = className;
            }

            @Override
            public OutputStream openOutputStream() {
                return new ClassDataOutputStream(className);
            }
        }

        private class ClassDataOutputStream extends OutputStream {
            private final String className;
            private final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            public ClassDataOutputStream(String className) {
                this.className = className;
            }

            @Override
            public void write(int b) {
                byteStream.write(b);
            }

            @Override
            public void close() {
                compiledClasses.put(className, byteStream.toByteArray());
            }
        }
    }

    static class MethodInvocationUtils {
        public static Object invokeStaticMethod(Class<?> clazz, String methodName, Object... args) {
            try {
                Method method = findMatchingStaticMethod(clazz, methodName, args);
                if (method != null) {
                    return method.invoke(null, args);
                }
                throw new NoSuchMethodException("No matching static method found: " + methodName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private static Method findMatchingStaticMethod(Class<?> clazz, String methodName, Object[] args) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(methodName) && Modifier.isStatic(method.getModifiers())
                        && areParametersMatching(method.getParameterTypes(), args)) {
                    return method;
                }
            }
            return null;
        }

        private static boolean areParametersMatching(Class<?>[] parameterTypes, Object[] args) {
            if (parameterTypes.length != args.length) {
                return false;
            }
            for (int i = 0; i < parameterTypes.length; i++) {
                if (args[i] != null && !parameterTypes[i].isAssignableFrom(args[i].getClass())) {
                    return false;
                }
            }
            return true;
        }
    }
}
