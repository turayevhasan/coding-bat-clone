package uz.pdp.app_codingbat.payload.user.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestFilterByCreatedDate {
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;

    private Integer page;
    private Integer size;
}
