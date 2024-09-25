package uz.pdp.app_codingbat.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_codingbat.config.core.BaseURI;
import uz.pdp.app_codingbat.payload.category.req.ReqCreateCategory;
import uz.pdp.app_codingbat.payload.category.req.ReqUpdateCategory;
import uz.pdp.app_codingbat.payload.category.res.ResCategory;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.service.CategoryService;

import java.util.List;

@RequestMapping(BaseURI.API1 + BaseURI.CATEGORY)
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADD_CATEGORY')")
    @PostMapping(BaseURI.CREATE)
    public ApiResult<ResCategory> add(@RequestBody @Valid ReqCreateCategory category) {
        return ApiResult.successResponse(categoryService.addCategory(category));
    }

    @PreAuthorize("hasAuthority('UPDATE_CATEGORY')")
    @PutMapping(BaseURI.UPDATE)
    public ApiResult<ResCategory> update(@Valid @RequestBody ReqUpdateCategory req) {
        return ApiResult.successResponse(categoryService.updateCategory(req));
    }

    @PreAuthorize("hasAuthority('DELETE_CATEGORY')")
    @DeleteMapping(BaseURI.DELETE + "/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ApiResult.successResponse("Category deleted");
    }


    @GetMapping(BaseURI.GET + "/{id}")
    public ApiResult<ResCategory> getOne(@PathVariable Long id) {
        return ApiResult.successResponse(categoryService.getOneCategory(id));
    }


    @GetMapping(BaseURI.GET_ALL)
    public ApiResult<List<ResCategory>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResult.successResponse(categoryService.getAllByPage(page, size));
    }
}
