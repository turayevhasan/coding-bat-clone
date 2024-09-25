package uz.pdp.app_codingbat.payload.cases.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUpdateCase {

    @NotNull
    private Long id;

    private String args;

    private String expected;

    private Boolean visible;

    private Long problemId;
}
