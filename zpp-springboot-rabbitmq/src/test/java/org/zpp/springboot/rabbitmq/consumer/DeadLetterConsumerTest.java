package org.zpp.springboot.rabbitmq.consumer;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.zpp.springboot.rabbitmq.BaseTest;
import org.zpp.springboot.rabbitmq.common.Constants;
import org.zpp.springboot.rabbitmq.model.MessageData;

/**
 * 死信队列
 */
public class DeadLetterConsumerTest extends BaseTest {

    /**
     * 测试死信队列
     */
    @Test
    @SneakyThrows
    public void testDeadLetter() {
        for (int i = 0; i < 1; i++) {
            Thread.sleep(1000);
            rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY,
                    new MessageData(id.getAndIncrement(), "消息内容"));
        }
        while (true){}
    }

}