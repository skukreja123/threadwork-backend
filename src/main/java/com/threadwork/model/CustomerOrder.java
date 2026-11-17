package com.threadwork.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity @Table(name = "orders") @Getter @Setter @NoArgsConstructor
public class CustomerOrder {
  @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
  @Column(nullable = false, unique = true) private String code;
  @Column(name = "user_id") private UUID userId;
  @Column(name = "product_id") private UUID productId;
  @Column(name = "product_name", nullable = false) private String productName;
  @Column(name = "product_base", nullable = false) private String productBase;
  @Column(nullable = false) private String color;
  @Column(nullable = false) private String sleeve;
  @Column(nullable = false) private String size;
  @Column(nullable = false) private int qty;
  @Column(nullable = false) private int total;
  @Column(nullable = false) private String status = "new";
  @Column(name = "customer_name", nullable = false) private String customerName;
  @Column(name = "customer_phone", nullable = false) private String customerPhone;
  @Column(name = "customer_address", nullable = false) private String customerAddress;
  @Column(name = "customer_note") private String customerNote;
  @Column(name = "created_at", insertable = false, updatable = false) private Instant createdAt;
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) private List<OrderDesign> designs = new ArrayList<>();
}
