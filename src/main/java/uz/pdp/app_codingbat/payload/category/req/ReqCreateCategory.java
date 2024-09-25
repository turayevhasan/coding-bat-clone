package uz.pdp.app_codingbat.payload.category.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class ReqCreateCategory {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Integer maxStars;

    @NotNull
    private Long languageId;
}
