package it.pdp.webscraper.entity;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import it.pdp.webscraper.filter.FilterBean;

@Entity
public class Annuncio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String link;
	private String id_annuncio;
	private String matches;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_agenzia", nullable = false)
    private Agenzia agenzia;
	
	protected Annuncio() {}
	
	public Annuncio(String idAnnuncio, String link, ArrayList<FilterBean> listMatch, Agenzia agenzia) {
		this.link = link;
		this.id_annuncio = idAnnuncio;
		StringBuilder tmpBuilder = new StringBuilder();
		
		for (FilterBean filterBean : listMatch) {
			tmpBuilder.append(filterBean.getFiltro());
		}
		this.matches = tmpBuilder.toString();
		this.agenzia = agenzia;
		
	}
	
	@Override
	public String toString() {
		return String.format("Annuncio[id=%d, link=%s, id_annuncio=%s, matches=%s]", id, link, id_annuncio, matches);
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

}
