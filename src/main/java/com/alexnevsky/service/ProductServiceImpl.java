package com.alexnevsky.service;

import com.alexnevsky.cache.LruCache;
import com.alexnevsky.mapper.ProductMapper;
import com.alexnevsky.model.Product;
import com.alexnevsky.model.ProductCategory;
import com.alexnevsky.model.ProductDto;
import com.alexnevsky.repository.ProductRepository;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Alex Nevsky
 *
 * Date: 12/10/2020
 */
@Slf4j
@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private static final int FIND_CACHE_SIZE = 1024;

  // Can be replaced by Guava Cache or Redis
  private final Map<String, List<ProductDto>> productsCache = Collections.synchronizedMap(
      new LruCache<>(FIND_CACHE_SIZE)
  );

  private final RankService rankService;

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  @Override
  public List<ProductDto> find(final String name, final ProductCategory category) {
    List<ProductDto> result;

    final String cacheKey = buildCacheKey(name, category);
    if (productsCache.containsKey(cacheKey)) {
      result = productsCache.get(cacheKey);
      log.info("Found products with name = {}, category = {} from cache : {}", name, category, result);
      return result;
    }

    List<Product> products = productRepository.findByNameAndCategoryOrderByScoreDesc(name, category);
    result = products.stream().map(productMapper::convert).collect(Collectors.toList());
    log.info("Found products with name = {}, category = {} from repo : {}", name, category, result);
    productsCache.put(cacheKey, result);

    return result;
  }

  @Override
  public void saveAll(List<Product> entities) {
    entities.forEach(e-> {
      e.setScore(rankService.getScore(e.getProvider()));
      productsCache.remove(buildCacheKey(e.getName(), e.getCategory()));
    });
    productRepository.saveAll(entities);
  }

  private String buildCacheKey(final String name, final ProductCategory category) {
    return name + category;
  }
}
