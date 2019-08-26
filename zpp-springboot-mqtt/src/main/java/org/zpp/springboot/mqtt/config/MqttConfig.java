package org.zpp.springboot.mqtt.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
@Slf4j
public class MqttConfig {
    private static final byte[] WILL_DATA;

    static {
        WILL_DATA = "offline".getBytes();
    }

    public static final String MQTT_INBOUND_CHANNEL = "mqttInboundChannel";


    public static final String MQTT_OUTBOUND_CHANNEL = "mqttOutboundChannel";

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.url}")
    private String url;

    @Value("${mqtt.defaultTopic}")
    private String defaultTopic;

    @Value("${mqtt.producer.clientId}")
    private String producerClientId;

    @Value("${mqtt.consumer.clientId}")
    private String consumerClientId;

    /**
     * 连接mqtt配置
     * @return
     */
    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // false，服务器会保留客户端的连接记录
        // true，表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setServerURIs(StringUtils.split(url, ","));
        //超时时间 单位为秒
        options.setConnectionTimeout(10);
        //会话心跳时间 单位: s, 间隔时间：1.5*20秒向客户端发送心跳判断客户端是否在线
        options.setKeepAliveInterval(20);
        //设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
        options.setWill("willTopic", WILL_DATA, 2, false);
        return options;
    }


    /**
     * MQTT客户端
     *
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    /**
     * 发送者消息通道
     *
     */
    @Bean(name = MQTT_OUTBOUND_CHANNEL)
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * 发送者消息处理
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = MQTT_OUTBOUND_CHANNEL)
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                producerClientId,
                mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(defaultTopic);
        return messageHandler;
    }

    /**
     * 消息订阅
     * @return
     */
    @Bean
    public MessageProducer inbound() {
        // 可同时消费（订阅）多个Topic
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        consumerClientId, mqttClientFactory(),
                        StringUtils.split(defaultTopic, ","));
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        // 设置订阅通道
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    /**
     * 消费者消息通道
     * @return
     */
    @Bean(name = MQTT_INBOUND_CHANNEL)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }

    /**
     * 消费者消息处理
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = MQTT_INBOUND_CHANNEL)
    public MessageHandler mqttInbound() {
        return (message -> log.info("[消息] - [{}]",message.getPayload()));
    }
}