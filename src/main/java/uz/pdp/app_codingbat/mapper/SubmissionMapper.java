package uz.pdp.app_codingbat.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import uz.pdp.app_codingbat.entity.Submission;
import uz.pdp.app_codingbat.payload.auth.res.ResUserSimple;
import uz.pdp.app_codingbat.payload.submission.ResSubmission;

import java.util.ArrayList;
import java.util.List;

public interface SubmissionMapper {
    static ResSubmission fromEntityToDto(Submission submission) {
        return ResSubmission.builder()
                .solution(submission.getSolution())
                .answerStatus(submission.getAnswerStatus())
                .message(submission.getMessage())
                .problem(ProblemMapper.fromEntityToDto(submission.getProblem()))
                .user(new ResUserSimple(submission.getUser()))
                .createdAt(submission.getCreatedAt())
                .updatedAt(submission.getUpdatedAt())
                .build();
    }

    static List<ResSubmission> pagesToDto(Page<Submission> submissions) {
        List<ResSubmission> resSubmissions = new ArrayList<>();
        for (Submission submission : submissions) {
            resSubmissions.add(fromEntityToDto(submission));
        }
        return resSubmissions;
    }
}
