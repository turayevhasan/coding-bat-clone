package uz.pdp.app_codingbat.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.app_codingbat.entity.template.AbsUUIDEntity;
import uz.pdp.app_codingbat.entity.template.AbsUUIDNotUserAuditEntity;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attachment")
public class Attachment extends AbsUUIDNotUserAuditEntity {
    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "upload_path", nullable = false)
    private String uploadPath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    public String getFilePath() {
        return this.uploadPath + super.getId() + "." + this.extension;
    }

}
