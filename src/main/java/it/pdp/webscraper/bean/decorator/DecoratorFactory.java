package it.pdp.webscraper.bean.decorator;

import javax.xml.parsers.ParserConfigurationException;

import it.pdp.webscraper.bean.AnnuncioBean;
import it.pdp.webscraper.costants.Costants;

public class DecoratorFactory {
	
	
	public DecoratorFactory() {
		super();
	}

	public static GenericAnnuncio selectDecorator(AnnuncioBean annuncioBean) throws ParserConfigurationException {
		
		GenericAnnuncio genericAnnuncio = null;
		
		switch (annuncioBean.getAgenzia()) {
		case Costants.TECNORETE:
			genericAnnuncio = new ExtraInfoTecnoreteDecorator(annuncioBean);
			break;

		case Costants.TECNOCASA:
			genericAnnuncio = new ExtraInfoTecnocasaDecorator(annuncioBean);
			break;
			
		case Costants.TEST:
			genericAnnuncio = new ExtraInfoTestDecorator(annuncioBean);
			break;
			
		default:
			break;
		}
		
		return genericAnnuncio;
	}


}
