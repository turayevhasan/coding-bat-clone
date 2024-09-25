package uz.pdp.app_codingbat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import uz.pdp.app_codingbat.config.core.BaseURI;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.payload.problem.req.ReqCreateProblem;
import uz.pdp.app_codingbat.payload.user.req.RequestFilterByCreatedDate;
import uz.pdp.app_codingbat.payload.problem.req.ReqUpdateProblem;
import uz.pdp.app_codingbat.payload.problem.res.ResProblem;
import uz.pdp.app_codingbat.service.ProblemService;

import java.util.List;

@RequestMapping(BaseURI.API1 + BaseURI.PROBLEM)
@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PreAuthorize("hasAuthority('ADD_PROBLEM')")
    @PostMapping(BaseURI.CREATE)
    public ApiResult<ResProblem> createProblem(@Valid @RequestBody ReqCreateProblem req) {
        return ApiResult.successResponse(problemService.create(req));
    }

    @PreAuthorize("hasAuthority('UPDATE_PROBLEM')")
    @PutMapping(BaseURI.UPDATE)
    public ApiResult<ResProblem> updateProblem(@Valid @RequestBody ReqUpdateProblem req) {
        return ApiResult.successResponse(problemService.update(req));
    }

    @PreAuthorize("hasAuthority('DELETE_PROBLEM')")
    @DeleteMapping(BaseURI.DELETE + "/{id}")
    public ApiResult<String> deleteProblem(@PathVariable Long id){
        problemService.delete(id);
        return ApiResult.successResponse("Problem deleted");
    }

    @GetMapping(BaseURI.GET + "/{id}")
    public ApiResult<ResProblem> getProblemById(@PathVariable Long id) {
        return ApiResult.successResponse(problemService.getProblemById(id));
    }

    @GetMapping(BaseURI.GET_ALL)
    public ApiResult<List<ResProblem>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResult.successResponse(problemService.getAllByPagination(page, size));
    }

    @GetMapping(BaseURI.SEARCH)
    public ResponseEntity<ApiResult<Page<ResProblem>>> search(
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ResProblem> problems = problemService.search(keyword, pageable);

        return ResponseEntity.ok(ApiResult.successResponse(problems));
    }

    @GetMapping(BaseURI.FILTER)
    public ApiResult<List<ResProblem>> getFilteredProblems(@Valid @RequestBody RequestFilterByCreatedDate req) {
        return ApiResult.successResponse(problemService.filterProblems(req));
    }

}
