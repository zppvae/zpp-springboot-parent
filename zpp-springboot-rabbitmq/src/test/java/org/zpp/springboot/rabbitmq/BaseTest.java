package org.zpp.springboot.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class BaseTest {

    protected AtomicInteger id = new AtomicInteger();

    @Autowired
    protected RabbitTemplate rabbitTemplate;

}
