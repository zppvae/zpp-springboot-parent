package org.zpp.springboot.rabbitmq.job;

import cn.hutool.cache.Cache;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zpp.springboot.rabbitmq.model.MessageData;
import org.zpp.springboot.rabbitmq.producer.MessageProducer;

import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 重发消息任务
 */
@Component
@Slf4j
public class RetryMessageJob {

    @Autowired
    private MessageProducer messageProducer;

    /**
     * initialDelay：容器启动后多长时间第一次执行
     * fixedDelay：间隔时间
     */
    @Scheduled(initialDelay = 2000, fixedDelay = 5000)
    public void retrySend() {
        Cache<Integer,MessageData> cache = messageProducer.cache;
        log.info("[开始执行定时消息处理job] - [{}] - [消息数目：{}]", LocalDateTime.now(),cache.size());

        Iterator<MessageData> its = cache.iterator();
        while (its.hasNext()) {
            MessageData item = its.next();
            if (item.getStatus() == MessageData.STATUS_FAIL) {
                if (item.getTryCount() < 3) {
                    item.addTryCount();
                    log.info("[定时消息处理job检测到处理失败的消息] - [{}]", JSON.toJSONString(item));
                    //重新发送消息到队列
                    messageProducer.send(item);
                } else {
                    //TODO 此处可以进行人工补偿
                    log.info("[移除重试次数达到上限的消息] - [{}]",JSON.toJSONString(item));
                    cache.remove(item.getId());
                }
            }
        }

    }
}