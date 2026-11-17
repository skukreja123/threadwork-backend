package com.threadwork.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name = "users") @Getter @Setter @NoArgsConstructor
public class User {
  @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
  @Column(nullable = false) private String name;
  @Column(nullable = false, unique = true) private String email;
  @Column(name = "password_hash", nullable = false) private String passwordHash;
  @Enumerated(EnumType.STRING) @Column(nullable = false) private Role role = Role.customer;
  @Column(nullable = false) private String plan = "free";
  @Column(name = "ai_used", nullable = false) private int aiUsed;
  @Column(name = "created_at", insertable = false, updatable = false) private Instant createdAt;
}
