package uz.pdp.app_codingbat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.app_codingbat.config.core.BaseURI;
import uz.pdp.app_codingbat.payload.base.ApiResult;
import uz.pdp.app_codingbat.payload.base.ResBaseMsg;
import uz.pdp.app_codingbat.payload.file.ResUploadFile;
import uz.pdp.app_codingbat.service.AttachmentService;

import java.util.UUID;

@RequestMapping(BaseURI.API1 + BaseURI.FILE)
@RestController
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService service;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(
            value = BaseURI.UPLOAD,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResult<ResUploadFile> upload(@RequestPart("file") MultipartFile file) {
        return ApiResult.successResponse(service.upload(file));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = BaseURI.DOWNLOAD + "/{id}")
    public ResponseEntity<?> download(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "inline") String view
    ) {
        return service.download(id, view);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(BaseURI.DELETE + "/{id}")
    public ApiResult<ResBaseMsg> deleteFile(@PathVariable UUID id){
        return ApiResult.successResponse(service.delete(id));
    }

}
