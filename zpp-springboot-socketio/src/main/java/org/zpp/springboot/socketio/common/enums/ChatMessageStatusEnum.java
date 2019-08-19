package org.zpp.springboot.socketio.common.enums;

/**
 * @author zpp
 * @date 2018/8/15 11:00
 */
public enum ChatMessageStatusEnum {

    SEND_NO(0,"未发送"),

    SEND_YES(1,"已发送"),

    ;

    private Integer key;

    private String desc;

    ChatMessageStatusEnum(Integer key,String desc){
        this.key = key;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }


}
