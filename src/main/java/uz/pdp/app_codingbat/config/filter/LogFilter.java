package uz.pdp.app_codingbat.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import uz.pdp.app_codingbat.config.core.GlobalVar;
import uz.pdp.app_codingbat.config.logger.Logger;
import uz.pdp.app_codingbat.utils.ConfigUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Component
public class LogFilter extends OncePerRequestFilter {

    private final Set<String> urlsNotToSave = new HashSet<>() {{
        add("log-conf");
        add("swagger");
        add("favicon");
        add("report");
        add("download");
        add("review");
        add("upload");
    }};

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        doFilterWrapped(
                ConfigUtils.wrapRequest(request),
                ConfigUtils.wrapResponse(response),
                filterChain
        );
    }

    private void doFilterWrapped(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        } finally {
            String URI = request.getRequestURI();
            if (allowToSave(URI)) {
                Logger.reqInt(
                        GlobalVar.getStartTime(),
                        GlobalVar.getRequestId(),
                        HttpMethod.valueOf(request.getMethod()),
                        URI,
                        ConfigUtils.getHeaders(request),
                        ConfigUtils.getRequestBody(request)
                );
                LocalDateTime endTime = LocalDateTime.now();
                Logger.resInt(
                        LocalDateTime.now(),
                        GlobalVar.getRequestId(),
                        response.getStatus(),
                        ChronoUnit.MILLIS.between(GlobalVar.getStartTime(), endTime),
                        ConfigUtils.getHeaders(response),
                        ConfigUtils.getResponseBody(response)
                );
            }
            response.copyBodyToResponse();
        }
    }

    private boolean allowToSave(String uri) {
        for (String url : this.urlsNotToSave) {
            if (uri.contains(url))
                return false;
        }
        return true;
    }
}



