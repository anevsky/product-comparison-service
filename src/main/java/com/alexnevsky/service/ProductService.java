package com.alexnevsky.service;

import com.alexnevsky.model.ProductCategory;
import com.alexnevsky.model.ProductDto;
import com.alexnevsky.model.Product;
import java.util.List;

/**
 * @author Alex Nevsky
 *
 * Date: 12/10/2020
 */
public interface ProductService {

  List<ProductDto> find(final String name, final ProductCategory category);

  void saveAll(List<Product> collect);
}
