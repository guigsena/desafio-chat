package com.gps.controller;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.gps.model.SocketSessionRegistry;

/**
 * Listener que registra cada acesso do usuario
 * @author Guilherme Sena
 *
 */
@Component
public class WebSocketDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {
	@Autowired
	SocketSessionRegistry webAgentSessionRegistry;

	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {
	    StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
	
	    String sessionId = sha.getSessionId();
	    ConcurrentMap<String, Set<String>> maps = webAgentSessionRegistry.getAllSessionIds();
	    for (Map.Entry<String, Set<String>> object : maps.entrySet()) {
	    	if(object.getValue().contains(sessionId)) {
	    		String agentId = object.getKey();
	    		/** Remove a sessao pelo id do usuario */
	    	    webAgentSessionRegistry.unregisterSessionId(agentId, sessionId);
	    	    break;
	    	}
		}  
	    
	}
}