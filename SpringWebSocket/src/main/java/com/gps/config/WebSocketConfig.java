package com.gps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.gps.controller.WebSocketConnectEventListener;
import com.gps.model.SocketSessionRegistry;

/**
 * Configura o acesso ao websocket
 * @author Guiherme Sena
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * tipos de mensagens habilitadas
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic","/queue","/user");
	    config.setApplicationDestinationPrefixes("/app");
	    config.setUserDestinationPrefix("/user");
        
	}

	/**
	 * Registra endpoint (path de acesso)
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/api/ws").
		setAllowedOrigins("*").
		withSockJS();
	}
	
	@Bean
	public SocketSessionRegistry SocketSessionRegistry(){
		return new SocketSessionRegistry();
	}
	
	@Bean
	public WebSocketConnectEventListener STOMPConnectEventListener(){
		return new WebSocketConnectEventListener();
	}
	
}