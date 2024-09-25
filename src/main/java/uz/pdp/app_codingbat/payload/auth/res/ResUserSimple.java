package uz.pdp.app_codingbat.payload.auth.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import uz.pdp.app_codingbat.entity.Attachment;
import uz.pdp.app_codingbat.entity.User;
import uz.pdp.app_codingbat.entity.enums.UserStatus;
import uz.pdp.app_codingbat.utils.FormatPatterns;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class ResUserSimple {
    private UUID id;
    private String email;
    private UserStatus status;
    private String role;
    private String profileImagePath;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;

    private boolean deleted;

    public ResUserSimple(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.role = user.getRole().getName();
        this.profileImagePath = user.getProfileImagePath();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.deleted = user.isDeleted();
    }
}
