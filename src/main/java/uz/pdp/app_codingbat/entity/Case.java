package uz.pdp.app_codingbat.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.app_codingbat.entity.template.AbsLongEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "problem_case")
public class Case extends AbsLongEntity {
    @Column(nullable = false, name = "args")
    private String args;

    @Column(nullable = false, name = "expected")
    private String expected;

    @Column(name = "visible")
    private Boolean visible;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Problem problem;
}
