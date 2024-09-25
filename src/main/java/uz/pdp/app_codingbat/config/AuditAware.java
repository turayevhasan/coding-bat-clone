package uz.pdp.app_codingbat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@Configuration
public class AuditAware {

    @Bean
    public AuditorAware<UUID> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (!principal.equals("anonymousUser")) {
                    UserPrincipal userPrincipal = (UserPrincipal) principal;
//                    return Optional.of(userPrincipal.getUser().getId());
                    return Optional.of(userPrincipal.getId());
                }
            }
            return Optional.empty();
        };

    }


}
