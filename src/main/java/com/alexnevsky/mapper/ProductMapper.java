package com.alexnevsky.mapper;

import com.alexnevsky.model.Product;
import com.alexnevsky.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Alex Nevsky
 *
 * Date: 14/10/2020
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

  ProductDto convert(Product source);

  Product convert(ProductDto source);
}
