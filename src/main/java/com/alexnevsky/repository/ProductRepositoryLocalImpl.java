package com.alexnevsky.repository;

import com.alexnevsky.model.Product;
import com.alexnevsky.model.ProductCategory;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alex Nevsky
 *
 * Date: 12/10/2020
 */
public class ProductRepositoryLocalImpl {

  private static final List<Product> STORAGE = Lists.newArrayListWithCapacity(4096);

  public List<Product> find(final String name, final ProductCategory category) {
    return STORAGE.stream().filter(p -> p.getName().equals(name) && p.getCategory().equals(category)).collect(Collectors.toList());
  }

  public void saveAll(final List<Product> entities) {
    STORAGE.addAll(entities);
  }
}
