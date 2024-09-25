package uz.pdp.app_codingbat.mapper;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.app_codingbat.entity.Attachment;
import uz.pdp.app_codingbat.payload.file.ResUploadFile;

public interface AttachmentMapper {

    static Attachment fromMultipartToEntity(MultipartFile file, String uploadPath) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        return Attachment.builder()
                .originalName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .extension(extension)
                .fileSize(file.getSize())
                .uploadPath(uploadPath)
                .build();
    }

    static ResUploadFile fromEntityToResDto(Attachment attachment) {
        String fileName = attachment.getId() + "." + attachment.getExtension();

        return new ResUploadFile(
                fileName,
                attachment.getFilePath(),
                attachment.getCreatedAt(),
                attachment.getUpdatedAt()
        );
    }
}
