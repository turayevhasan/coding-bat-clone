package uz.pdp.app_codingbat.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.payload.base.ErrorResponse;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        log.error("Responding with 401 unauthorized error. URL -  {}, Message - {}", request.getRequestURI(), ex.getMessage());

        ApiResult<ErrorResponse> errorDataApiResult = ApiResult.errorResponse("unauthorized", 401);
        response.getWriter().write(objectMapper.writeValueAsString(errorDataApiResult));

        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
