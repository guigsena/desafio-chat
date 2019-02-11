package com.gps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gps.model.Usuario;

@RepositoryRestResource
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query("select new Usuario(u.id, u.email, u.nome, (select count(*) from Mensagem m where m.para.id = :idOrigem and m.dataLeitura is null and m.de.id = u.id)) from Usuario u where u.id <> :idOrigem")
	List<Usuario> findUsuariosCountMsgEnviadas(@Param("idOrigem")Long idOrigem);
	
	@Query("select u from Usuario u where u.email = :email and u.senha = :senha")
	Usuario findUsuariosEmailSenha(@Param("email")String email, @Param("senha")String senha);
}


