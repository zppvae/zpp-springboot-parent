package org.zpp.springboot.test;

import com.mzt.logapi.starter.annotation.EnableLogRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLogRecord(tenant = "com.test")
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class,args);
    }
}
