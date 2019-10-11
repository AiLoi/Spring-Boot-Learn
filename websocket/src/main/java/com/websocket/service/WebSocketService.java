package com.websocket.service;

import javax.websocket.Session;

/**
 * @program: websocket
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-15 18:50
 **/
public interface WebSocketService {

    void onOpen(Session session);

    void onClose();

    void onMessage(String message,Session session);

    void onError(Session session,Throwable error);


}
