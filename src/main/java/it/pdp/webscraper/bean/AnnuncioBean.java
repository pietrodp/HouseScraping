package it.pdp.webscraper.bean;

public class AnnuncioBean {
	
       private String titoloAnnuncio;
	private String html;
	private String idAnnuncio;
	private String prezzo;
	private String url;
	
	
	public AnnuncioBean(String url, String pageHtml) {
		this.url = url;
		this.html = pageHtml;
	}
	
       public String getTitoloAnnuncio() {
               return titoloAnnuncio;
       }
       public void setTitoloAnnuncio(String titoloAnnuncio) {
               this.titoloAnnuncio = titoloAnnuncio;
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
