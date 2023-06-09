package com.pinellia;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pinellia.dao")
@Slf4j
public class PinelliaOaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PinelliaOaServerApplication.class, args);
    }

}
