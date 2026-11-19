package com.threadwork.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.threadwork.model.CustomerOrder;
import com.threadwork.model.OrderDesign;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
public final class OrderDtos {
  private OrderDtos() {}
  public record CustomerInfo(@NotBlank String name, @NotBlank String phone, @NotBlank String address, String note) {}
  public record DesignRequest(@NotNull @Pattern(regexp = "front|back") String side, @NotBlank String zone,
    @NotNull @Pattern(regexp = "print|embroidery") String method, @NotNull Float x, @NotNull Float y, @NotNull @Positive Float size,
    @NotBlank String imageUrl, String previewUrl, Float photoX, Float photoY) {}
  public record CreateOrderRequest(@NotNull UUID productId, @NotBlank String color, @NotNull @Pattern(regexp = "short|full") String sleeve,
    @NotBlank String size, @Min(1) @Max(100) int qty, @NotNull @Valid CustomerInfo customer,
    @NotEmpty @Size(max = 2, message = "at most one design per side") @Valid List<DesignRequest> designs) {}
  public record CreateOrderResponse(String code, int total) {}
  public record StatusRequest(@NotNull @Pattern(regexp = "new|in_production|shipped|delivered|cancelled") String status) {}

  public record DesignView(UUID id, String side, String zone, String method, float x, float y, float size,
    @JsonProperty("image_url") String imageUrl, @JsonProperty("preview_url") String previewUrl,
    @JsonProperty("photo_x") Float photoX, @JsonProperty("photo_y") Float photoY) {
    public static DesignView from(OrderDesign d) { return new DesignView(d.getId(), d.getSide(), d.getZone(), d.getMethod(), d.getX(), d.getY(), d.getSize(), d.getImageUrl(), d.getPreviewUrl(), d.getPhotoX(), d.getPhotoY()); }
  }
  public record OrderView(UUID id, String code, @JsonProperty("product_name") String productName, @JsonProperty("product_base") String productBase,
    String color, String sleeve, String size, int qty, int total, String status, CustomerInfo customer, List<DesignView> designs,
    @JsonProperty("created_at") Instant createdAt, @JsonProperty("product_image") String productImage) {
    public static OrderView from(CustomerOrder o) {
      return new OrderView(o.getId(), o.getCode(), o.getProductName(), o.getProductBase(), o.getColor(), o.getSleeve(), o.getSize(), o.getQty(), o.getTotal(), o.getStatus(),
        new CustomerInfo(o.getCustomerName(), o.getCustomerPhone(), o.getCustomerAddress(), o.getCustomerNote()),
        o.getDesigns().stream().map(DesignView::from).toList(), o.getCreatedAt(), o.getProductImageUrl());
    }
  }
}
