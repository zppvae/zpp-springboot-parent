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
@ActiveProfiles(Constants.PUBLISHER_CONFIRM_PROFILE)
public class ProducerConfirmTest {

    protected AtomicInteger id = new AtomicInteger();

    @Autowired
    @ConfirmRabbitTemplate
    RabbitTemplate rabbitTemplateWithConfirm;

    /**
     * 发送需要confirm的消息
     */
    @Test
    @SneakyThrows
    public void sendConfirmMessage() {
        Thread.sleep(5000);
        Executors.newSingleThreadScheduledExecutor()
            .scheduleAtFixedRate(
                this::send, 0, 3, TimeUnit.SECONDS);
        Thread.sleep(12 * 1000);
    }

    private void send() {
        try {
            final int i = id.getAndIncrement();
            rabbitTemplateWithConfirm.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY,
                new MessageData(i, "confirm message id:" + i),
                new CorrelationData(Integer.toString(i)));
        } catch (Exception e) {
            log.error("send error !", e);
        }
    }

}