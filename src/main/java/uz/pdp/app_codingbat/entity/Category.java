package uz.pdp.app_codingbat.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.app_codingbat.entity.template.AbsLongEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category extends AbsLongEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "max_stars")
    private Integer maxStars;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Language language;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Problem> problems;
}
