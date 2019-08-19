package org.zpp.springboot.socketio.model;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.zpp.springboot.socketio.common.Constants;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ClientInfo implements Serializable{

	private Integer clientId;

	private String clientName;

	private boolean isOnline;
	private long mostSignificantBits;
	private long leastSignificantBits;
	private Date lastConnectedTime;

	private String sessionId;

	private String roomId;

	/** 默认/*/
	private String namespace;

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
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public long getMostSignificantBits() {
		return mostSignificantBits;
	}
	public void setMostSignificantBits(long mostSignificantBits) {
		this.mostSignificantBits = mostSignificantBits;
	}
	public long getLeastSignificantBits() {
		return leastSignificantBits;
	}
	public void setLeastSignificantBits(long leastSignificantBits) {
		this.leastSignificantBits = leastSignificantBits;
	}
	public Date getLastConnectedTime() {
		return lastConnectedTime;
	}
	public void setLastConnectedTime(Date lastConnectedTime) {
		this.lastConnectedTime = lastConnectedTime;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ClientInfo that = (ClientInfo) o;
		return clientId.intValue() == that.clientId;
	}

	@Override
	public int hashCode() {
		return 1;
	}
}