package com.alexnevsky;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.alexnevsky.model.Product;
import com.alexnevsky.model.ProductCategory;
import com.alexnevsky.model.ProductDto;
import com.alexnevsky.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Alex Nevsky
 *
 * Date: 15/10/2020
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductRepository productRepository;

  @Test
  public void testGet() throws Exception {
    // given
    List<ProductDto> payload = new ArrayList<>();
    ProductDto dto = new ProductDto();
    dto.setName("Pixel 5");
    dto.setCategory(ProductCategory.MOBILE_PHONE);
    payload.add(dto);

    given(productRepository.saveAll(any())).willReturn(List.of(productEntity(UUID.randomUUID(), dto)));
    given(productRepository.findByNameAndCategoryOrderByScoreDesc(dto.getName(), dto.getCategory())).willReturn(List.of(productEntity(UUID.randomUUID(), dto)));

    mockMvc.perform(get("/api/v1/products")
        .contentType("application/json")
        .param("name", dto.getName())
        .param("category", dto.getCategory().name()))
        .andExpect(status().isOk());

    // when
    List<Product> result = productRepository.findByNameAndCategoryOrderByScoreDesc(dto.getName(), dto.getCategory());

    // then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertThat(result.get(0).getName()).isEqualTo(dto.getName());
    assertThat(result.get(0).getCategory()).isEqualTo(dto.getCategory());
  }

  @Test
  public void testImport() throws Exception {
    // given
    List<ProductDto> payload = new ArrayList<>();
    ProductDto dto = new ProductDto();
    dto.setName("Pixel 5");
    dto.setCategory(ProductCategory.MOBILE_PHONE);
    payload.add(dto);

    given(productRepository.saveAll(any())).willReturn(List.of(productEntity(UUID.randomUUID(), dto)));
    given(productRepository.findByNameAndCategoryOrderByScoreDesc(dto.getName(), dto.getCategory())).willReturn(List.of(productEntity(UUID.randomUUID(), dto)));

    mockMvc.perform(put("/api/v1/products")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(payload)))
        .andExpect(status().isOk());

    // when
    List<Product> result = productRepository.findByNameAndCategoryOrderByScoreDesc(dto.getName(), dto.getCategory());

    // then
    assertNotNull(result);
    assertEquals(1, result.size());
    assertThat(result.get(0).getName()).isEqualTo(dto.getName());
    assertThat(result.get(0).getCategory()).isEqualTo(dto.getCategory());
  }

  private Product productEntity(UUID id, ProductDto dto) {
    Product product = new Product();
    product.setId(id);
    product.setName(dto.getName());
    product.setCategory(dto.getCategory());
    return product;
  }
}
