package uz.pdp.app_codingbat.payload.cases.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreateCase {
    @NotBlank
    private String args;

    @NotNull
    private String expected;

    @NotNull
    private Boolean visible;

    @NotNull
    private Long problemId;
}
