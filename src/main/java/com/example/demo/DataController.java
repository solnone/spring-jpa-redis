package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RequestMapping("/api")
@AllArgsConstructor
@RestController
public class DataController {
  private final DataRepository dataRepository;

  @Cacheable(value = "allData")
  @GetMapping("/data")
  @Transactional(readOnly = true)
  public List<Data> findAll() {
    return dataRepository.findAll();
  }

  @Cacheable(value = "data", key = "#id")
  @GetMapping("/data/{id}")
  @Transactional(readOnly = true)
  public Optional<Data> findById(@PathVariable final Long id) {
    return dataRepository.findById(id);
  }

  @CacheEvict(value = "allData", allEntries = true)
  @CachePut(value = "data", key = "#data.id")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/data")
  @Transactional
  public Data add(@RequestBody final Data data) {
    return dataRepository.save(data);
  }

  @CacheEvict(value = "allData", allEntries = true)
  @CachePut(value = "data", key = "#data.id")
  @PutMapping("/data/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Transactional
  public Data save(@PathVariable final Long id, @RequestBody final Data data) {
    data.setId(id);
    return dataRepository.save(data);
  }

  @CacheEvict(value = { "data", "allData" }, allEntries = true)
  @DeleteMapping("/data/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Transactional
  public void del(@PathVariable final Long id) {
    dataRepository.deleteById(id);
  }
}
