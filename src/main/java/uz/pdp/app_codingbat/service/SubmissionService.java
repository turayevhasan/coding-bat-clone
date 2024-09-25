package uz.pdp.app_codingbat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.app_codingbat.config.core.GlobalVar;
import uz.pdp.app_codingbat.entity.Problem;
import uz.pdp.app_codingbat.entity.Submission;
import uz.pdp.app_codingbat.entity.User;
import uz.pdp.app_codingbat.entity.enums.AnswerStatus;
import uz.pdp.app_codingbat.enums.ErrorTypeEnum;
import uz.pdp.app_codingbat.exceptions.RestException;
import uz.pdp.app_codingbat.mapper.ProblemMapper;
import uz.pdp.app_codingbat.mapper.SubmissionMapper;
import uz.pdp.app_codingbat.payload.compile.CompilerResult;
import uz.pdp.app_codingbat.payload.problem.res.ResProblem;
import uz.pdp.app_codingbat.payload.submission.ResSubmission;
import uz.pdp.app_codingbat.repository.ProblemRepository;
import uz.pdp.app_codingbat.repository.SubmissionRepository;
import uz.pdp.app_codingbat.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;

    public void save(String code, List<CompilerResult> compilerResults, User user, Long problemId) {
        AnswerStatus status = checkProblemIsSolved(compilerResults);
        Optional<Submission> submission = submissionRepository.findByProblemId(problemId);

        if (submission.isPresent()) {
            Submission updated = submission.get();

            updated.setAnswerStatus(status);
            updated.setSolution(code);

            submissionRepository.save(updated); //save updated submission;
        } else {
            submissionRepository.save(new Submission(code, status, "Submission_Message", user, getProblem(problemId)));
        }
    }

    public ResSubmission getByProblemId(Long problemId) {
        User user = GlobalVar.getUser();

        if (user == null) {
            throw RestException.restThrow(ErrorTypeEnum.SUBMISSION_NOT_FOUND);
        }

        getProblem(problemId);

        Submission submission = submissionRepository.findByUserEmailAndProblemId(user.getEmail(), problemId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.SUBMISSION_NOT_FOUND));

        return SubmissionMapper.fromEntityToDto(submission);
    }


    public List<ResSubmission> getAll(String email, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        userRepository.findByEmail(email)
                .orElseThrow((RestException.thew(ErrorTypeEnum.USER_NOT_FOUND)));

        Page<Submission> submissions = submissionRepository.findAllByUserEmail(email, pageRequest);

        return SubmissionMapper.pagesToDto(submissions);
    }


    private Problem getProblem(Long problemId) {
        return problemRepository.findById(problemId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.PROBLEM_NOT_FOUND));
    }

    private AnswerStatus checkProblemIsSolved(List<CompilerResult> compilerResults) {
        for (CompilerResult res : compilerResults) {
            if (!res.getIsSolved())
                return AnswerStatus.WRONG_ANSWER;
        }
        return AnswerStatus.ACCEPTED;
    }
}
