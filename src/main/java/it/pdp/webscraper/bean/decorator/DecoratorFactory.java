package it.pdp.webscraper.bean.decorator;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import it.pdp.webscraper.bean.AnnuncioBean;
import it.pdp.webscraper.costants.Costants;

public class DecoratorFactory {
	
	
	public DecoratorFactory() {
		super();
	}

	public static GenericAnnuncio selectDecorator(AnnuncioBean annuncioBean) throws ParserConfigurationException, SAXException, IOException {
		
		GenericAnnuncio genericAnnuncio = null;
		
		switch (annuncioBean.getNomeAgenzia()) {
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
