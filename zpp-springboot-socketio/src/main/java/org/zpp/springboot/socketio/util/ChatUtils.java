package org.zpp.springboot.socketio.util;

import com.corundumstudio.socketio.SocketIOClient;
import org.apache.commons.lang3.StringUtils;
import org.zpp.springboot.socketio.model.ClientInfo;

/**
 * @author zpp
 * @date 2018/8/3 14:57
 */
public class ChatUtils {

    /**
     * 离开房间
     * @param client
     * @return
     */
    public static void joinRoom(SocketIOClient client, String roomId) {
        String sessionId = client.getSessionId().toString();
        ClientInfo clientInfo = client.get(sessionId);
        clientInfo.setRoomId(roomId);
        client.joinRoom(roomId);
        client.set(sessionId,clientInfo);
    }

    /**
     * 离开房间
     * @param client
     * @return
     */
    public static String leaveRoom(SocketIOClient client) {
        String sessionId = client.getSessionId().toString();
        ClientInfo clientInfo = client.get(sessionId);
        if (clientInfo != null && StringUtils.isNotBlank(clientInfo.getRoomId())) {
            client.leaveRoom(clientInfo.getRoomId());
            return clientInfo.getRoomId();
        }

        return null;

    }

}
