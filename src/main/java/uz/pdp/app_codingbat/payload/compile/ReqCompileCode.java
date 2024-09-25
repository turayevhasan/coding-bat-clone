package uz.pdp.app_codingbat.payload.compile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCompileCode {
    @NotBlank
    private String code;
    @NotNull
    private Long problemId;
}
