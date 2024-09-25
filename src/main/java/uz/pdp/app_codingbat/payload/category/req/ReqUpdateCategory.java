package uz.pdp.app_codingbat.payload.category.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReqUpdateCategory {
    @NotNull
    private Long id;

    private String name;
    private String description;
    private Integer maxStars;
    private Long languageId;
}
