package uz.pdp.app_codingbat.payload.language.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUpdateLanguage {
    @NotNull
    private Long id;

    @NotBlank
    private String name;
}