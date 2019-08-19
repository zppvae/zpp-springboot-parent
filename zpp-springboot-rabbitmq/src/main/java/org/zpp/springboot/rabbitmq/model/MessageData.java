package org.zpp.springboot.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageData {
    int id;

    String message;

    int tryCount;

    /**
     * 消息状态
     */
    int status;

    public MessageData(int id,String message){
        this.id = id;
        this.message = message;
    }

    /**
     * 处理中
     */
    public static final int STATUS_PROCESSING = 0;

    /**
     * 处理成功
     */
    public static final int STATUS_SUCCESS = 1;

    /**
     * 处理失败
     */
    public static final int STATUS_FAIL = -1;

    public void addTryCount(){
        this.tryCount ++;
    }
}