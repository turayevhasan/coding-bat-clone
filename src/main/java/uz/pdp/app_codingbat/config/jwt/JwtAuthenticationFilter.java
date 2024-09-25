package uz.pdp.app_codingbat.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.app_codingbat.config.UserPrincipal;
import uz.pdp.app_codingbat.config.core.GlobalVar;
import uz.pdp.app_codingbat.config.logger.Logger;
import uz.pdp.app_codingbat.utils.RestConstants;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(@Lazy JwtTokenProvider jwtTokenProvider,
                                   @Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        GlobalVar.clearContext();
        GlobalVar.initStartTime();

        String X_REQUEST_ID = request.getHeader("X-Request-ID");
        GlobalVar.setRequestId(Optional.ofNullable(X_REQUEST_ID).orElse(UUID.randomUUID().toString()));

        try {
            String authorization = request.getHeader(RestConstants.AUTHENTICATION_HEADER);

            if (authorization != null) {
                UserPrincipal userPrincipal = getUserFromBearerToken(authorization);
                if (userPrincipal != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    GlobalVar.setUserPrincipal(userPrincipal);
                    GlobalVar.setUser(userPrincipal.user());
                    GlobalVar.setUserUuid(userPrincipal.getId());
                }
            }
        } catch (Exception e) {
            Logger.error("SET_USER_PRINCIPAL_IF_ALL_OK_METHOD_ERROR", e);
        }

        filterChain.doFilter(request, response);
    }


    public UserPrincipal getUserFromBearerToken(String token) {

        token = token.trim();
        if (token.startsWith(RestConstants.BEARER_TOKEN)) {
            token = token.substring(RestConstants.BEARER_TOKEN.length()).trim();

            String userEmail = jwtTokenProvider.extractUsername(token);

            if (userEmail != null) {
                return (UserPrincipal) userDetailsService.loadUserByUsername(userEmail);
            }
        }

        return null;
    }


}
