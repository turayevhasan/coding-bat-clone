package uz.pdp.app_codingbat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_codingbat.payload.compile.CompilerResult;
import uz.pdp.app_codingbat.config.core.BaseURI;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.payload.compile.ReqCompileCode;
import uz.pdp.app_codingbat.service.CompilerService;

import java.util.List;

@RestController
@RequestMapping(BaseURI.API1 + BaseURI.COMPILER)
@RequiredArgsConstructor
public class CompilerController {

    private final CompilerService compilerService;

    @PostMapping(BaseURI.RUN)
    public ApiResult<List<CompilerResult>> runCode(@Valid @RequestBody ReqCompileCode req){
        return ApiResult.successResponse(compilerService.run(req));
    }

}
