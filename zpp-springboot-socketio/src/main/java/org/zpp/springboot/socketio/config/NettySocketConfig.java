package org.zpp.springboot.socketio.config;

import javax.annotation.PreDestroy;

import com.corundumstudio.socketio.SocketIONamespace;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import org.zpp.springboot.socketio.exception.SocketIoExceptionListener;
import org.zpp.springboot.socketio.listener.GroupChatEventListener;
import org.zpp.springboot.socketio.model.User;
import org.zpp.springboot.socketio.service.UserService;

@Configuration
public class NettySocketConfig {
	
	//单机模式
	private static final String SINGLE_SERVER = "redis://172.18.7.172:6379";
	
	@Value("${wss.server.port}")
	private int WSS_PORT;
	@Value("${wss.server.host}")
	private String WSS_HOST;
	
	private SocketIOServer server;

	@Autowired
    private UserService userService;

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		// 不设置主机、默认绑定0.0.0.0 or ::0
		// config.setHostname(WSS_HOST);
		config.setPort(WSS_PORT);

		// Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
        config.setPingInterval(20000);
        // Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
        config.setPingTimeout(55000);
		config.setWorkerThreads(100);

		config.setExceptionListener(new SocketIoExceptionListener());

		// 该处进行身份验证
		config.setAuthorizationListener(data -> {
			String clientName = data.getSingleUrlParam("clientName");
			User user = userService.selectByName(clientName);
			if (user == null) {
                return false;
            }
			return true;
		});

		config.getSocketConfig().setReuseAddress(true);
		config.getSocketConfig().setSoLinger(0);
		config.getSocketConfig().setTcpNoDelay(true);
		config.getSocketConfig().setTcpKeepAlive(true);

		Config redissonConfig = new Config();
		redissonConfig.useSingleServer()
					.setAddress(SINGLE_SERVER)
					.setDatabase(11)
					;
		RedissonClient redisson = Redisson.create(redissonConfig);
		config.setStoreFactory(new RedissonStoreFactory(redisson));
		
		server = new SocketIOServer(config);

        SocketIONamespace chat1namespace = server.addNamespace("/user"); //设置命名空间
        chat1namespace.addListeners(new GroupChatEventListener(server));
		return server;
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}
	
	@PreDestroy  
    public void destory() { 
		server.stop();
	}
}