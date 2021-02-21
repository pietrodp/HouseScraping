package it.pdp.webscraper.filter;

public class FilterBean {
	
	private boolean mandatory;
	private String filtro;
	
	public FilterBean(boolean mandatory, String filtro) {
		super();
		this.mandatory = mandatory;
		this.filtro = filtro;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	
	

}
