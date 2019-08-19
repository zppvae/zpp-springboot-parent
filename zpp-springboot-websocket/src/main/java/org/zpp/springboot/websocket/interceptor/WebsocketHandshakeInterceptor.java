package org.zpp.springboot.websocket.interceptor;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * websocket拦截器
 * Description:
 * @author zpp
 * @date   2017年10月20日
 */
@Configuration
public class WebsocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
	@Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        //解决The extension [x-webkit-deflate-frame] is not supported问题
        if (request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        HttpServletRequest req = ((ServletServerHttpRequest)request).getServletRequest();
        HttpServletResponse res = ((ServletServerHttpResponse)response).getServletResponse();

        if (!StringUtils.isEmpty(req.getHeader("Sec-WebSocket-Protocol"))) {
            res.setHeader("Sec-WebSocket-Protocol",req.getHeader("Sec-WebSocket-Protocol"));
        }
        super.afterHandshake(request, response, wsHandler, ex);
    }

}