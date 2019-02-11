package com.gps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gps.model.Mensagem;

@RepositoryRestResource
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
	@Query("select m from Mensagem m where (m.de.id = :idOrigem and m.para.id = :idDestino) OR (m.de.id = :idDestino and m.para.id = :idOrigem) order by m.dataEnvio")
	List<Mensagem> findByUsuarioOrigemDestino(@Param("idOrigem")Long idOrigem , @Param("idDestino")Long idDestino);
	
	@Query("select m from Mensagem m where (m.de.id = :idEnviou and m.para.id = :idLeu) and m.dataLeitura is null ")
	List<Mensagem> findMsgNaoLidasOrigemDestino(@Param("idEnviou")Long idEnviou, @Param("idLeu")Long idLeu);
}


