package com.gps.controller;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.gps.model.Mensagem;
import com.gps.model.SocketSessionRegistry;
import com.gps.repository.MensagemRepository;

/**
 * Controle de rests e chamadas via socket a entidade mensagem
 * @author Guilherme Sena
 *
 */
@Controller
public class MensagemController {
	
	private MensagemRepository mensagemRepository;
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	SocketSessionRegistry webAgentSessionRegistry;
	
    public MensagemController(MensagemRepository mensagemRepository) {
		this.mensagemRepository = mensagemRepository;
	}

	@MessageMapping("/message")
	public void processMessageFromClient(SimpMessageHeaderAccessor headerAccessor, Mensagem message) {
    	//recuepra qual a sessao do usuario
    	String idSessaoDestino = getIdSessaoUsuIndex(webAgentSessionRegistry.getSessionIds(message.getPara().getId().toString()), "0");
    	//Monta protocolo de mensagem
	    SimpMessageHeaderAccessor ha = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
	    ha.setSessionId(idSessaoDestino);
	    ha.setLeaveMutable(true);
	    
	    //SE O USUARIO ESTIVER CONECTADO ENVIA A MSG
	    if(idSessaoDestino != null) {
	    	message.setDataLeitura(new Date());
	    	//envia mensagem de volta
	    	messagingTemplate.convertAndSendToUser( idSessaoDestino, "/queue/reply", message, ha.getMessageHeaders());
	    }
	    
	    //SALVA NO BD
	    mensagemRepository.save(message);
	}
	 
	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public String handleException(Throwable exception) {
	    return exception.getMessage();
	}
	
	/**
	 * 
	 * @param set
	 * @param value
	 * @return retorna id da sessao do usuario na primeira posicao
	 */
	private String getIdSessaoUsuIndex(Set<String> set, String value) {
		int cont = 0;
		for (String entry :set) {
			if (Integer.toString(cont).equals(value)) return entry;
		}
		return null;
	}
	

}
