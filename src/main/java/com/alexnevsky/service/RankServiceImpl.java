package com.alexnevsky.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author Alex Nevsky
 *
 * Date: 14/10/2020
 */
@Service
public class RankServiceImpl implements RankService {

  private static final Map<String, Double> SCORE = new HashMap<>();

  static {
    SCORE.putAll(Map.of(
        "Apple", 99.2,
        "Amazon", 83.67,
        "Google", 65.3,
        "Ebay", 64.3,
        "Tesla", 100.0)
    );
  }

  @Override
  public double getScore(String provider) {
    return SCORE.containsKey(provider) ? SCORE.get(provider) : 0;
  }
}
