package it.pdp.webscraper.bean;

import java.util.ArrayList;

import it.pdp.webscraper.bean.decorator.GenericAnnuncio;
import it.pdp.webscraper.filter.FilterBean;

public class AnnuncioBean extends GenericAnnuncio{
	
	private String agenzia;
	private String titoloAnnucio;
	private String idAnnuncio;
	private Integer prezzo;
	private String url;
	private ArrayList<FilterBean> matchedFilters;
	private String indirizzo;
	
	public AnnuncioBean() {
		
	}
	
	public AnnuncioBean(String url, String pageHtml) {
		this.url = url;
		super.html = pageHtml;
	}
	
	public AnnuncioBean(String url) {
		this.url = url;
	}
	
	
	public String getTitoloAnnucio() {
		return titoloAnnucio;
	}
	public void setTitoloAnnucio(String titoloAnnucio) {
		this.titoloAnnucio = titoloAnnucio;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getIdAnnuncio() {
		return idAnnuncio;
	}
	public void setIdAnnuncio(String idAnnuncio) {
		this.idAnnuncio = idAnnuncio;
	}
	public Integer getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<FilterBean> getMatchedFilters() {
		return matchedFilters;
	}

	public void setMatchedFilters(ArrayList<FilterBean> matchedFilters) {
		this.matchedFilters = matchedFilters;
	}

	public String getNomeAgenzia() {
		return agenzia;
	}

	public void setAgenzia(String agenzia) {
		this.agenzia = agenzia;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
}
