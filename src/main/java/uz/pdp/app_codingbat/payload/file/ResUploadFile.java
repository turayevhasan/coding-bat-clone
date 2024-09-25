
package uz.pdp.app_codingbat.payload.file;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import uz.pdp.app_codingbat.utils.FormatPatterns;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ResUploadFile {
    private String fileName;
    private String filePath;

    // Using Timestamp instead of LocalDateTime
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private Timestamp createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private Timestamp updatedAt;
}
