package com.threadwork.service;
import com.threadwork.dto.OrderDtos.*;
import com.threadwork.exception.ApiException;
import com.threadwork.model.CustomerOrder;
import com.threadwork.model.OrderDesign;
import com.threadwork.model.Product;
import com.threadwork.repository.CustomerOrderRepository;
import com.threadwork.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
@Service @RequiredArgsConstructor
public class OrderService {
  private static final int FULL_SLEEVE_EXTRA = 300, EMBROIDERY_EXTRA = 400;
  private final CustomerOrderRepository orders; private final ProductRepository products;

  /** The price is always recalculated here. The browser's total is never trusted. */
  @Transactional public CreateOrderResponse create(UUID userId, CreateOrderRequest r) {
    Product p = products.findById(r.productId()).filter(Product::isActive)
      .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "This product is not available"));
    if (r.designs().stream().map(DesignRequest::side).distinct().count() != r.designs().size())
      throw new ApiException(HttpStatus.BAD_REQUEST, "Only one design per side");
    int embroidered = (int) r.designs().stream().filter(d -> "embroidery".equals(d.method())).count();
    int unit = p.getPrice() + ("full".equals(r.sleeve()) && p.isSleeves() ? FULL_SLEEVE_EXTRA : 0) + embroidered * EMBROIDERY_EXTRA;

    CustomerOrder o = new CustomerOrder();
    o.setCode(newCode()); o.setUserId(userId); o.setProductId(p.getId()); o.setProductName(p.getName()); o.setProductBase(p.getBase()); o.setProductImageUrl(p.getImageUrl());
    o.setColor(r.color()); o.setSleeve(r.sleeve()); o.setSize(r.size()); o.setQty(r.qty()); o.setTotal(unit * r.qty());
    o.setCustomerName(r.customer().name().trim()); o.setCustomerPhone(r.customer().phone().trim());
    o.setCustomerAddress(r.customer().address().trim()); o.setCustomerNote(r.customer().note());
    for (DesignRequest d : r.designs()) {
      OrderDesign od = new OrderDesign();
      od.setOrder(o); od.setSide(d.side()); od.setZone(d.zone()); od.setMethod(d.method());
      od.setX(d.x()); od.setY(d.y()); od.setSize(d.size()); od.setImageUrl(d.imageUrl()); od.setPreviewUrl(d.previewUrl()); od.setPhotoX(d.photoX()); od.setPhotoY(d.photoY());
      o.getDesigns().add(od);
    }
    orders.save(o);
    return new CreateOrderResponse(o.getCode(), o.getTotal());
  }
  @Transactional(readOnly = true) public List<OrderView> mine(UUID userId) { return orders.findByUserIdOrderByCreatedAtDesc(userId).stream().map(OrderView::from).toList(); }
  @Transactional(readOnly = true) public List<OrderView> all() { return orders.findAllByOrderByCreatedAtDesc().stream().map(OrderView::from).toList(); }
  @Transactional public OrderView updateStatus(UUID id, String status) {
    CustomerOrder o = orders.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Order not found"));
    o.setStatus(status);
    return OrderView.from(o);
  }
  private String newCode() {
    String code;
    do { code = "TW-" + String.format("%06d", ThreadLocalRandom.current().nextInt(1_000_000)); } while (orders.existsByCode(code));
    return code;
  }
}
