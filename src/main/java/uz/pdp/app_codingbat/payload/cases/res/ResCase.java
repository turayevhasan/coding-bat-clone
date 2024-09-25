package uz.pdp.app_codingbat.payload.cases.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import uz.pdp.app_codingbat.entity.Problem;
import uz.pdp.app_codingbat.payload.problem.res.ResProblem;
import uz.pdp.app_codingbat.utils.FormatPatterns;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResCase {

    private Long id;

    private String args;

    private String expected;

    private Boolean visible;

    private ResProblem problem;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;

    private boolean deleted;
}
