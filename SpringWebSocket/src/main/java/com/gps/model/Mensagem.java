package com.gps.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mensagem")
public class Mensagem {
	
		
	@Id @GeneratedValue
	private Long id;

	private String conteudo;
    
	@ManyToOne
    private Usuario de;
    
	@ManyToOne
    private Usuario para;   
    
    private Date dataEnvio;
    private Date dataLeitura;
    
    public Mensagem() {
		super();
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Usuario getDe() {
		return de;
	}

	public void setDe(Usuario de) {
		this.de = de;
	}

	public Usuario getPara() {
		return para;
	}

	public void setPara(Usuario para) {
		this.para = para;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Date getDataLeitura() {
		return dataLeitura;
	}

	public void setDataLeitura(Date dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	@Override
	public String toString() {
		return "Mensagem [conteudo=" + conteudo + ", de=" + de + ", para=" + para + ", dataEnvio=" + dataEnvio
				+ ", dataLeitura=" + dataLeitura + ", getConteudo()=" + getConteudo() + ", getDe()=" + getDe()
				+ ", getPara()=" + getPara() + ", getDataEnvio()=" + getDataEnvio() + ", getDataLeitura()="
				+ getDataLeitura() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
