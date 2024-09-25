package uz.pdp.app_codingbat.payload.problem.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUpdateProblem {

    @NotNull
    private Long Id;

    private String name;

    private String questionTitle;

    private String exampleCase;

    private String questionCode;

    private Long categoryId;
}
