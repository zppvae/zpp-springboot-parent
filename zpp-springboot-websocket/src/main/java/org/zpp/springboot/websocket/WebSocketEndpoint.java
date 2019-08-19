//package org.zpp.springboot.websocket;
//
//import java.io.IOException;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//
//@ServerEndpoint("/zpp-websocket")
//public class WebSocketEndpoint {
//
//
//	private static CopyOnWriteArraySet<Session> webSocketSet = new CopyOnWriteArraySet<Session>();
//
//	/**
//	 * 连接建立时调用
//	 * @param session
//	 */
//	@OnOpen
//	public void onOpen(Session session) {
//		webSocketSet.add(session);
//		System.out.println("当前在线人数：" + webSocketSet.size());
//	}
//
//	@OnMessage
//	public void handleMessage(Session session, String message) throws IOException {
//		session.getBasicRemote()
//				.sendText("Reversed: " + new StringBuilder(message).reverse());
//	}
//
//	/**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose(Session session) {
//        webSocketSet.remove(session);
//        System.out.println("连接关闭！当前在线人数：" + webSocketSet.size());
//    }
//
//
//    /**
//     * 发生错误时调用
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        System.out.println("发生错误");
//        error.printStackTrace();
//    }
//
//    /**
//     * 群发消息
//     */
//    public static void sendGroup(String message) throws IOException {
//        for (Session item : webSocketSet) {
//            try {
//                sendMessage(item,message);
//            } catch (IOException e) {
//                continue;
//            }
//        }
//    }
//
//    public static void sendMessage(Session session,String message) throws IOException {
//        session.getBasicRemote().sendText(message);
//    }
//
//}
