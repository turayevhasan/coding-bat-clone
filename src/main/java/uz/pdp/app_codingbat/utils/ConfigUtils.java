package uz.pdp.app_codingbat.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class ConfigUtils {

    public static HttpHeaders getHeaders(ContentCachingRequestWrapper request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HashMap<String, String> headers = new HashMap<>();
        Iterator<String> iterator = request.getHeaderNames().asIterator();
        while (iterator.hasNext()) {
            String header = iterator.next();
            headers.put(header, request.getHeader(header));
        }
        httpHeaders.setAll(headers);
        return httpHeaders;
        //return mapper.writeValueAsString(headers);
    }

    public static HttpHeaders getHeaders(ContentCachingResponseWrapper response) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HashMap<String, String> headers = new HashMap<>();
        for (String header : response.getHeaderNames()) {
            headers.put(header, response.getHeader(header));
        }
        httpHeaders.setAll(headers);
        return httpHeaders;
    }

    public static String getRequestBody(ContentCachingRequestWrapper request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if (request.getContentAsByteArray().length > 0) {
            ObjectReader reader = mapper.reader();
            return reader.readTree(new ByteArrayInputStream(request.getContentAsByteArray())).toString();
        }
        if (!request.getParameterMap().isEmpty()) {
            ObjectNode parameters = mapper.createObjectNode();
            request.getParameterMap().forEach((key, value) -> parameters.put(key, getParameter(value)));
            return parameters.toString();
        }
        ObjectNode node = mapper.createObjectNode();
        return node.toString();
    }

    public static String getResponseBody(ContentCachingResponseWrapper response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        if (response.getContentAsByteArray().length > 0) {
            ObjectReader reader = mapper.reader();
            return reader.readTree(new ByteArrayInputStream(response.getContentAsByteArray())).toString();
        }
        ObjectNode node = mapper.createObjectNode();
        return node.toString();
    }

    private static String getParameter(String[] value) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : value) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    public static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    public static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }
}