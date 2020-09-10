package com.nic.zuul;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableDiscoveryClient//启用
@SpringBootApplication
public class ZuulApplication {
    private final static Logger log = LoggerFactory.getLogger(ZuulApplication.class);
    public static void main(String[] args) {
        log.info("test");
        SpringApplication.run(ZuulApplication.class, args);

    }
}
