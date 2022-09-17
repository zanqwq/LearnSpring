package com.example.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class EmployeeRes {
  @JsonIgnoreProperties(ignoreUnknown = true)
  // class DataItem {
  //   private int id;
  //   private String employee_name;
  //   private String employee_age;
  //   private String employee_salary;
  //   private String profile_image;
  // }
  private String status;
  private String message;
  // private DataItem[] data;

  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return String.format("EmployeeRes{status = %s, message = %s}", status, message);
  }
}


@Service
public class ConsumeRestService {
  private Logger logger = LoggerFactory.getLogger(ConsumeRestService.class);

  @Bean
  public RestTemplate resetTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean
  public CommandLineRunner run(RestTemplate restTemplate) {
    System.out.println("@@@@ service run @@@@");
    return arg -> {
      System.out.println("@@@@ command line runner run @@@@");
      EmployeeRes quote = restTemplate.getForObject("https://dummy.restapiexample.com/api/v1/employees", EmployeeRes.class);
      System.out.println("run done");
      logger.info(quote.toString());
    };
  }
}
