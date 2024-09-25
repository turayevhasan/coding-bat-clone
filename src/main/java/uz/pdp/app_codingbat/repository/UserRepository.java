package uz.pdp.app_codingbat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.app_codingbat.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    Page<User> findUsersByCreatedAtBetween(
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
    @Query("select u from User u where lower(u.email) like lower(concat('%', :keyword, '%'))")
    Page<User> search(@Param("keyword") String keyword, Pageable pageable);

}
