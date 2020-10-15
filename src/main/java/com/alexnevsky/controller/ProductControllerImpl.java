package com.alexnevsky.controller;

import com.alexnevsky.datasource.ProductConsumer;
import com.alexnevsky.model.ProductCategory;
import com.alexnevsky.model.ProductDto;
import com.alexnevsky.service.ProductService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alex Nevsky
 *
 * Date: 12/10/2020
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductControllerImpl implements ProductController {

  private final ProductService productService;

  @Override
  @GetMapping
  public List<ProductDto> get(@RequestParam final String name, @RequestParam final ProductCategory category) {
    return productService.find(name, category);
  }

  @Override
  @PutMapping
  public void importBatch(@RequestBody final List<ProductDto> products) {
    products.forEach(p -> {
      try {
        ProductConsumer.QUERY.put(p);
      } catch (InterruptedException e) {
        log.error(e.getMessage(), e);
      }
    });
  }
}
