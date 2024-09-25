package uz.pdp.app_codingbat.payload.compile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompilerResult {
    private CaseDTO aCase;

    private String methodName;

    private String run;

    private Boolean isSolved;
}
