package org.zpp.springboot.mqtt.component;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.zpp.springboot.mqtt.config.MqttConfig;

/**
 * 消息发送者
 */
@Component
@MessagingGateway(defaultRequestChannel = MqttConfig.MQTT_OUTBOUND_CHANNEL)
public interface MQTTProducer {

    void send(String data);

    /**
     * 发送信息
     * @param topic
     * @param qos
     *        0，只发送一次，消费者会丢消息
     *        1，消费者没收到消息之前会一直重试，但消费者有可能收到重复的消息
     *        2，在【1】的基础上去重，消费者只会收到1条消息
     * @param payload 消息
     */
    void send(@Header(MqttHeaders.TOPIC) String topic,
              @Header(MqttHeaders.QOS) int qos,
              String payload);
}