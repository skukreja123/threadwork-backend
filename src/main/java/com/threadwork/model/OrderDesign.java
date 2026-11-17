package com.threadwork.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
@Entity @Table(name = "order_designs") @Getter @Setter @NoArgsConstructor
public class OrderDesign {
  @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
  @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "order_id") private CustomerOrder order;
  @Column(nullable = false) private String side;      // front | back
  @Column(nullable = false) private String zone;
  @Column(nullable = false) private String method;    // print | embroidery
  @Column(nullable = false) private float x;
  @Column(nullable = false) private float y;
  @Column(nullable = false) private float size;
  @Column(name = "image_url", nullable = false) private String imageUrl;
  @Column(name = "preview_url") private String previewUrl;
}
