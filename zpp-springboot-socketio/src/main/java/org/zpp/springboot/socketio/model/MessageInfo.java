package org.zpp.springboot.socketio.model;

import com.alibaba.fastjson.JSONObject;
import org.zpp.springboot.socketio.util.JsonConverterUtils;

import java.io.Serializable;

public class MessageInfo implements Serializable{

	private Integer clientId;

	private String clientName;

	// 源客户端id
	private Integer sourceClientId;
	// 目标客户端id
	private Integer targetClientId;
	// 消息内容
	private String msg;

	private Integer msgId;

	public MessageInfo(){}

    public MessageInfo(Integer clientId,String clientName,Integer sourceClientId,Integer targetClientId,String msg){
	    this.clientId = clientId;
	    this.clientName = clientName;
	    this.sourceClientId = sourceClientId;
	    this.targetClientId = targetClientId;
	    this.msg = msg;
    }

    public MessageInfo(String json) throws Exception{
		System.out.println(json);
		JSONObject obj = JSONObject.parseObject(json);
		this.clientId = obj.getInteger("clientId");
		this.sourceClientId = obj.getInteger("sourceClientId");
		this.targetClientId = obj.getInteger("targetClientId");
		this.msg = obj.getString("msg");
		this.clientName = obj.getString("clientName");
	}

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public Integer getSourceClientId() {
		return sourceClientId;
	}

	public void setSourceClientId(Integer sourceClientId) {
		this.sourceClientId = sourceClientId;
	}

	public Integer getTargetClientId() {
		return targetClientId;
	}

	public void setTargetClientId(Integer targetClientId) {
		this.targetClientId = targetClientId;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}