package it.pdp.webscraper.bean;

public class PaginaBean {
	
	private String pag;
	private String url;
	private String html;
	
	public PaginaBean(String pag, String url, String html) {
		super();
		this.pag = pag;
		this.url = url;
		this.html = html;
	}
	
	public String getPag() {
		return pag;
	}
	public void setPag(String pag) {
		this.pag = pag;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}

}
