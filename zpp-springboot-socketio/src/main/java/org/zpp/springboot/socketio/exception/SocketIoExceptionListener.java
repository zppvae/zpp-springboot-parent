package org.zpp.springboot.socketio.exception;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;

public class SocketIoExceptionListener extends ExceptionListenerAdapter {
	private static final Logger log = LoggerFactory.getLogger(SocketIoExceptionListener.class);

    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
    	e.printStackTrace();
    	if(e instanceof IOException){
    		log.info(e.getMessage());
    	}else{
    		log.error(e.getMessage(), e);
    	}
    	client.disconnect();
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
    	e.printStackTrace();
    	if(e instanceof IOException){
    		log.info(e.getMessage());
    	}else{
    		log.error(e.getMessage(), e);
    	}
    	client.disconnect();
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
    	e.printStackTrace();
    	if(e instanceof IOException){
    		log.info(e.getMessage());
    	}else{
    		log.error(e.getMessage(), e);
    	}
    	client.disconnect();
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
    	e.printStackTrace();
    	if(e instanceof IOException){
    		log.info(e.getMessage());
    	}else{
    		log.error(e.getMessage(), e);
    	}
    	ctx.close();
        return true;
    }
}