package com.threadwork.service;
import com.threadwork.dto.ProductDtos.*;
import com.threadwork.exception.ApiException;
import com.threadwork.model.Product;
import com.threadwork.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
@Service @RequiredArgsConstructor
public class ProductService {
  private final ProductRepository products;
  @Transactional(readOnly = true) public List<ProductView> listActive() { return products.findByActiveTrueOrderByCreatedAtAsc().stream().map(ProductView::from).toList(); }
  @Transactional(readOnly = true) public List<ProductView> listAll() { return products.findAllByOrderByCreatedAtAsc().stream().map(ProductView::from).toList(); }
  @Transactional public ProductView create(ProductRequest r) { Product p = new Product(); apply(p, r); return ProductView.from(products.save(p)); }
  @Transactional public ProductView update(UUID id, ProductRequest r) { Product p = find(id); apply(p, r); return ProductView.from(p); }
  @Transactional public void delete(UUID id) { products.delete(find(id)); }
  private Product find(UUID id) { return products.findById(id).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Product not found")); }
  private void apply(Product p, ProductRequest r) {
    p.setName(r.name().trim()); p.setSection(r.section()); p.setBase(r.base()); p.setPrice(r.price()); p.setColor(r.color());
    p.setSleeves(r.sleeves() && !r.base().equals("hoodie") && !r.base().equals("wtop"));   // hoodie and women's top have fixed sleeves
    p.setActive(r.active() == null || r.active());
    String img = r.imageUrl() == null || r.imageUrl().isBlank() ? null : r.imageUrl().trim();
    if (img != null && !img.startsWith("/uploads/")) throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid image");   // only images uploaded through this API
    p.setImageUrl(img);
  }
}
