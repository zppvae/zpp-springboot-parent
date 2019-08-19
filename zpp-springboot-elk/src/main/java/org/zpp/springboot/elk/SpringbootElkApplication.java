package org.zpp.springboot.elk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootElkApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringbootElkApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        Logger logger = LoggerFactory.getLogger(SpringbootElkApplication.class);
        logger.info("测试log");

        for (int i = 0; i < 10; i++) {
            logger.error("something wrong. id={}; name=Ryan-{};", i, i);
        }
    }

}