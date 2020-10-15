package com.alexnevsky.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Alex Nevsky
 *
 * Date: 14/10/2020
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@TypeAlias(value = "Product")
@CompoundIndex(name = "product_find_ordered_score", def = "{'name' : 1, 'category': 1, 'score': -1}")
public class Product {

  @Id
  @EqualsAndHashCode.Include
  private UUID id;
  private String name;
  private ProductCategory category;
  private String provider;
  private double score;
}
