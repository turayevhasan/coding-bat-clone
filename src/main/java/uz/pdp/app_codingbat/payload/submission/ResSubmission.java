package uz.pdp.app_codingbat.payload.submission;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import uz.pdp.app_codingbat.entity.enums.AnswerStatus;
import uz.pdp.app_codingbat.payload.auth.res.ResUserSimple;
import uz.pdp.app_codingbat.payload.problem.res.ResProblem;
import uz.pdp.app_codingbat.utils.FormatPatterns;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResSubmission {
    private String solution;
    private AnswerStatus answerStatus;
    private String message;
    private ResProblem problem;
    private ResUserSimple user;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;

    private boolean deleted;

}
