package it.pdp.webscraper.navigator;

import javax.xml.parsers.ParserConfigurationException;

import it.pdp.webscraper.costants.Costants;

public class NavigatorFactory {
	
	
	public NavigatorFactory() {
		super();
	}

	public static INavigator buildNavigator(String agenzia) throws ParserConfigurationException {
		
		INavigator navigator = null;
		
		switch (agenzia) {
		case Costants.TECNORETE:
			navigator = new TecnoreteNavigator();
			break;

		case Costants.TECNOCASA:
			navigator = new TecnocasaNavigator();
			break;
			
		case Costants.TEST:
			navigator = new TestNavigator();
			break;
			
		default:
			break;
		}
		
		return navigator;
	}

}
