package org.zpp.springboot.socketio.request;

import java.io.Serializable;


public class LoginRequest implements Serializable {
    private int code;
    private String body;

    public LoginRequest() {
    }

    public LoginRequest(int code, String body) {
        super();
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
