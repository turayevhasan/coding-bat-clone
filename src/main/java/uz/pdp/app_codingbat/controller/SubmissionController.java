package uz.pdp.app_codingbat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_codingbat.config.core.BaseURI;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.payload.submission.ResSubmission;
import uz.pdp.app_codingbat.service.SubmissionService;

import java.util.List;

@RestController
@RequestMapping(BaseURI.API1 + BaseURI.SUBMISSION)
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @PreAuthorize("hasAuthority('GET_SUBMISSION')")
    @GetMapping(BaseURI.GET + "/{problemId}")
    public ApiResult<ResSubmission> getSubmissionByProblem(@PathVariable Long problemId) {
        return ApiResult.successResponse(submissionService.getByProblemId(problemId));
    }

    @PreAuthorize("hasAuthority('GET_SUBMISSION')")
    @GetMapping(BaseURI.GET_ALL + "/{email}")
    public ApiResult<List<ResSubmission>> getAllSubmissionsByUser(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResult.successResponse(submissionService.getAll(email, page, size));
    }
}
