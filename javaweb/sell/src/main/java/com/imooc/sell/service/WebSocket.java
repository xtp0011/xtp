package com.imooc.sell.service;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {
	private Session session;
	private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();
	
	@OnOpen
	public void onOpen(Session session) {
		this.session=session;
		webSocketSet.add(this);
		log.info("【webSocketSet消息】有新的连接，总数:{}",webSocketSet.size());
	}
	@OnClose
	public void OnClose(Session session) {
		webSocketSet.remove(this);
		log.info("【webSocketSet消息】断开连接，总数:{}",webSocketSet.size());
	}
	@OnMessage
	public void OnMessage(String message) {
		log.info("【webSocketSet消息】收到客户发来的消息，message={}",message);
	}
	public void sendMessage(String message) {
		for (WebSocket webSocket : webSocketSet) {
			log.info("【webSocketSet消息】广播消息，message={}",message);
			try {
				webSocket.session.getBasicRemote().sendText(message);
			} catch (Exception e) {
				e.printStackTrace();
			};
		}
	}

}
