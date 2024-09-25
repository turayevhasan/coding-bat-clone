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
import uz.pdp.app_codingbat.payload.user.req.RequestFilterByCreatedDate;
import uz.pdp.app_codingbat.payload.user.req.ReqUserUpdate;
import uz.pdp.app_codingbat.payload.user.res.ResUserSimple;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(BaseURI.API1 + BaseURI.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('GET_USER')")
    @GetMapping(BaseURI.GET_USER_BY_EMAIL)
    public ApiResult<ResUserSimple> getUserByEmail(@PathVariable String email) {
        return ApiResult.successResponse(userService.getOneByEmail(email));
    }

    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    @GetMapping(BaseURI.GET_ALL)
    public ApiResult<List<ResUserSimple>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResult.successResponse(userService.getAllUsers(page, size));
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping(BaseURI.DELETE + "/{id}")
    public ApiResult<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return ApiResult.successResponse("User successfully deleted");
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping(BaseURI.UPDATE)
    public ApiResult<ResUserSimple> update(@RequestBody ReqUserUpdate req){
        return ApiResult.successResponse(userService.updateUser(req));
    }

    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    @GetMapping(BaseURI.FILTER)
    public ApiResult<List<ResUserSimple>> filter(@Valid @RequestBody RequestFilterByCreatedDate req) {
        return ApiResult.successResponse(userService.getFilteredUsers(req));
    }

    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    @GetMapping(BaseURI.SEARCH)
    public ResponseEntity<ApiResult<Page<ResUserSimple>>> search(
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ResUserSimple> users = userService.search(keyword, pageable);

        return ResponseEntity.ok(ApiResult.successResponse(users));
    }


}
