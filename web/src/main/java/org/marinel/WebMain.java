package org.marinel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class WebMain {

    public static void main(String[] args) {
        log.info("WebMain class called");
        SpringApplication.run(WebMain.class, args);
    }
}
