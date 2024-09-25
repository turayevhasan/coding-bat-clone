package uz.pdp.app_codingbat.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import uz.pdp.app_codingbat.enums.ErrorTypeEnum;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.payload.base.ErrorResponse;
import uz.pdp.app_codingbat.utils.MessageConstants;

import java.util.Arrays;
import java.util.Objects;


@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    // FOYDALANUVCHI TOMONIDAN XATO SODIR BO'LGANDA
    @ExceptionHandler(value = {RestException.class})
    public ResponseEntity<ApiResult<ErrorResponse>> handleException(RestException ex) {

        ErrorTypeEnum errorTypeEnum = ex.getErrorTypeEnum();
        ErrorResponse errorData = new ErrorResponse(errorTypeEnum.name(), errorTypeEnum.getStatus().value());

        return new ResponseEntity<>(ApiResult.errorResponse(errorData), ex.getStatus());
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException -> ", ex);
        String errorFieldMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> String.format("%s: %s, ", getJsonPropertyName(fe, ex.getTarget()), fe.getDefaultMessage()))
                .distinct()
                .reduce("", String::concat);
        ObjectError objectError = ex.getBindingResult().getGlobalError();
        String errorMessage = Objects.nonNull(objectError) ? "" + objectError.getDefaultMessage() : "Invalid request parameters";
        if (StringUtils.hasText(errorFieldMessage)) {
            errorMessage = errorMessage.concat(" (").concat(errorFieldMessage).concat(")");
        }
        return new ResponseEntity<>(ApiResult.errorResponse(errorMessage, 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TypeMismatchException.class})
    public ResponseEntity<?> handleException(TypeMismatchException ex) {
        log.error("TypeMismatchException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleException(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResponseEntity<?> handleException(ServletRequestBindingException ex) {
        log.error("ServletRequestBindingException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestPartException.class})
    public ResponseEntity<?> handleException(MissingServletRequestPartException ex) {
        log.error("MissingServletRequestPartException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<?> handleException(BindException ex) {
        log.error("BindException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<?> handleException(AccessDeniedException ex) {
        log.error("AccessDeniedException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(MessageConstants.DO_NOT_HAVE_PERMISSION_TO_USE_THIS_WAY, 400), //"Bu yo'lga kirishga huquq yo'q"
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {MissingPathVariableException.class})
    public ResponseEntity<?> handleException(MissingPathVariableException ex) {
        log.error("MissingPathVariableException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(MessageConstants.PAGE_NOT_FOUND, 404), //ex.me
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<?> handleException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND);
    }


    //METHOD XATO BO'LSA
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(MessageConstants.METHOD_ERROR, 405),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotAcceptableException ex) {
        log.error("HttpMediaTypeNotAcceptableException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(MessageConstants.UNACCEPTABLE, 406),
                HttpStatus.NOT_ACCEPTABLE);
    }


    //METHOD XATO BO'LSA
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotSupportedException ex) {
        log.error("HttpMediaTypeNotSupportedException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(MessageConstants.METHOD_ERROR, 415),
                HttpStatus.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(value = {ConversionNotSupportedException.class})
    public ResponseEntity<?> handleException(ConversionNotSupportedException ex) {
        log.error("ConversionNotSupportedException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {HttpMessageNotWritableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotWritableException ex) {
        log.error("HttpMessageNotWritableException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Exception -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AsyncRequestTimeoutException.class})
    public ResponseEntity<?> handleException(AsyncRequestTimeoutException ex) {
        log.error("AsyncRequestTimeoutException -> ", ex);
        return new ResponseEntity<>(
                ApiResult.errorResponse(ex.getMessage(), 503),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    private String getJsonPropertyName(@NonNull FieldError fieldError, @Nullable Object target) {
        String fieldName = fieldError.getField();
        if (target == null) {
            return fieldName;
        }
        JsonProperty jsonPropertyAnnotation = Arrays.stream(target.getClass()
                        .getDeclaredFields())
                .filter(field -> field.getName().equals(fieldName))
                .findFirst()
                .map(field -> field.getAnnotation(JsonProperty.class))
                .orElse(null);

        return (jsonPropertyAnnotation != null) ? jsonPropertyAnnotation.value() : fieldName;
    }

}
