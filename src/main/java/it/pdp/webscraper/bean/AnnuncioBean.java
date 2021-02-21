package it.pdp.webscraper.bean;

public class AnnuncioBean {
	
	private String titoloAnnucio;
	private String html;
	private String idAnnuncio;
	private String prezzo;
	private String url;
	
	
	public AnnuncioBean(String url, String pageHtml) {
		this.url = url;
		this.html = pageHtml;
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
	public String getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

	
	

}
