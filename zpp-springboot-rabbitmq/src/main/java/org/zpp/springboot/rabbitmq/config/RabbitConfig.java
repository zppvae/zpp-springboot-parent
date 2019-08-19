package org.zpp.springboot.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zpp.springboot.rabbitmq.common.Constants;

import java.util.HashMap;
import java.util.Map;


/**
 * 延迟队列：
 *      订单业务： 在电商/点餐中，都有下单后 30 分钟内没有付款，就自动取消订单。
 *      短信通知： 下单成功后 60s 之后给用户发送短信通知。
 *      失败重试： 业务操作失败后，间隔一定的时间进行失败重试。
 * Description:
 * @author zpp
 * @date   2017年5月3日
 */
@Configuration  
public class RabbitConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 消费者监听器
     * 消费者的处理逻辑不是在自己的线程池中执行而是直接在client线程池中处理，这样最明显的是省去了线程的上下文切换的开销
     * @param connectionFactory
     * @return
     */
    @Bean
    DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        final DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory = new DirectRabbitListenerContainerFactory();
        directRabbitListenerContainerFactory.setConsumersPerQueue(Runtime.getRuntime().availableProcessors());
        directRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        directRabbitListenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        directRabbitListenerContainerFactory.setConsumersPerQueue(10);
        return directRabbitListenerContainerFactory;
    }

	@Bean
	public SimpleRabbitListenerContainerFactory container(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		//消息序列化
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		return factory;
	}

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(Constants.EXCHANGE, true, false);
    }

    @Bean
    public Queue defaultQueue() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", Constants.DEAD_EXCHANGE);
        params.put("x-dead-letter-routing-key", Constants.DEAD_KEY);
        return new Queue(Constants.QUEUE, true, false, false, params);

    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(defaultQueue()).to(defaultExchange()).with(Constants.ROUTING_KEY);
    }

    /**
     * 死信交换机
     * @return
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(Constants.DEAD_EXCHANGE, true, false);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(Constants.DEAD_QUEUE, true);

    }

    @Bean
    public Binding deadLetterbinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(Constants.DEAD_KEY);
    }

    /**
     * 延时队列配置
     * @return
     */
    @Bean
    public Queue delayProcessQueue() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", Constants.REGISTER_EXCHANGE_NAME);
        params.put("x-dead-letter-routing-key", Constants.ROUTING_KEY_ALL);
        return new Queue(Constants.REGISTER_DELAY_QUEUE, true, false, false, params);
    }

    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(Constants.REGISTER_DELAY_EXCHANGE);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayProcessQueue()).to(delayExchange()).with(Constants.DELAY_ROUTING_KEY);
    }


    @Bean
    public Queue registerBookQueue() {
        return new Queue(Constants.REGISTER_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange registerBookTopicExchange() {
        return new TopicExchange(Constants.REGISTER_EXCHANGE_NAME);
    }

    @Bean
    public Binding registerBookBinding() {
        return BindingBuilder.bind(registerBookQueue()).to(registerBookTopicExchange()).with(Constants.ROUTING_KEY_ALL);
    }
}