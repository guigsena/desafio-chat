package com.gps.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Usuario {
	@Id @GeneratedValue
	private Long id;

	@JsonProperty("email")
	private @NonNull String email;
	
	private @NonNull String nome;
	
	@JsonProperty("senha")
	private @NonNull String senha;
	
	@Transient
	private boolean online = false;
	
	@Transient
	private Long contMsgNaoLidas;
	
	public Usuario() {
		super();
	}
	
	public Usuario(Long id, String email, String nome, Long contMsgNaoLidas) {
		super();
		this.id = id;
		this.email = email;
		this.nome = nome;
		this.contMsgNaoLidas = contMsgNaoLidas;
	}

	public String senhaCriptografada(){
	    String md5 = null;

	    try {
	        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(getSenha().getBytes(), 0, getSenha().length());
	        md5 = new java.math.BigInteger(1, digest.digest()).toString(16);
	    }
	    catch (java.security.NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return md5;
   }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Long getContMsgNaoLidas() {
		return contMsgNaoLidas;
	}

	public void setContMsgNaoLidas(Long contMsgNaoLidas) {
		this.contMsgNaoLidas = contMsgNaoLidas;
	}

}
