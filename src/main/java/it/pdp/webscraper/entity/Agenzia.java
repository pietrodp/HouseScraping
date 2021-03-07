package it.pdp.webscraper.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agenzia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idAgenzia;
	private String nome;
	
	protected Agenzia() {}
	
	public Agenzia(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id_agenzia=%d, nome=%s]", idAgenzia, nome);
	}
	
	public long getIdAgenzia() {
		return idAgenzia;
	}
	
	public String getNome() {
		return nome;
	}
	

}
