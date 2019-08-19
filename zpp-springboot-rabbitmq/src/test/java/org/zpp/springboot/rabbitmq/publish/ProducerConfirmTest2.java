package org.zpp.springboot.rabbitmq.publish;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.zpp.springboot.rabbitmq.common.ConfirmRabbitTemplate;
import org.zpp.springboot.rabbitmq.common.Constants;
import org.zpp.springboot.rabbitmq.model.MessageData;
import org.zpp.springboot.rabbitmq.producer.MessageProducer;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ProducerConfirmTest2 {

    protected AtomicInteger id = new AtomicInteger(1);


    @Autowired
    MessageProducer messageProducer;

    /**
     * 发送者确认消息
     *
     */
    @Test
    @SneakyThrows
    public void sendConfirmMessage2(){
        MessageData data = new MessageData(id.getAndIncrement(),"测试发送确认消息");
        messageProducer.send(data);
        while (true){}
    }

}