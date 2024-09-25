package uz.pdp.app_codingbat.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.app_codingbat.entity.*;
import uz.pdp.app_codingbat.entity.enums.Permission;
import uz.pdp.app_codingbat.entity.enums.UserStatus;
import uz.pdp.app_codingbat.repository.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProblemRepository problemRepository;
    private final LanguageRepository languageRepository;
    private final CategoryRepository categoryRepository;
    private final CaseRepository caseRepository;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        if (Objects.equals(ddl, "create")) {
            initRoles();
            initAdmin();
            initUser();
            loadCategoryWarmUp1();
            loadCategoryWarmUp2();
            loadCategoryString1();
            loadCategoryLogic1();
        } else if (isDBEmpty()) {
            initRoles();
            initAdmin();
            initUser();
            loadCategoryWarmUp1();
            loadCategoryWarmUp2();
            loadCategoryString1();
            loadCategoryLogic1();
        }

        System.out.println("\n\nApplication started.............");
    }

    private boolean isDBEmpty() {
        return roleRepository.findAll().isEmpty();
    }

    private void initAdmin() {
        User user = new User(
                "admin123@gmail.com",
                passwordEncoder.encode("admin123"),
                roleRepository.findByName("ADMIN").orElseThrow(),
                UserStatus.ACTIVE,
                null
        );
        userRepository.save(user);

        System.out.println("Admin user loaded ...");
    }

    private void initUser() {
        User user = new User(
                "user123@gmail.com",
                passwordEncoder.encode("user123"),
                roleRepository.findByName("USER").orElseThrow(),
                UserStatus.ACTIVE,
                null
        );
        userRepository.save(user);

        System.out.println("Admin user loaded ...");
    }

    private void initRoles() {
        Role admin = Role.builder()
                .name("ADMIN")
                .permissions(Set.of(Permission.values()))
                .build();

        Role user = Role.builder()
                .name("USER")
                .permissions(
                        Set.of(
                                Permission.GET_LANGUAGE,
                                Permission.GET_CATEGORY,
                                Permission.READ_ME,
                                Permission.GET_PROBLEM,
                                Permission.GET_ROLE,
                                Permission.GET_SUBMISSION,
                                Permission.GET_CASE,
                                Permission.UPDATE_USER
                        )
                )
                .build();

        roleRepository.saveAll(List.of(admin, user));

        System.out.println("User Roles loaded ...");
    }

    private void loadCategoryWarmUp1() {
        Language java = Language.builder().name("JAVA").build();

        languageRepository.save(java);

        Category warmup2 = Category.builder().language(java).name("WarmUp-1").description("Simple warmup problems to get started (solutions available).").maxStars(3).build();

        categoryRepository.save(warmup2);

        Problem p1 = Problem.builder().name("sleepIn").questionTitle("The parameter weekday is true if it is a weekday, and the parameter vacation is true if we are on vacation. We sleep in if it is not a weekday or we're on vacation. Return true if we sleep in.").exampleCase("""
                sleepIn(false, false) → true
                sleepIn(true, false) → false
                sleepIn(false, true) → true""").questionCode("""
                public boolean sleepIn(boolean weekday, boolean vacation) {
                }
                """).category(warmup2).build();

        problemRepository.save(p1);

        caseLoader(p1, "false, false", "true");
        caseLoader(p1, "true, false", "false");
        caseLoader(p1, "false, true", "true");
        caseLoader(p1, "true, true", "true");


        categoryRepository.save(warmup2);


        Problem p2 = Problem.builder().name("monkeyTrouble").questionTitle("We have two monkeys, a and b, and the parameters aSmile and bSmile indicate if each is smiling. We are in trouble if they are both smiling or if neither of them is smiling. Return true if we are in trouble.").exampleCase("""
                monkeyTrouble(true, true) → true
                monkeyTrouble(false, false) → true
                monkeyTrouble(true, false) → false""").questionCode("""
                public boolean monkeyTrouble(boolean as, boolean bs) {
                 \s
                }
                """).category(warmup2).build();

        problemRepository.save(p2);

        caseLoader(p2, "true, true", "true");
        caseLoader(p2, "false, false", "true");
        caseLoader(p2, "true, false", "false");
        caseLoader(p2, "false, true", "false");


        Problem p3 = Problem.builder().name("sumDouble").questionTitle("\n" + "Given two int values, return their sum. Unless the two values are the same, then return double their sum.").exampleCase("""
                sumDouble(1, 2) → 3
                sumDouble(3, 2) → 5
                sumDouble(2, 2) → 8""").questionCode("""
                public int sumDouble(int a, int b) {
                }
                """).category(warmup2).build();

        problemRepository.save(p3);

        caseLoader(p3, "1, 2", "3");
        caseLoader(p3, "3, 2", "5");
        caseLoader(p3, "2, 2", "8");
        caseLoader(p3, "-1, 0", "-1");
        caseLoader(p3, "3, 3", "12");
        caseLoader(p3, "0, 0", "0");
        caseLoader(p3, "0, 1", "1");
        caseLoader(p3, "3, 4", "7");


        System.out.println("Category Warm Up 1 data loaded ...");
    }

    private void loadCategoryWarmUp2() {
        Language language = languageRepository.findByName("JAVA").orElseThrow();


        Category warmup2 = Category.builder().language(language).name("WarmUp-2").description("Medium warmup string/array loops (solutions available)").maxStars(3).build();

        categoryRepository.save(warmup2);

        Problem p1 = Problem.builder().name("stringTimes").questionTitle("Given a string and a non-negative int n, return a larger string that is n copies of the original string.").exampleCase("""
                stringTimes("Hi", 2) → "HiHi"
                stringTimes("Hi", 3) → "HiHiHi"
                stringTimes("Hi", 1) → "Hi\"""").questionCode("""
                public String stringTimes(String str, int n) {
                }
                """).category(warmup2).build();

        problemRepository.save(p1);

        caseLoader(p1, "\"Hi\", 2", "\"HiHi\"");
        caseLoader(p1, "\"Hi\", 3", "\"HiHiHi\"");
        caseLoader(p1, "\"Hi\", 1", "\"Hi\"");
        caseLoader(p1, "\"Hi\", 0", "\"\"");
        caseLoader(p1, "\"Hi\", 5", "\"HiHiHiHiHi\"");
        caseLoader(p1, "\"Oh Boy!\", 2", "\"Oh Boy!Oh Boy!\"");
        caseLoader(p1, "\"x\", 4", "\"xxxx\"");
        caseLoader(p1, "\"code\", 2", "\"codecode\"");
        caseLoader(p1, "\"code\", 3", "\"codecodecode\"");

        Problem p2 = Problem.builder().name("frontTimes").questionTitle("""

                We have two monkeys, a and b, and the parameters aSmile and bSmile indicate if each is smiling. We are in trouble if they are both smiling or if neither of them is smiling. Return true if we are in trouble.

                """).exampleCase("""
                frontTimes(true, true) → true
                frontTimes(false, false) → true
                frontTimes(true, false) → false""").questionCode("""
                public boolean frontTimes(boolean aSmile, boolean bSmile) {
                 \s
                }
                """).category(warmup2).build();

        problemRepository.save(p2);

        caseLoader(p2, "\"Chocolate\", 2", "\"ChoCho\"");
        caseLoader(p2, "\"Chocolate\", 3", "\"ChoChoCho\"");
        caseLoader(p2, "\"Abc\", 3", "\"AbcAbcAbc\"");
        caseLoader(p2, "\"Ab\", 4", "\"AbAbAbAb\"");
        caseLoader(p2, "\"A\", 4", "\"AAAA\"");
        caseLoader(p2, "\"\", 2", "\"\"");
        caseLoader(p2, "\"Abc\", 0", "\"\"");


        Problem p3 = Problem.builder()
                .name("countXX")
                .questionTitle("\n" + "Count the number of \"xx\" in the given string. We'll say that overlapping is allowed, so \"xxx\" contains 2 \"xx\".")
                .exampleCase("""
                        countXX("abcxx") → 1
                        countXX("xxx") → 2
                        countXX("xxxx") → 3""")
                .category(warmup2)
                .questionCode("int countXX(String str) {\n" +
                        "  \n" +
                        "}\n")
                .build();

        problemRepository.save(p3);

        caseLoader(p3, "\"abcxx\"", "1");
        caseLoader(p3, "\"xxx\"", "2");
        caseLoader(p3, "\"xxxx\"", "3");
        caseLoader(p3, "\"abc\"", "0");
        caseLoader(p3, "\"Hello there\"", "0");
        caseLoader(p3, "\"Hexxo thxxe\"", "2");
        caseLoader(p3, "\"\"", "0");
        caseLoader(p3, "\"Kittens\"", "0");
        caseLoader(p3, "\"Kittensxxx\"", "2");


        System.out.println("Category Warm Up 2 data loaded ...");
    }

    private void loadCategoryString1() {
        Language language = languageRepository.findByName("JAVA").orElseThrow();
        Category string1 = Category.builder().language(language).name("string1").description("Basic string problems -- no loops. Use + to combine Strings, str.length() is the number of chars in a String, str.substring(i, j) extracts the substring starting at index i and running up to but not including index j").maxStars(3).build();

        categoryRepository.save(string1);

        Problem problem1 = Problem.builder()
                .name("helloName")
                .questionTitle("Given a string name, e.g. \"Bob\", return a greeting of the form \"Hello Bob!\".")
                .exampleCase("""
                        helloName("Bob") → "Hello Bob!"
                        helloName("Alice") → "Hello Alice!"
                        helloName("X") → "Hello X!\"""")
                .questionCode("""
                        public String helloName(String name) {
                         \s
                        }
                        """).category(string1).build();

        problemRepository.save(problem1);

        caseLoader(problem1, "\"Bob\"", "\"Hello Bob!\"");
        caseLoader(problem1, "\"Alice\"", "\"Hello Alice!\"");
        caseLoader(problem1, "\"X\"", "\"Hello X!\"");
        caseLoader(problem1, "\"Dolly\"", "\"Hello Dolly!\"");
        caseLoader(problem1, "\"Alpha\"", "\"Hello Alpha!\"");
        caseLoader(problem1, "\"Omega\"", "\"Hello Omega!\"");
        caseLoader(problem1, "\"GoodBye\"", "\"Hello GoodBye!\"");
        caseLoader(problem1, "\"ho ho ho\"", "\"Hello ho ho ho!\"");
        caseLoader(problem1, "\"xyz\"", "\"Hello xyz!\"");
        caseLoader(problem1, "\"Hello\"", "\"Hello Hello!\"");

        Problem problem2 = Problem.builder()
                .name("makeAbba")
                .questionTitle("Given two strings, a and b, return the result of putting them together in the order abba, e.g. \"Hi\" and \"Bye\" returns \"HiByeByeHi\".")
                .exampleCase("""
                        makeAbba("Hi", "Bye") → "HiByeByeHi"
                        makeAbba("Yo", "Alice") → "YoAliceAliceYo"
                        makeAbba("What", "Up") → "WhatUpUpWhat\"""")
                .questionCode("""
                        public String makeAbba(String a, String b) {
                         \s
                        }
                        """).category(string1).build();

        problemRepository.save(problem2);

        caseLoader(problem2, "\"Hi\", \"Bye\"", "\"HiByeByeHi\"");
        caseLoader(problem2, "\"You\", \"Alice\"", "\"YouAliceAliceYou\"");
        caseLoader(problem2, "\"What\", \"Up\"", "\"WhatUpUpWhat\"");
        caseLoader(problem2, "\"aaa\", \"bbb\"", "\"aaabbbbbbaaa\"");
        caseLoader(problem2, "\"x\", \"y\"", "\"xyyx\"");
        caseLoader(problem2, "\"x\", \"\"", "\"xx\"");
        caseLoader(problem2, "\"\", \"y\"", "\"yy\"");
        caseLoader(problem2, "\"Bo\", \"Ya\"", "\"BoYaYaBo\"");
        caseLoader(problem2, "\"Ya\", \"Ya\"", "\"YaYaYaYa\"");

        Problem problem3 = Problem.builder()
                .name("makeTags")
                .questionTitle("""
                        The web is built with HTML strings like "<i>Yay</i>" which draws Yay as italic text. In this example, the "i" tag makes <i> and </i> which surround the word "Yay". Given tag and word strings, create the HTML string with tags around the word, e.g. "<i>Yay</i>".

                        """)
                .exampleCase("""
                        makeTags("i", "Yay") → "<i>Yay</i>"
                        makeTags("i", "Hello") → "<i>Hello</i>"
                        makeTags("cite", "Yay") → "<cite>Yay</cite>"
                        """)
                .questionCode("""
                        public String makeTags(String tag, String word) {
                         \s
                        }""")
                .category(string1)
                .build();

        problemRepository.save(problem3);

        caseLoader(problem3, "\"i\", \"Yay\"", "\"<i>Yay</i>\"");
        caseLoader(problem3, "\"i\", \"Hello\"", "\"<i>Hello</i>\"");
        caseLoader(problem3, "\"cite\", \"Yay\"", "\"<cite>Yay</cite>\"");
        caseLoader(problem3, "\"address\", \"here\"", "\"<address>here</address>\"");
        caseLoader(problem3, "\"body\", \"Heart\"", "\"<body>Heart</body>\"");
        caseLoader(problem3, "\"i\", \"i\"", "\"<i>i</i>\"");
        caseLoader(problem3, "\"i\", \"\"", "\"<i></i>\"");

        System.out.println("Category String 1 data loaded ...");
    }

    private void loadCategoryLogic1() {
        Language language = languageRepository.findByName("JAVA").orElseThrow();
        Category logic1 = Category.builder()
                .language(language)
                .name("logic1")
                .description("Basic boolean logic puzzles -- if else && || !. ")
                .maxStars(3)
                .build();

        categoryRepository.save(logic1);

        Problem problem1 = Problem.builder()
                .name("cigarParty")
                .questionTitle("""
                        When squirrels get together for a party, they like to have cigars. A squirrel party is successful when the number of cigars is between 40 and 60, inclusive. Unless it is the weekend, in which case there is no upper bound on the number of cigars. Return true if the party with the given values is successful, or false otherwise.

                        """)
                .exampleCase("""
                        cigarParty(30, false) → false
                        cigarParty(50, false) → true
                        cigarParty(70, true) → true
                        """)
                .questionCode("""
                        public boolean cigarParty(int cigars, boolean isWeekend) {

                        }""")
                .category(logic1)
                .build();
        problemRepository.save(problem1);

        caseLoader(problem1, "30, false", "false");
        caseLoader(problem1, "50, false", "true");
        caseLoader(problem1, "70, true", "true");
        caseLoader(problem1, "30, true", "false");
        caseLoader(problem1, "50, true", "true");
        caseLoader(problem1, "60, false", "true");
        caseLoader(problem1, "61, false", "false");
        caseLoader(problem1, "40, false", "true");
        caseLoader(problem1, "39, false", "false");
        caseLoader(problem1, "40, true", "true");
        caseLoader(problem1, "39, true", "false");


        Problem problem2 = Problem.builder()
                .name("dateFashion")
                .questionTitle("You and your date are trying to get a table at a restaurant. The parameter \"you\" is the stylishness of your clothes, in the range 0..10, and \"date\" is the stylishness of your date's clothes. The result getting the table is encoded as an int value with 0=no, 1=maybe, 2=yes. If either of you is very stylish, 8 or more, then the result is 2 (yes). With the exception that if either of you has style of 2 or less, then the result is 0 (no). Otherwise the result is 1 (maybe).")
                .exampleCase("""
                        dateFashion(5, 10) → 2
                        dateFashion(5, 2) → 0
                        dateFashion(5, 5) → 1""")
                .questionCode("""
                        public int dateFashion(int you, int date) {

                        }""")
                .category(logic1)
                .build();

        problemRepository.save(problem2);

        caseLoader(problem2, "5, 10", "2");
        caseLoader(problem2, "5, 2", "0");
        caseLoader(problem2, "5, 5", "1");
        caseLoader(problem2, "3, 3", "1");
        caseLoader(problem2, "10, 2", "0");
        caseLoader(problem2, "2, 9", "0");
        caseLoader(problem2, "9, 9", "2");
        caseLoader(problem2, "10, 5", "2");
        caseLoader(problem2, "2, 2", "0");
        caseLoader(problem2, "3, 7", "1");
        caseLoader(problem2, "2, 7", "0");
        caseLoader(problem2, "6, 2", "0");

        Problem problem3 = Problem.builder()
                .name("squirrelPlay")
                .questionTitle("""
                        The squirrels in Palo Alto spend most of the day playing. In particular, they play if the temperature is between 60 and 90 (inclusive). Unless it is summer, then the upper limit is 100 instead of 90. Given an int temperature and a boolean isSummer, return true if the squirrels play and false otherwise.

                        """)
                .exampleCase("""
                        squirrelPlay(70, false) → true
                        squirrelPlay(95, false) → false
                        squirrelPlay(95, true) → true
                        """)
                .questionCode("""
                        public boolean squirrelPlay(int temp, boolean isSummer) {

                        }""")
                .category(logic1)
                .build();

        problemRepository.save(problem3);

        caseLoader(problem3, "70, false", "true");
        caseLoader(problem3, "95, false", "false");
        caseLoader(problem3, "95, true", "true");
        caseLoader(problem3, "90, false", "true");
        caseLoader(problem3, "90, true", "true");
        caseLoader(problem3, "50, false", "false");
        caseLoader(problem3, "50, true", "false");
        caseLoader(problem3, "100, false", "false");
        caseLoader(problem3, "100, true", "true");
        caseLoader(problem3, "105, true", "false");
        caseLoader(problem3, "59, true", "false");
        caseLoader(problem3, "59, false", "false");
        caseLoader(problem3, "59, false", "true");


        System.out.println("Category Logic 1 data loaded ...");
    }


    private void caseLoader(Problem problem, String args, String expected) {
        Case case1 = Case.builder().problem(problem).visible(true).expected(expected).args(args).build();
        caseRepository.save(case1);
    }

}
