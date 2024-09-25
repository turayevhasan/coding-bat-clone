package uz.pdp.app_codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.app_codingbat.entity.Case;
import uz.pdp.app_codingbat.entity.Problem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<Case, Integer> {
    Optional<Case> findById(Long id);

    @Query("SELECT c FROM Case c WHERE c.problem.id = :problemId")
    List<Case> findAllByProblemId(@Param("problemId") Long problemId);

}
