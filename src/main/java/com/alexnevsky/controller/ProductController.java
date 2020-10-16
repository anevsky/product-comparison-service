package com.alexnevsky.controller;

import com.alexnevsky.model.ProductCategory;
import com.alexnevsky.model.ProductDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

/**
 * @author Alex Nevsky
 *
 * Date: 12/10/2020
 */
public interface ProductController {

  /**
   * Find product by params.
   */
  @ApiOperation(httpMethod = "GET",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      value = "Get products by name and category.",
      tags = {"product-controller"},
      response = List.class)
  @ApiImplicitParams({
          @ApiImplicitParam(
              name = "name",
              value = "Product name",
              required = true,
              dataType = "string",
              paramType = "query"),
      @ApiImplicitParam(
          name = "category",
          value = "Product category",
          required = true,
          dataType = "string",
          paramType = "query")
  })
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK,
          message = "OK. Result list",
          response = List.class)
  })
  List<ProductDto> get(final String name, final ProductCategory category);

  /**
   * Import products.
   */
  @ApiOperation(httpMethod = "PUT",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      value = "Import products.",
      tags = {"product-controller"})
  @ApiImplicitParams({
      @ApiImplicitParam(
          name = "products",
          value = "Products",
          required = true,
          dataType = "ProductDto",
          paramType = "body")
  })
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK,
          message = "OK. Result list",
          response = List.class)
  })
  void importBatch(List<ProductDto> products);
}
