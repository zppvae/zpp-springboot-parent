package org.zpp.springboot.rabbitmq.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.zpp.springboot.rabbitmq.BaseTest;
import org.zpp.springboot.rabbitmq.common.Constants;
import org.zpp.springboot.rabbitmq.model.MessageData;

import java.time.LocalDateTime;

/**
 * @author zpp
 * @date 2019/7/1 15:17
 */
@Slf4j
public class TTLConsumerTest extends BaseTest {

    /**
     * 延迟消息
     */
    @Test
    @SneakyThrows
    public void testDelay(){
        MessageData data = new MessageData(1,"测试延迟消息");
        rabbitTemplate.convertAndSend(Constants.REGISTER_DELAY_EXCHANGE, Constants.DELAY_ROUTING_KEY, data, message -> {
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageData.class.getName());
            message.getMessageProperties().setExpiration(5 * 1000 + "");
            return message;
        });
        log.info("[发送延迟消息] - [消费时间] - [{}]", LocalDateTime.now());
        while (true){}
    }

}
