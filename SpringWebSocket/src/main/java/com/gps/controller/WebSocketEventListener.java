package com.gps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.gps.model.SocketSessionRegistry;

@Component
public class WebSocketEventListener implements ApplicationListener<SessionConnectEvent> {
	@Autowired
	SocketSessionRegistry webAgentSessionRegistry;

	@Override
	public void onApplicationEvent(SessionConnectEvent event) {
	    StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
	
	    String agentId = sha.getNativeHeader("Login").get(0);
	    String sessionId = sha.getSessionId();
	
	    /** add new session to registry */
	    webAgentSessionRegistry.registerSessionId(agentId, sessionId);
	
	}
}
/*public class WebSocketEventListener implements ApplicationListener<SessionSubscribeEvent> {

 private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

 @Override
 public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
  StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
  //logger.info(headerAccessor.getSessionAttributes().get("HTTPSESSIONID").toString());
 }
}*/

/*@Component
public class WebSocketEventListener {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    private List<String> connectedClientId = new ArrayList<String>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    	MessageHeaders msgHeaders = event.getMessage().getHeaders();
        Principal princ = (Principal) msgHeaders.get("simpUser");
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        List<String> nativeHeaders = sha.getNativeHeader("userId");
        String userId = null;
        boolean exibeMsg = false;
        
        if( nativeHeaders != null ) {
            userId = nativeHeaders.get(0);
            connectedClientId.add(userId);
            if( logger.isDebugEnabled() )
            {
            	exibeMsg = true;
            }
        } else {
            userId = princ.getName();
            connectedClientId.add(userId);
            if( logger.isDebugEnabled() ) {
            	exibeMsg = true;
            }
        }
        
        if( exibeMsg ) {
            logger.debug("Conectado "+ userId);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    	/*MessageHeaders msgHeaders = event.getMessage().getHeaders();
        Principal princ = (Principal) msgHeaders.get("simpUser");
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        List<String> nativeHeaders = sha.getNativeHeader("userId");
        String userId = null;
        
        boolean exibeMsg = false;
        if( nativeHeaders != null ) {
            userId = nativeHeaders.get(0);
            connectedClientId.remove(userId);
            if( logger.isDebugEnabled() ) {
            	exibeMsg = true;
            }
        }
        else {
            userId = princ.getName();
            connectedClientId.remove(userId);
            if( logger.isDebugEnabled() ) {
            	exibeMsg = true;
            }
        }
        
        if( exibeMsg ) {
            logger.debug("Desconectado "+ userId);
        }
    }
    
    public List<String> getConnectedClientId() {
        return connectedClientId;
    }
    
    public void setConnectedClientId(List<String> connectedClientId) {
        this.connectedClientId = connectedClientId;
    }
}
*/