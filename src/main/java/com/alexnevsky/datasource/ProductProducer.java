package com.alexnevsky.datasource;

import com.alexnevsky.model.ProductCategory;
import com.alexnevsky.model.ProductDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * @author Alex Nevsky
 *
 * Date: 14/10/2020
 */
@Slf4j
@Component
public class ProductProducer {

  private static final int TEST_PRODUCTS_SIZE = 10000;
  private static final RestTemplate REST_TEMPLATE = new RestTemplate();
  private static final String IMPORT_URL = "/api/v1/products";
  private static final List<String> PRODUCT_NAMES = new ArrayList<>(100);
  private static final List<String> PRODUCT_PROVIDERS = new ArrayList<>(3);

  static {
    REST_TEMPLATE.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080"));
    PRODUCT_PROVIDERS.addAll(List.of("Amazon", "Google", "Ebay", "Adidas", "Apple"));
    for (int i = 0; i < TEST_PRODUCTS_SIZE; ++i) {
      PRODUCT_NAMES.add(RandomStringUtils.randomAlphabetic(4, 20));
    }
  }

  public void run() {
    RequestEntity<List<ProductDto>> requestEntity = RequestEntity
        .put(REST_TEMPLATE.getUriTemplateHandler().expand(IMPORT_URL))
        .body(buildTestProducts());
    REST_TEMPLATE.exchange(requestEntity, Void.class);
  }

  private static List<ProductDto> buildTestProducts() {
    List<ProductDto> result = new ArrayList<>(TEST_PRODUCTS_SIZE);
    for (int i = 0; i < TEST_PRODUCTS_SIZE; ++i) {
      final int productCategoryIndex = RandomUtils.nextInt(0, ProductCategory.values().length);
      final int productNameIndex = RandomUtils.nextInt(0, PRODUCT_NAMES.size());
      final int productSourceIndex = RandomUtils.nextInt(0, PRODUCT_PROVIDERS.size());
      ProductDto product = new ProductDto(
          UUID.randomUUID(),
          PRODUCT_NAMES.get(productNameIndex),
          ProductCategory.valueOf(ProductCategory.values()[productCategoryIndex].name()),
          PRODUCT_PROVIDERS.get(productSourceIndex),
          0
      );
      result.add(product);
    }

    result.add(new ProductDto(
        UUID.randomUUID(),
        "iPhone 12",
        ProductCategory.MOBILE_PHONE,
        "Apple",
        0
    ));
    result.add(new ProductDto(
        UUID.randomUUID(),
        "iPhone 12",
        ProductCategory.MOBILE_PHONE,
        "Amazon",
        0
    ));
    result.add(new ProductDto(
        UUID.randomUUID(),
        "iPhone 12",
        ProductCategory.MOBILE_PHONE,
        "Ebay",
        0
    ));
    result.add(new ProductDto(
        UUID.randomUUID(),
        "iPhone 12 Pro",
        ProductCategory.MOBILE_PHONE,
        "Apple",
        0
    ));

    return result;
  }

}
