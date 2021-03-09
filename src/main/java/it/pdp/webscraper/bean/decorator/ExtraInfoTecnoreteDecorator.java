package it.pdp.webscraper.bean.decorator;

public class ExtraInfoTecnoreteDecorator extends ExtraInfoDecorator{

	public ExtraInfoTecnoreteDecorator(GenericAnnuncio genericAnnuncio) {
		this.genericAnnuncio = genericAnnuncio;
	}

	@Override
	public Integer getPrezzo() {
		return 99;
	}

}
