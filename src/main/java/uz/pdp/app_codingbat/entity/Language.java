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
@Table(name = "language")
public class Language extends AbsLongEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "language", fetch = FetchType.LAZY)
    private List<Category> categories;
}
