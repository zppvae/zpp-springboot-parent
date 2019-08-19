package org.zpp.springboot.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zpp.springboot.rabbitmq.common.ConfirmRabbitTemplate;
import org.zpp.springboot.rabbitmq.common.Constants;

/**
 * 发送方需要确认消息发送到队列的配置
 */
@Component
@Slf4j
@Profile(Constants.PUBLISHER_CONFIRM_PROFILE)
public class ProducerConfirmConfig {

    /**
     * 回调的template
     * @param connectionFactory
     * @return
     */
    @Bean
    @ConfirmRabbitTemplate
    RabbitTemplate rabbitTemplateWithConfirm(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)
            -> log.info("confirm callback id:{},ack:{},cause:{}", correlationData, ack, cause));
        //手动ACK
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey)
            -> log.info("return callback message：{},code:{},text:{}", message, replyCode, replyText));
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}