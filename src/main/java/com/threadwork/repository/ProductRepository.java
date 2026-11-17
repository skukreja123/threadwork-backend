package com.threadwork.repository;
import com.threadwork.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
public interface ProductRepository extends JpaRepository<Product, UUID> {
  List<Product> findByActiveTrueOrderByCreatedAtAsc();
  List<Product> findAllByOrderByCreatedAtAsc();
}
