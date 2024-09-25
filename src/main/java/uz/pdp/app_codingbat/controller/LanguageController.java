package uz.pdp.app_codingbat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_codingbat.config.core.BaseURI;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.payload.language.req.ReqCreateLanguage;
import uz.pdp.app_codingbat.payload.language.req.ReqUpdateLanguage;
import uz.pdp.app_codingbat.payload.language.res.ResLanguage;
import uz.pdp.app_codingbat.service.LanguageService;

import java.util.List;
@RequestMapping(BaseURI.API1 + BaseURI.LANGUAGE)
@RestController
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PreAuthorize("hasAuthority('ADD_LANGUAGE')")
    @PostMapping(BaseURI.CREATE)
    public ApiResult<ResLanguage> createLanguage(@Valid @RequestBody ReqCreateLanguage req) {
        return ApiResult.successResponse(languageService.create(req.getName()));
    }

    @PreAuthorize("hasAuthority('UPDATE_LANGUAGE')")
    @PutMapping(BaseURI.UPDATE)
    public ApiResult<ResLanguage> updateLanguage(@Valid @RequestBody ReqUpdateLanguage reqUpdateLanguage){
        return ApiResult.successResponse(languageService.update(reqUpdateLanguage));
    }

    @PreAuthorize("hasAuthority('DELETE_LANGUAGE')")
    @DeleteMapping(BaseURI.DELETE + "/{id}")
    public ApiResult<Boolean> deleteLanguage(@PathVariable Long id) {
        return ApiResult.successResponse(languageService.delete(id));
    }

    @GetMapping(BaseURI.GET + "/{id}")
    public ApiResult<ResLanguage> getLanguage(@PathVariable Long id){
        return ApiResult.successResponse(languageService.get(id));
    }

    @GetMapping(BaseURI.GET_ALL)
    public ApiResult<List<ResLanguage>> getAllLanguage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ApiResult.successResponse(languageService.getAll(page, size));
    }

}



