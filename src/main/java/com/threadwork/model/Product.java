package com.threadwork.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name = "products") @Getter @Setter @NoArgsConstructor
public class Product {
  @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
  @Column(nullable = false) private String name;
  @Column(nullable = false) private String section;   // Men | Women
  @Column(nullable = false) private String base;      // tee | polo | drop | hoodie | wtop (picks the 3D shape)
  @Column(nullable = false) private int price;
  @Column(nullable = false) private String color;
  @Column(nullable = false) private boolean sleeves;
  @Column(nullable = false) private boolean active = true;
  @Column(name = "image_url") private String imageUrl;   // e.g. /uploads/3f2c.jpg
  @Column(name = "created_at", insertable = false, updatable = false) private Instant createdAt;
}
