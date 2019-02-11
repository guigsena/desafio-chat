package com.gps.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gps.config.TokenAuthenticationService;
import com.gps.model.Mensagem;
import com.gps.model.SocketSessionRegistry;
import com.gps.model.Usuario;
import com.gps.repository.MensagemRepository;
import com.gps.repository.UsuarioRepository;

import io.swagger.annotations.ApiOperation;

/**
 * Controle de rests e chamadas via socket a entidade usuario
 * @author Guilherme Sena
 *
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/api"})
public class UsuarioController {
	
	@Autowired
	SocketSessionRegistry webAgentSessionRegistry;
	
	private UsuarioRepository usuarioRepository;
	
	private MensagemRepository mensagemRepository;
	
	public UsuarioController(UsuarioRepository usuarioRepository, MensagemRepository mensagemRepository) {
        this.usuarioRepository = usuarioRepository;
        this.mensagemRepository = mensagemRepository;
    }
	
	
	@ApiOperation(value = "Recupera todos usuários logados na aplicação")
	@GetMapping("/todos-contatos/{idOrigem}")
	public Collection<Usuario> todosContatos(@PathVariable(value="idOrigem")Long idOrigem) {
		Collection<Usuario> lstUsu = usuarioRepository.findUsuariosCountMsgEnviadas(idOrigem);
		for (Usuario usuario : lstUsu) {
			if(!webAgentSessionRegistry.getSessionIds(usuario.getId().toString()).isEmpty()) {
				usuario.setOnline(true);
			}
		}
		//Ordenar pelos usuários online
		List<Usuario> list = (List<Usuario>) lstUsu;
		list.sort( (o1, o2) -> Boolean.compare(o2.isOnline(), o1.isOnline()));
		
		return list;
	}
	
	@ApiOperation(value = "Recupera todas as mensagens trocadas entre dois usuários")
	@GetMapping("/todas-mensagens/{idOrigem}/{idDestino}")
	public Collection<Mensagem> todasMensagens(@PathVariable(value="idOrigem")Long idOrigem, @PathVariable(value="idDestino")Long idDestino) {
		Collection<Mensagem> lstMsg = mensagemRepository.findByUsuarioOrigemDestino(idOrigem, idDestino);
		return lstMsg;
	}

	@ApiOperation(value = "Cria um novo usuário")
	@PostMapping("/salvar-usuario")
	public Usuario salvar(@RequestBody Usuario usuario) {
		usuario.setSenha(usuario.senhaCriptografada());
	    return usuarioRepository.saveAndFlush(usuario);
	}
	
	@ApiOperation(value = "Valida se usuario e senha são validos e retorna para o frontend o token de acesso caso o login seja válido")
	@PostMapping("/login")
	public String login(@RequestBody Usuario usuario) {
	    String token = null;
	    usuario = usuarioRepository.findUsuariosEmailSenha(usuario.getEmail(), usuario.senhaCriptografada());
	    if(usuario != null) {
	    	
	    	token = TokenAuthenticationService.generateJWT(usuario);
	    }
	    return token;
	}
	
	@ApiOperation(value = "Adiciona data de leitura à mensagem logo que o usuário realiza a leitura")
	@GetMapping("/update_msg-Lida/{idEnviou}/{idLeu}")
	public boolean updateMsgLida(@PathVariable(value="idEnviou")Long idEnviou, @PathVariable(value="idLeu")Long idLeu) {
		boolean retorno = true;
		Collection<Mensagem> lstMsg = mensagemRepository.findMsgNaoLidasOrigemDestino(idEnviou, idLeu);
		for (Mensagem mensagem : lstMsg) {
			mensagem.setDataLeitura(new Date());
			mensagemRepository.save(mensagem);
		}
		return retorno;
	}
}
