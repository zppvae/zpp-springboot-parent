package org.zpp.springboot.rabbitmq.producer;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zpp.springboot.rabbitmq.common.Constants;
import org.zpp.springboot.rabbitmq.model.MessageData;

import java.time.LocalDateTime;


/**
 * 发送方回调
 */
@Slf4j
@Service
public class MessageProducer{

    /**
     * 消息缓存，实际存入db
     */
    public Cache<Integer,MessageData> cache = CacheUtil.newFIFOCache(10);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final RabbitTemplate.ConfirmCallback confirmCallback =
            new RabbitTemplate.ConfirmCallback() {
                @Override
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    log.info("接收到发送者回调确认");
                    //业务id
                    int bizId = Integer.valueOf(correlationData.getId());
                    if (ack) {
                        log.info("消息发送到exchange成功");
                        updateStatus(bizId,MessageData.STATUS_SUCCESS);
                        cache.remove(bizId);
                    } else {
                        log.error("消息发送失败，由定时任务处理失败消息");
                        updateStatus(bizId,MessageData.STATUS_FAIL);
                    }
                }
            };

    /**
     * 消息从交换器发送到对应队列失败时触发
     *
     * ps：发送一个不存在routingKey到队列
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    private final RabbitTemplate.ReturnCallback returnCallback =
            new RabbitTemplate.ReturnCallback() {
                @Override
                public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                    log.info("[消息路由到队列失败] - [message:{},exchange:{},routingKey:{}]",
                            new String(message.getBody()),exchange,routingKey);
                }
            };


    public void send(MessageData data) {
        //添加到消息处理集合中
        cache.put(data.getId(),data);
        Message message = MessageBuilder.withBody(JSON.toJSONBytes(data))
                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                        .setContentEncoding("utf-8").setMessageId(data.getId()+"").build();
        CorrelationData correlationData = new CorrelationData(data.getId()+"");

        rabbitTemplate.setConfirmCallback(this.confirmCallback);
        rabbitTemplate.setReturnCallback(this.returnCallback);
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY, message, correlationData);
        log.info("发送消息完成：{}",LocalDateTime.now());
    }

    private void updateStatus(int bizId,int status){
        MessageData data = cache.get(bizId);
        data.setStatus(status);
        cache.put(bizId,data);
    }

}
