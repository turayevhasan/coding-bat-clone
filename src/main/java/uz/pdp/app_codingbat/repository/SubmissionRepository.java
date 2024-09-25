package uz.pdp.app_codingbat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app_codingbat.entity.Submission;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByUserEmailAndProblemId(String userEmail, Long problemId);

    Page<Submission> findAllByUserEmail(String email, Pageable pageable);

    Optional<Submission> findByProblemId(Long problemId);
}
