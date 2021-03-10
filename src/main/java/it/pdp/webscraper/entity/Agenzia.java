package it.pdp.webscraper.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
		return String.format("Agenzia[id_agenzia=%d, nome=%s]", idAgenzia, nome);
	}
	
	public long getIdAgenzia() {
		return idAgenzia;
	}
	
	public String getNome() {
		return nome;
	}
	
	@OneToMany(mappedBy = "agenzia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Annuncio> pages;

}
