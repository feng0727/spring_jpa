package com.ccb.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @author yangfei
 * @date 2019/3/21
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.ccb.domain", "uyun.springboot.consumer"})
public class StartApplication {

    public static void main(String[] args) {
        String workdir = System.getProperty("work.dir", System.getProperty("user.dir"));
        System.setProperty("work.dir", workdir);
        new SpringApplicationBuilder(StartApplication.class).main(StartApplication.class)
                .properties("spring.config.name:domain-main-base,domain-main-config").build().run(args);
    }

}
