package com.threadwork.repository;
import com.threadwork.model.CustomerOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {
  @EntityGraph(attributePaths = "designs") List<CustomerOrder> findAllByOrderByCreatedAtDesc();
  @EntityGraph(attributePaths = "designs") List<CustomerOrder> findByUserIdOrderByCreatedAtDesc(UUID userId);
  boolean existsByCode(String code);
}
