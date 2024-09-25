package uz.pdp.app_codingbat.config.prop;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application")
public class AppProp {

    private JwtConf jwt;
    private Mail mail;

    @Getter
    @Setter
    public static class JwtConf {
        private String secretKey;
        private Long accessTokenExp;
        private Long refreshTokenExp;
    }

    @Getter
    @Setter
    public static class Mail {
        private String from;
        private String activateUrl;
    }

}
