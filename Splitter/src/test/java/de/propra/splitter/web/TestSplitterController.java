package de.propra.splitter.web;

import de.propra.splitter.config.SecurityConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@ImportAutoConfiguration(classes = SecurityConfig.class)
@WebMvcTest(SplitterController.class)
public class TestSplitterController {

}
