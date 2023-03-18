package de.propra.splitter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.propra.splitter.web.SplitterController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SplitterApplicationTests {

  @Autowired
  SplitterController controller;

}
