package uz.pdp.app_codingbat.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.app_codingbat.entity.Category;
import uz.pdp.app_codingbat.entity.Problem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    @NotNull
    Optional<Problem> findById(@NotNull Long id);

    List<Problem> findProblemsByCategory(Category category);

    @Query("SELECT p FROM Problem p WHERE p.createdAt BETWEEN :startDate AND :endDate")
    Page<Problem> findProblemsByCreatedAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    List<Problem> findAllByCategoryId(Long id);

    @Query("select p from Problem p where lower(p.name) like lower(concat('%', :keyword, '%'))")
    Page<Problem> search(String keyword, Pageable pageable);
}
