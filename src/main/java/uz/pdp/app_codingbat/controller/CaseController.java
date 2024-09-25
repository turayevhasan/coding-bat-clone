package uz.pdp.app_codingbat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_codingbat.config.core.BaseURI;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.payload.cases.req.ReqCreateCase;
import uz.pdp.app_codingbat.payload.cases.req.ReqUpdateCase;
import uz.pdp.app_codingbat.payload.cases.res.ResCase;
import uz.pdp.app_codingbat.service.CaseService;

import java.util.List;

@RequestMapping(BaseURI.API1 + BaseURI.CASE)
@RestController
@RequiredArgsConstructor
public class CaseController {
    private final CaseService caseService;

    @PreAuthorize("hasAuthority('ADD_CASE')")
    @PostMapping(BaseURI.CREATE)
    public ApiResult<ResCase> createCase(@Valid @RequestBody ReqCreateCase req) {
        return ApiResult.successResponse(caseService.create(req));
    }

    @PreAuthorize("hasAuthority('UPDATE_CASE')")
    @PutMapping(BaseURI.UPDATE)
    public ApiResult<ResCase> updateCase(@Valid @RequestBody ReqUpdateCase req) {
        return ApiResult.successResponse(caseService.update(req));
    }

    @PreAuthorize("hasAuthority('DELETE_CASE')")
    @DeleteMapping(BaseURI.DELETE + "/{id}")
    public ApiResult<String> deleteCase(@PathVariable Long id){
        caseService.delete(id);
        return ApiResult.successResponse("Case deleted");
    }

    @GetMapping(BaseURI.GET + "/{id}")
    public ApiResult<ResCase> getCaseById(@PathVariable Long id) {
        return ApiResult.successResponse(caseService.getCaseById(id));
    }

    @GetMapping(BaseURI.GET_ALL)
    public ApiResult<List<ResCase>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResult.successResponse(caseService.getAllByPagination(page, size));
    }

}
