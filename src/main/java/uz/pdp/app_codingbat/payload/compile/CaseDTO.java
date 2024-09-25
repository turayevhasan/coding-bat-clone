package uz.pdp.app_codingbat.payload.compile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CaseDTO {
    private String args;

    private String expected;

    private Boolean visible;
}
