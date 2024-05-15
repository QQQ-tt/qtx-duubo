package qtx.dubbo.websocket.server;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qtx
 * @since 2024/4/28 10:27
 */
@Slf4j
@Component
@ServerEndpoint("/web_socket/{user}")
public class WebSocketServer {

    private static final ConcurrentHashMap<String, Session> ONLINE_SESSION_POOL = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String user) {
        try {
            log.info("用户:{}上线", user);
            ONLINE_SESSION_POOL.put(user, session);
            logInfo();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    @OnClose
    public void onClose(@PathParam("user") String user) {
        try (Session remove = ONLINE_SESSION_POOL.remove(user)) {
            log.info("用户:{}下线", user);
            logInfo();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    @OnError
    public void onError(Throwable error) {
        log.error(error.getMessage());
    }

    @OnMessage
    public void onMessage(String message, @PathParam("user") String user) {
        log.info("收到消息:{},来自:{}", message, user);
    }

    public static void sendMassage(String user, String message) {
        Session session = ONLINE_SESSION_POOL.get(user);
        if (session != null) {
            session.getAsyncRemote()
                    .sendText(message);
        }
    }

    public static void sendAllMassage(String message) {
        ONLINE_SESSION_POOL.forEach((k, v) -> v.getAsyncRemote()
                .sendText(message));
    }

    private void logInfo() {
        log.info("当前在线人数:{}", ONLINE_SESSION_POOL.size());
    }
}
