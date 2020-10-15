package com.alexnevsky.repository;

import com.alexnevsky.model.Product;
import com.alexnevsky.model.ProductCategory;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Nevsky
 *
 * Date: 12/10/2020
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, UUID> {

  List<Product> findByNameAndCategoryOrderByScoreDesc(final String name, final ProductCategory category);
}
