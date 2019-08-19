## 死信

RabbitMQ支持为队列或者消息设置TTL（time to live 存活时间）。TTL表明了一条消息可在队列中存活的最大时间。当某条消息被设置了TTL或者当某条消息进入了设置了TTL的队列时，这条消息会在TTL时间后死亡成为Dead Letter。如果既配置了消息的TTL，又配置了队列的TTL，那么较小的那个值会被取用。

TTL 的消息或队列最终会成为Dead Letter。如果为队列设置了Dead Letter Exchange（DLX），那么这些Dead Letter就会被重新发送到Dead Letter Exchange中，然后通过Dead Letter Exchange路由到其他队列，即可实现延迟队列的功能。

> 消息进入死信的规则

- 消息被拒绝(basic.reject/basic.nack)并且requeue=false
- 消息TTL过期
- 队列达到最大长度

## 注解
`@Header`，注入消息头的单个属性

`@Payload`， 注入消息体到一个JavaBean中

`@Headers`， 注入所有消息头到一个Map中

## 延迟队列配置

```
方式一：
# 直接设置 Queue 延迟时间
params.put("x-message-ttl", 5 * 1000);

方式二：
# 每次发送消息动态设置延迟时间
rabbitTemplate.convertAndSend(book, message -> {
    message.getMessageProperties().setExpiration(2 * 1000 + "");
     return message;
});
```

## yml
```
# 消费失败后最大重试次数
spring.rabbitmq.listener.simple.retry.max-attempts=5
# 是否开启消费者重试（为false时关闭消费者重试，这时消费端代码异常会一直重复收到消息）  
spring.rabbitmq.listener.simple.retry.enabled=true 
# 重试间隔时间（ms）
spring.rabbitmq.listener.simple.retry.initial-interval=5000
# 重试次数超过上面的设置之后是否丢弃（false不丢弃时需要写相应代码将该消息加入死信队列）
spring.rabbitmq.listener.simple.default-requeue-rejected=false
```
## mq事务（最终一致性）

> 生产者发送数据到mq

保证生产者发送数据到mq，需要通过`confirm`机制处理

> 消费者正确消费消息

保证消费者完成消息的消费，采用手动ack的方式。

> 生产者事务回滚

生产者在发送消息的时候，给补偿队列发送一条消息，通过补偿机制去检测
生产者数据是否回滚，以此来处理生产者数据。

## 参考

https://cloud.tencent.com/developer/article/1369500