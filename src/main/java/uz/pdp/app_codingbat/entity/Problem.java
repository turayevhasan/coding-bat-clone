package uz.pdp.app_codingbat.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.app_codingbat.entity.template.AbsLongEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "problem")
public class Problem extends AbsLongEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "question_title", columnDefinition = "TEXT")
    private String questionTitle;

    @Column(name = "example_case", columnDefinition = "TEXT")
    private String exampleCase;

    @Column(name = "question_code", columnDefinition = "TEXT")
    private String questionCode;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Category category;

}
