package com.hojoon.mysteam.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
@Slf4j
public class CacheConfig {

  @Bean
  public CaffeineCacheManager caffeineCacheManager() {
    CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
    caffeineCacheManager.setCaffeine(caffeineConfig());
    return caffeineCacheManager;
  }

  public Caffeine caffeineConfig() {
    return Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(1)).maximumSize(500)
        .evictionListener(
            (key, value, cause) -> log.info("cache {} : {} was evicted {}", key, value, cause))
        .weakValues();
  }
}
