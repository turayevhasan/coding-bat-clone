package uz.pdp.app_codingbat.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorTypeEnum {

    TOKEN_NOT_VALID,
    USER_PERMISSION_RESTRICTION(HttpStatus.UNAUTHORIZED),
    FORBIDDEN(HttpStatus.FORBIDDEN),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED),
    WRONG_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_NOT_EXPIRED(HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND_OR_DISABLED(HttpStatus.FORBIDDEN),
    LOGIN_OR_PASSWORD_ERROR(HttpStatus.FORBIDDEN),
    EMAIL_ALREADY_EXISTS,
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND),
    PROBLEM_NOT_FOUND(HttpStatus.NOT_FOUND),
    LANGUAGE_NOT_FOUND(HttpStatus.NOT_FOUND),
    CASE_NOT_FOUND(HttpStatus.NOT_FOUND),
    ATTACHMENT_NOT_FOUND(HttpStatus.NOT_FOUND),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND),
    FILE_CANNOT_DELETED,
    ERROR_SAVING_FILE,
    SUBMISSION_NOT_FOUND(HttpStatus.NOT_FOUND);

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    ErrorTypeEnum(HttpStatus status) {
        this.status = status;
    }

    ErrorTypeEnum() {
    }
}
