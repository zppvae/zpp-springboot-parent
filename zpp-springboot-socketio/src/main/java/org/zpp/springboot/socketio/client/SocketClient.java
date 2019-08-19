package org.zpp.springboot.socketio.client;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zpp.springboot.socketio.common.Constants;
import org.zpp.springboot.socketio.model.MessageInfo;
import org.zpp.springboot.socketio.request.LoginRequest;
import org.zpp.springboot.socketio.util.JsonConverterUtils;

import com.alibaba.fastjson.JSONObject;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketClient {
    private static Socket socket;
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"};
        options.reconnectionAttempts = 2;     // 重连尝试次数
        options.reconnectionDelay = 5000;     // 失败重连的时间间隔(ms)
        options.timeout = 20000;              // 连接超时时间(ms)
        options.forceNew = true;
        options.query = "token=123456&roomId=12&clientId=12";
        socket = IO.socket("http://127.0.0.1:8081/", options);
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // 客户端一旦连接成功，开始发起登录请求
                LoginRequest message = new LoginRequest(12, "消息体");
                socket.emit("login", JSONObject.toJSON(message), (Ack) args1 -> {
                    logger.info("回执消息=" + Arrays.stream(args1).map(Object::toString).collect(Collectors.joining(",")));
                });
            }
        }).on("login", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                logger.info("接受到服务器房间广播的登录消息：" + Arrays.toString(args));
            }
        }).on(Constants.BROADCAST_KEY, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                logger.info("接受到服务器广播的消息：" + Arrays.toString(args));
            }
        }).on(Constants.ROOM_CHAT_KEY, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                logger.info("接受到房间的消息：" + Arrays.toString(args));
            }
        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                logger.info("Socket.EVENT_CONNECT_ERROR");
                socket.disconnect();
            }
        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                logger.info("Socket.EVENT_CONNECT_TIMEOUT");
                socket.disconnect();
            }
        }).on(Socket.EVENT_PING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                logger.info("Socket.EVENT_PING");
            }
        }).on(Socket.EVENT_PONG, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                logger.info("Socket.EVENT_PONG");
            }
        }).on(Socket.EVENT_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                logger.info("-----------接受到消息啦--------" + Arrays.toString(args));
            }
        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                logger.info("客户端断开连接啦。。。");
                socket.disconnect();
            }
        });
        socket.connect();
        //给所有客户端广播消息
        socket.emit(Constants.BROADCAST_KEY,"{data:\"hello,all\"}");
        //加入房间（分组）
        socket.emit(Constants.ROOM_CHAT_KEY, "1081262");
        
        MessageInfo sendData = new MessageInfo();
//		sendData.setSourceSessionId("12");
//		sendData.setTargetSessionId("12");
		sendData.setMsg("客户端发送的消息");
		
        socket.send(JsonConverterUtils.objectToJSONObject(sendData));
    }
}