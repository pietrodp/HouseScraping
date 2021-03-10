package it.pdp.webscraper.bean.decorator;

public abstract class GenericAnnuncio {

	protected String titoloAnnuncio;
	protected Integer prezzo;
	protected String Url;
	protected  String html;

	public abstract Integer getPrezzo();
	public abstract String getIdAnnuncio();
	public abstract String getIndirizzo();


	public String getHtml() {
		return html;
	}


}
