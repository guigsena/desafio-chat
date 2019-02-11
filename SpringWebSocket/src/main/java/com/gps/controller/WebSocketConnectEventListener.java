package com.gps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.gps.model.SocketSessionRegistry;

/**
 * Listener que registra cada acesso do usuario
 * @author Guilherme Sena
 *
 */
@Component
public class WebSocketConnectEventListener implements ApplicationListener<SessionConnectEvent> {
	@Autowired
	SocketSessionRegistry webAgentSessionRegistry;

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
	    StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
	
	    String agentId = sha.getNativeHeader("Login").get(0);
	    String sessionId = sha.getSessionId();
	
	    /** Registra a sessao pelo id do usuario */
	    webAgentSessionRegistry.registerSessionId(agentId, sessionId);
	
	}
}