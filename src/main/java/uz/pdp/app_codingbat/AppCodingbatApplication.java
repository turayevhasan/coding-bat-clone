package uz.pdp.app_codingbat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AppCodingbatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppCodingbatApplication.class, args);
    }

}
