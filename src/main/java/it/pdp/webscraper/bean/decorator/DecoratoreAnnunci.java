package it.pdp.webscraper.bean.decorator;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import it.pdp.webscraper.bean.AnnuncioBean;

public class DecoratoreAnnunci {
	
	public static ArrayList<AnnuncioBean> decorator(ArrayList<AnnuncioBean> listaAnnunciValidi) throws ParserConfigurationException, SAXException, IOException {
		
		for (AnnuncioBean annuncioBean : listaAnnunciValidi) {
			GenericAnnuncio genericAnnuncio = DecoratorFactory.selectDecorator(annuncioBean);
			annuncioBean.setPrezzo(genericAnnuncio.getPrezzo());
			annuncioBean.setIdAnnuncio(genericAnnuncio.getIdAnnuncio());
			annuncioBean.setIndirizzo(genericAnnuncio.getIndirizzo());
		}
		return listaAnnunciValidi;
		
	}

}
