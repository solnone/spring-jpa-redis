package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DataService {

  private final DataRepository dataRepository;

  @Cacheable(value = "allData")
  public List<Data> findAll() {
    return dataRepository.findAll();
  }

  @Cacheable(value = "data", key = "#id")
  public Optional<Data> findById(final Long id) {
    return dataRepository.findById(id);
  }

  @CacheEvict(value = "allData", allEntries = true)
  @CachePut(value = "data", key = "#data.id")
  public Data save(final Data data) {
    return dataRepository.save(data);
  }

  @CacheEvict(value = { "data", "allData" }, allEntries = true)
  public void deleteById(final Long id) {
    dataRepository.deleteById(id);
  }

}
