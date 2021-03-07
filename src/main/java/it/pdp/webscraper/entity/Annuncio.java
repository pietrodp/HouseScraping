package it.pdp.webscraper.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Annuncio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String link;
	private String id_annuncio;
	private String matches;
	private long id_agenzia;
	
	protected Annuncio() {}
	
	@Override
	public String toString() {
		return String.format("Annuncio[id=%d, link=%s, id_annuncio=%s, matches=%s, id_agenzia=%d]", id, link, id_annuncio, matches, id_agenzia);
	}

	public long getId() {
		return id;
	}

	public String getLink() {
		return link;
	}

	public String getId_annuncio() {
		return id_annuncio;
	}

	public String getMatches() {
		return matches;
	}

	public long getId_agenzia() {
		return id_agenzia;
	}
	
	
}
