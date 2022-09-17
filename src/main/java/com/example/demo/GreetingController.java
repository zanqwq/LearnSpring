package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
  private String template = "Hello, %s";
  private AtomicLong cnt = new AtomicLong();

  class Greeting {
    private final long id;
    private final String content;

    Greeting(long id, String content) {
      this.id = id;
      this.content = content;
    }

    public long getId() {
      return id;
    }
    public String getContent() {
      return content;
    }
  }

  @GetMapping("/hello")
  public String hello(@RequestParam(name = "name", defaultValue = "World") String name) {
    return String.format(template, name);
  }

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(name = "name", defaultValue = "World") String name) {
    return new Greeting(cnt.incrementAndGet(), String.format(template, name));
  }
}
