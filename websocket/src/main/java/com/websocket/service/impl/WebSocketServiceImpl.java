package com.websocket.service.impl;

import com.websocket.service.WebSocketService;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: websocket
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-15 18:53
 **/

@Service
@Data
@ServerEndpoint("/ws")
public class WebSocketServiceImpl implements WebSocketService {

    //静态遍历，用来记录当前在线连接数，应该把它设计成线程安全的
    private static int onlineCount = 0;

    //concurrent包的线程安全的Set，用来存放每个客户端对应的WebSocketServiceImpl对象
    private static CopyOnWriteArraySet<WebSocketServiceImpl> webSocketServiceCopyOnWriteArraySet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过他来给客户端发送数据
    private Session session;

    /*
    连接建立成功调用的方法
     */
    @Override
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketServiceCopyOnWriteArraySet.add(this);   //加入set中
        addOnlineCount();                                //在线人数+1
        System.out.println("有新连接加入！当前在线人数为"+getOnlineCount());
        try {
            sendMessage("有新的连接加入了！！！");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /*
    连接关闭调用的方法
     */
    @Override
    @OnClose
    public void onClose() {
        webSocketServiceCopyOnWriteArraySet.remove(this);
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为"+getOnlineCount());
    }

    /*
    收到客户端消息后调用的方法
     */
    @Override
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息："+message);
        for (WebSocketServiceImpl item:webSocketServiceCopyOnWriteArraySet)
        {
            try {
//                String userName = item.getSession().getUserPrincipal().getName();
//                System.out.println(userName);
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServiceImpl.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServiceImpl.onlineCount--;
    }

}

