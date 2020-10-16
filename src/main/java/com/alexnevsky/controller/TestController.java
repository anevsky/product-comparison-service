package com.alexnevsky.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

/**
 * @author Alex Nevsky
 *
 * Date: 16/10/2020
 */
public interface TestController {

  /**
   * Import products.
   */
  @ApiOperation(httpMethod = "POST",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      value = "Import 10000+ test products.",
      tags = {"test-controller"})
  @ApiResponses({
      @ApiResponse(code = HttpServletResponse.SC_OK,
          message = "OK.")
  })
  void productsImport();
}
