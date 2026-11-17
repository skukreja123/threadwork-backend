package com.threadwork.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name = "messages") @Getter @Setter @NoArgsConstructor
public class Message {
  @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
  @Column(nullable = false) private String name;
  @Column(nullable = false) private String email;
  @Column(nullable = false) private String body;
  @Column(name = "created_at", insertable = false, updatable = false) private Instant createdAt;
}
