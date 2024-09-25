package uz.pdp.app_codingbat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.app_codingbat.config.logger.Logger;
import uz.pdp.app_codingbat.entity.Attachment;
import uz.pdp.app_codingbat.enums.ErrorTypeEnum;
import uz.pdp.app_codingbat.exceptions.RestException;
import uz.pdp.app_codingbat.mapper.AttachmentMapper;
import uz.pdp.app_codingbat.payload.base.ResBaseMsg;
import uz.pdp.app_codingbat.payload.file.ResUploadFile;
import uz.pdp.app_codingbat.repository.AttachmentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static uz.pdp.app_codingbat.enums.ErrorTypeEnum.FILE_CANNOT_DELETED;
import static uz.pdp.app_codingbat.enums.ErrorTypeEnum.FILE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    @Value("${application.file.upload-path}")
    private String uploadPath;

    public ResUploadFile upload(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw RestException.restThrow(FILE_NOT_FOUND);

        Attachment attachment = AttachmentMapper.fromMultipartToEntity(file, uploadPath);
        attachmentRepository.save(attachment); //save file in db

        try {
            Files.copy(file.getInputStream(), Paths.get(attachment.getFilePath()), StandardCopyOption.REPLACE_EXISTING); //save file in memory
        } catch (IOException e) {
            Logger.error(e);
            throw RestException.restThrow(ErrorTypeEnum.ERROR_SAVING_FILE);
        }



        return AttachmentMapper.fromEntityToResDto(attachment);
    }

    public ResponseEntity<?> download(UUID id, String view) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(ErrorTypeEnum.ATTACHMENT_NOT_FOUND));

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(attachment.getContentType()))
                    .headers(httpHeaders -> {
                        httpHeaders.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
                        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, view + "; filename=" + attachment.getOriginalName());
                    })
                    .body(Files.readAllBytes(Path.of(attachment.getFilePath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResBaseMsg delete(UUID id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(FILE_NOT_FOUND));

        System.out.println("File Deleted ///////////");
        try {
            Files.deleteIfExists(Path.of(attachment.getFilePath()));  // deletes file from system
        } catch (IOException e) {
            throw RestException.restThrow(FILE_CANNOT_DELETED);
        }

        attachmentRepository.deleteById(attachment.getId()); // deletes file from db

        return new ResBaseMsg("File successfully deleted");
    }

}
