package it.pdp.webscraper.navigator;

import javax.xml.parsers.ParserConfigurationException;

public class TecnocasaNavigator extends AbstractNavigator implements INavigator{

	public TecnocasaNavigator() throws ParserConfigurationException {
		super.basePathAnnuncio="https://www.tecnocasa.it/vendita/appartamenti/napoli/napoli/#placeHolder#.html";
	}


}
