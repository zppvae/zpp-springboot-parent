package org.zpp.springboot.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.zpp.springboot.rabbitmq.common.Constants;
import org.zpp.springboot.rabbitmq.model.MessageData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 死信队列消费
 *
 * 获取消息头的某个字段：
 * @Header(name = "amqp_deliveryTag") long deliveryTag
 *
 * 获取消息头的所有字段：
 * @Headers Map<String, String> head
 *
 * 消息消费失败处理
 * 1、消费重试
 * 2、记录消息日志，进行人工补偿
 */
@Component
@Slf4j
public class Consumer {

    /**
     * 消费重试
     * @param headers
     * @param msg
     */
//    @RabbitListener(queues = Constants.QUEUE)
//    @SneakyThrows
//    public void consumerRetry(@Headers Map<String, Object> headers, @Payload MessageData msg) {
//        log.info("consumer retry receive message:{headers = [" + headers + "], msg = [" + msg + "]}");
//        throw new RuntimeException();
//    }

    /**
     * 延时消息
     * @param msg
     * @param message
     * @param channel
     */
    @RabbitListener(queues = Constants.REGISTER_QUEUE_NAME)
    @SneakyThrows
    public void listenerDelayQueue(@Payload MessageData msg, Message message, Channel channel) {
        log.info("[listenerDelayQueue 监听的消息] - [消费时间] - [{}] - [{}]", LocalDateTime.now(), msg.toString());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 消费消费失败后转入死信队列
     *
     * 捕获异常后不会进行消费重试
     * @param headers
     * @param msg
     */
    @RabbitListener(queues = Constants.QUEUE)
    @SneakyThrows
    public void process(@Headers Map<String, Object> headers, @Payload MessageData msg,
                        Message message, Channel channel) {
        log.info("consumer receive message:{headers = [" + headers + "], msg = [" + msg + "]}");
        try {
            //异常
            //int result  = 1/0;
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            //拒绝后转入死信队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);

            //应答，从队列中删除
            //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //重新发送消息到队尾
//            channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
//                                message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
//                                JSON.toJSONBytes(new Object()));
        }
    }

    /**
     * 监听死信队列消息
     * @param headers
     * @param msg
     * @param message
     * @param channel
     */
    @RabbitListener(queues =Constants.DEAD_QUEUE,containerFactory="rabbitListenerContainerFactory")
    @SneakyThrows
    public void deadListener(@Headers Map<String, Object> headers, @Payload MessageData msg,
                             Message message, Channel channel) {
        log.info("dead consumer receive message:{headers = [" + headers + "], msg = [" + msg + "]}");

        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动ack
        channel.basicAck(deliveryTag, false);

        log.info("dead consumer process message success !");
    }

}
