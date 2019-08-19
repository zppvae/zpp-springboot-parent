package org.zpp.springboot.rabbitmq.common;

public class Constants {
	
	public static final String EXCHANGE = "springboot.exchange";
	
	public static final String QUEUE = "springboot.exchange.queue";
	
	public static final String ROUTING_KEY = "springboot.exchange.queue.key";

	/**
	 * 延迟队列 TTL 名称
	 */
	public static final String REGISTER_DELAY_QUEUE = "dev.book.register.delay.queue";
	/**
	 * DLX，dead letter发送到的 exchange
	 */
	public static final String REGISTER_DELAY_EXCHANGE = "dev.book.register.delay.exchange";

	/**
	 * routing key 名称
	 */
	public static final String DELAY_ROUTING_KEY = "";


	public static final String REGISTER_QUEUE_NAME = "dev.book.register.queue";
	public static final String REGISTER_EXCHANGE_NAME = "dev.book.register.exchange";
	public static final String ROUTING_KEY_ALL = "all";

	/**
	 * 死信队列
	 */
	public static final String DEAD_EXCHANGE = "exchange.dead";
	public static final String DEAD_QUEUE = "queue.dead";
	public static final String DEAD_KEY = "key.dead";

	public static final String TURE = "true";
	public static final String FALSE = "false";

	/**
	 * 死信队列参数
	 */
	public static final String DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
	public static final String DEAD_LETTER_KEY = "x-dead-letter-routing-key";


	/**
	 * 死信队列
	 */
	public static final String DEAD_LETTER_PROFILE = "dead.letter";

	/**
	 * 发送者确认模式
	 */
	public static final String PUBLISHER_CONFIRM_PROFILE = "publisher.confirm";

	/**
	 * 带重试机制的Consumer
	 */
	public static final String CONSUMER_RETRY_PROFILE = "consumer.retry";
}
