package com.threadwork.repository;
import com.threadwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
  List<User> findAllByOrderByCreatedAtDesc();
  /** Atomic, so two requests at once cannot push a user past the plan limit. Returns 0 when the limit is reached. */
  @Modifying @Query("update User u set u.aiUsed = u.aiUsed + 1 where u.id = :id and u.aiUsed < :limit")
  int incrementAi(@Param("id") UUID id, @Param("limit") int limit);
}
