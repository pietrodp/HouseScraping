package it.pdp.webscraper.bean.decorator;

public class ExtraInfoTecnocasaDecorator extends ExtraInfoDecorator{
	
	public ExtraInfoTecnocasaDecorator(GenericAnnuncio genericAnnuncio) {
		this.genericAnnuncio = genericAnnuncio;
	}

	@Override
	public Integer getPrezzo() {
		return 99;
	}
}
