package com.alexnevsky.datasource;

import com.alexnevsky.mapper.ProductMapper;
import com.alexnevsky.model.ProductDto;
import com.alexnevsky.service.ProductService;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alex Nevsky
 *
 * Date: 14/10/2020
 */
@Slf4j
@Component
public class ProductConsumer {

  public static final int BATCH_PROCESSING_SIZE = 16;
  public static final LinkedBlockingQueue<ProductDto> QUERY = new LinkedBlockingQueue<>();

  private ThreadPoolExecutor threadPool;
  private volatile boolean isStopped = false;

  private final ProductService productService;

  private final ProductMapper productMapper;

  @Autowired
  public ProductConsumer(ProductService productService, ProductMapper productMapper) {
    this.productService = productService;
    this.productMapper = productMapper;
  }

  @PostConstruct
  private void init() {
    this.threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(12);
    for (int i = 0; i < threadPool.getCorePoolSize(); ++i) {
      threadPool.execute(this::retrieveMessageFromQueue);
    }
  }

  public void stop() {
    isStopped = true;
    threadPool.shutdown();
    try {
      threadPool.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      log.error("Problem on await termination: ", e);
    }
  }

  public boolean isStopped() {
    return isStopped;
  }

  private void retrieveMessageFromQueue() {
    while (!Thread.currentThread().isInterrupted() && !isStopped()) {
      try {
        List<ProductDto> batch = Lists.newArrayListWithExpectedSize(BATCH_PROCESSING_SIZE);
        do {
          final ProductDto taken = QUERY.take();
          log.info("Taken: {}", taken);
          batch.add(taken);
        } while (batch.size() <= BATCH_PROCESSING_SIZE && !QUERY.isEmpty());
        processMessages(batch);
      } catch (Exception e) {
        log.error("Error during retrieving messages: ", e);
      }
    }
  }

  private void processMessages(List<ProductDto> messages) {
    productService.saveAll(messages.stream().map(productMapper::convert).collect(Collectors.toList()));
  }
}
