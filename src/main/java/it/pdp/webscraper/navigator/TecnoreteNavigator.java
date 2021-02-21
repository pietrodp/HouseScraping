package it.pdp.webscraper.navigator;

import javax.xml.parsers.ParserConfigurationException;

public class TecnoreteNavigator extends AbstractNavigator implements INavigator{

	public TecnoreteNavigator() throws ParserConfigurationException {
		super.basePathAnnuncio="https://www.tecnorete.it/vendita/appartamenti/napoli/napoli/#placeHolder#.html";
	}


}
