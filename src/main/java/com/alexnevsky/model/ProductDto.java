package com.alexnevsky.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * @author Alex Nevsky
 *
 * Date: 12/10/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

  private UUID id;
  @NonNull
  private String name;
  @NonNull
  private ProductCategory category;
  private String provider;
  private double score;
}
