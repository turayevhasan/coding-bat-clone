package uz.pdp.app_codingbat.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProp {

   List<AuthUser> users;

   @Getter
   @Setter
   public static class AuthUser {
       private String email;
       private String password;
       private String role;
   }

}
