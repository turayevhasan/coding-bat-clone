package uz.pdp.app_codingbat.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.app_codingbat.entity.enums.AnswerStatus;
import uz.pdp.app_codingbat.entity.template.AbsLongEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "submission")
public class Submission extends AbsLongEntity {
    @Column(name = "solution", columnDefinition = "TEXT")
    private String solution;

    @Column(name = "answer_status")
    @Enumerated(EnumType.STRING)
    private AnswerStatus answerStatus;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;
}
