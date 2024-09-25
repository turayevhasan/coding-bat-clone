package uz.pdp.app_codingbat.config.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import uz.pdp.app_codingbat.config.core.GlobalVar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Logger {

    // 2022-09-01 20:20:01.234
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    // TYPE >>>> [DATE TIME]::[MODE::SESSION]::[METHOD(URL)]::[HEADER]::[BODY]
    // REQ >>>> [2022-01-09 20:20:00.234]::[int::uuid]::[POST(/api/v1/card)]::[HEADER]::[BODY]
    private static final String reqFormat = "REQ >>>>>>>>>>>>>>>> [%s]::[%s::%s]::[%s(%s)]::[%s]::[%s]";

    // TYPE <<<< [DATE TIME]::[MODE::SESSION]::[STATUS(DELAY)]::[HEADER]::[BODY]
    // RES <<<< [2022-09-01 20:20:01.234]::[ext::uuid]::[200(145ms)]::[HEADER]::[BODY]
    private static final String resFormat = "RES <<<<<<<<<<<<<<<< [%s]::[%s::%s]::[%s(%sms)]::[%s]::[%s]";

    // TYPE ---- [DATE TIME]::[MODE::SESSION]::[MESSAGE]
    // ERR ---- [2022-09-01 20:20:00.987]::[gen::uuid]::[MESSAGE]
    private static final String errFormat = "ERR ---------------- [%s]::[%s::%s]::[%s]";

    // TYPE **** [DATE TIME]::[MODE::SESSION]::[MESSAGE]
    // INF **** [2022-09-01 20:20:00.987]::[gen::uuid]::[MESSAGE]
    private static final String infFormat = "INF **************** [%s]::[%s::%s]::[%s]";


    public static void reqInt(
            LocalDateTime dateTime,
            String requestID,
            HttpMethod method,
            String url,
            HttpHeaders headers,
            String body
    ) {
        reqInfo(
                dateTime,
                requestID,
                method,
                url,
                headers.toString(),
                body
        );
    }

    public static void resInt(
            LocalDateTime dateTime,
            String requestID,
            Integer status,
            Long delay,
            HttpHeaders headers,
            String body
    ) {
        resInfo(
                dateTime,
                requestID,
                status,
                delay,
                headers.toString(),
                body
        );
    }

    public static void info(
            String requestID,
            String message
    ) {
        info(
                LocalDateTime.now(),
                requestID,
                message
        );
    }

    public static void info(
            String message
    ) {
        info(
                LocalDateTime.now(),
                GlobalVar.getRequestId(),
                message
        );
    }

    public static void info(
            String message,
            Object... args
    ) {
        info(
                LocalDateTime.now(),
                GlobalVar.getRequestId(),
                message,
                args
        );
    }

    public static void error(
            Throwable e
    ) {
        exception(
                LocalDateTime.now(),
                GlobalVar.getRequestId(),
                e
        );
    }

    public static void error(
            String message,
            Throwable e
    ) {
        exception(
                LocalDateTime.now(),
                GlobalVar.getRequestId(),
                message,
                e
        );
    }

    public static void error(
            Exception e,
            String requestID
    ) {
        exception(
                LocalDateTime.now(),
                requestID,
                e
        );
    }

    public static void error(
            String message,
            Exception e,
            String requestID
    ) {
        exception(
                LocalDateTime.now(),
                requestID,
                message,
                e
        );
    }

    public static void error(
            Throwable e,
            String requestID
    ) {
        exception(
                LocalDateTime.now(),
                requestID,
                e
        );
    }

    public static void error(
            String requestID,
            String message,
            Throwable e
    ) {
        exception(
                LocalDateTime.now(),
                requestID,
                message,
                e
        );
    }


    private static void reqInfo(
            LocalDateTime dateTime,
            String requestId,
            HttpMethod method,
            String url,
            String headers,
            String body
    ) {
        log.info(
                String.format(
                        reqFormat,
                        dateFormat.format(dateTime),
                        LogMode.GEN,
                        requestId,
                        method.name(),
                        url,
                        headers,
                        body
                )
        );
    }

    private static void resInfo(
            LocalDateTime dateTime,
            String requestId,
            Integer status,
            Long delay,
            String headers,
            String body
    ) {
        log.info(
                String.format(
                        resFormat,
                        dateFormat.format(dateTime),
                        LogMode.INT,
                        requestId,
                        status,
                        delay,
                        headers,
                        body
                )
        );
    }


    private static void info(
            LocalDateTime dateTime,
            String requestId,
            String message
    ) {
        log.info(
                String.format(
                        infFormat,
                        dateFormat.format(dateTime),
                        LogMode.GEN,
                        requestId,
                        message
                )
        );
    }

    private static void info(
            LocalDateTime dateTime,
            String requestId,
            String message,
            Object... args
    ) {
        log.info(
                String.format(
                        infFormat,
                        dateFormat.format(dateTime),
                        LogMode.GEN,
                        requestId,
                        message
                ),
                args
        );
    }

    private static void exception(
            LocalDateTime dateTime,
            String requestId,
            Exception e
    ) {
        log.error(
                String.format(
                        errFormat,
                        dateFormat.format(dateTime),
                        LogMode.GEN,
                        requestId,
                        e.getMessage()
                ), e
        );
    }

    private static void exception(
            LocalDateTime dateTime,
            String requestId,
            String message,
            Exception e
    ) {
        log.error(
                String.format(
                        errFormat,
                        dateFormat.format(dateTime),
                        LogMode.GEN,
                        requestId,
                        message
                ), e
        );
    }

    private static void exception(
            LocalDateTime dateTime,
            String requestId,
            Throwable e
    ) {
        log.error(
                String.format(
                        errFormat,
                        dateFormat.format(dateTime),
                        LogMode.GEN,
                        requestId,
                        e.getMessage()
                ), e
        );
    }

    private static void exception(
            LocalDateTime dateTime,
            String requestId,
            String message,
            Throwable e
    ) {
        log.error(
                String.format(
                        errFormat,
                        dateFormat.format(dateTime),
                        LogMode.GEN,
                        requestId,
                        message
                ), e
        );
    }

    public static void errorShort(String requestId,
                                  Throwable e) {
        log.error(
                String.format(
                        errFormat,
                        dateFormat.format(LocalDateTime.now()),
                        LogMode.GEN,
                        requestId,
                        e.getMessage()
                )
        );
    }


}
