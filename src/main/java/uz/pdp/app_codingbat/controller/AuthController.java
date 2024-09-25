package uz.pdp.app_codingbat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_codingbat.config.core.BaseURI;
import uz.pdp.app_codingbat.payload.auth.req.ReqRefreshToken;
import uz.pdp.app_codingbat.payload.auth.req.ReqSignIn;
import uz.pdp.app_codingbat.payload.auth.req.ReqSignUp;
import uz.pdp.app_codingbat.payload.auth.res.ResSignIn;
import uz.pdp.app_codingbat.payload.auth.res.TokenDto;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.payload.base.ResBaseMsg;
import uz.pdp.app_codingbat.service.AuthService;


@RequestMapping(BaseURI.API1 + BaseURI.AUTH)
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(BaseURI.SIGN_UP)
    public ApiResult<ResBaseMsg> signUp(@RequestBody @Valid ReqSignUp req) {
        return ApiResult.successResponse(authService.signUp(req));
    }

    @GetMapping(BaseURI.ACTIVATE + "/{email}")
    public ApiResult<ResBaseMsg> verificationEmail(@PathVariable String email) {
        return ApiResult.successResponse(authService.verifyEmail(email));
    }

    @PostMapping(BaseURI.SIGN_IN)
    ApiResult<ResSignIn> signIn(@Valid @RequestBody ReqSignIn req) {
        return ApiResult.successResponse(authService.signIn(req));
    }

    @GetMapping(BaseURI.TOKEN + BaseURI.REFRESH)
    ApiResult<TokenDto> refreshToken(@Valid @RequestBody ReqRefreshToken req) {
        return ApiResult.successResponse(authService.refreshToken(req));
    }
}
