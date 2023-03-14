package de.propra.splitter.api;

import de.propra.splitter.config.SecurityConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@ImportAutoConfiguration(classes = SecurityConfig.class)
@WebMvcTest
public class TestApiController {

}
