package it.pdp.webscraper.clients;

import javax.xml.parsers.ParserConfigurationException;

import it.pdp.webscraper.navigator.NavigatorFactory;
import it.pdp.webscraper.utility.MyConfiguration;

public class Client extends ClientAbastract implements IClient{
	
	public Client(String agenzia) throws ParserConfigurationException {
		super.agenzia = agenzia;
		super.homePageUrl = MyConfiguration.getProperty("agenzia."+agenzia+".home");
		super.navigator = NavigatorFactory.buildNavigator(agenzia);
	}
}
