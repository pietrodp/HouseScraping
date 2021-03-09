package it.pdp.webscraper.bean.decorator;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import it.pdp.webscraper.bean.AnnuncioBean;

public class DecoratoreAnnunci {
	
	public static ArrayList<AnnuncioBean> decorator(ArrayList<AnnuncioBean> listaAnnunciValidi) throws ParserConfigurationException {
		
		for (AnnuncioBean annuncioBean : listaAnnunciValidi) {
			GenericAnnuncio genericAnnuncio = DecoratorFactory.selectDecorator(annuncioBean);
			annuncioBean.setPrezzo(genericAnnuncio.getPrezzo());
			
		}
		
		return listaAnnunciValidi;
		
	}

}
