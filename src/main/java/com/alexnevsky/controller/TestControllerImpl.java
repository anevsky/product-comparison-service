package com.alexnevsky.controller;

import com.alexnevsky.datasource.ProductProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alex Nevsky
 *
 * Date: 16/10/2020
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/test")
public class TestControllerImpl implements TestController {

  private final ProductProducer productProducer;

  @Override
  @PostMapping("/products/import")
  public void productsImport() {
    productProducer.run();
  }
}
