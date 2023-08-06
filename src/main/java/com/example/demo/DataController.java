package com.example.demo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  private final DataService dataService;

  @GetMapping("/data")
  @ResponseStatus(HttpStatus.OK)
  @Transactional(readOnly = true)
  public List<Data> findAll() {
    return dataService.findAll();
  }

  @GetMapping("/data/{id}")
  @Transactional(readOnly = true)
  public ResponseEntity<Data> findById(@PathVariable final Long id) {
    return dataService.findById(id).map(data -> ResponseEntity.ok().body(data))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/data")
  @ResponseStatus(HttpStatus.CREATED)
  @Transactional
  public Data add(@RequestBody final Data data) {
    return dataService.save(data);
  }

  @PutMapping("/data/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Transactional
  public Data save(@PathVariable final Long id, @RequestBody final Data data) {
    data.setId(id);
    return dataService.save(data);
  }

  @DeleteMapping("/data/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Transactional
  public void deleteById(@PathVariable final Long id) {
    dataService.deleteById(id);
  }

}
