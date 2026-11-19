package com.threadwork.dto;
import com.threadwork.model.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
public final class ProductDtos {
  private ProductDtos() {}
  public record ProductRequest(@NotBlank String name, @NotNull @Pattern(regexp = "Men|Women") String section,
    @NotNull @Pattern(regexp = "tee|polo|drop|hoodie|wtop") String base, @Min(0) int price, @NotBlank String color, boolean sleeves, Boolean active, String imageUrl) {}
  public record ProductView(UUID id, String name, String section, String base, int price, String color, boolean sleeves, boolean active, String imageUrl) {
    public static ProductView from(Product p) { return new ProductView(p.getId(), p.getName(), p.getSection(), p.getBase(), p.getPrice(), p.getColor(), p.isSleeves(), p.isActive(), p.getImageUrl()); }
  }
}
