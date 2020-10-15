package com.alexnevsky.repository;

import static java.util.Objects.isNull;

import com.alexnevsky.model.Product;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * @author Alex Nevsky
 *
 * Date: 15/10/2020
 */
@Component
public class ProductBeforeSaveListener extends AbstractMongoEventListener<Product> {

  @Override
  public void onBeforeConvert(BeforeConvertEvent<Product> event) {
    if (isNull(event.getSource().getId())) {
      event.getSource().setId(UUID.randomUUID());
    }
  }
}