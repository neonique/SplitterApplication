package de.propra.splitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class })
public class SplitterApplication {

  public static void main(String[] args) {
    SpringApplication.run(SplitterApplication.class, args);
  }

}
