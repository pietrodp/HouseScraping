package it.pdp.webscraper.clients;

import javax.xml.parsers.ParserConfigurationException;

import it.pdp.webscraper.costants.Costants;

public class ClientFactory {
	
	
	public ClientFactory() {
		super();
	}

	public static ClientAbastract buildClient(String agenzia) throws ParserConfigurationException {
		
		ClientAbastract clientAbstract = null;
		
		if(agenzia.equals(Costants.TECNORETE)) {
			clientAbstract = new Client(Costants.TECNORETE);
		} else if (agenzia.equals(Costants.TECNOCASA)) {
			clientAbstract = new Client(Costants.TECNOCASA);
		} else if(agenzia.equals(Costants.TEST)) {
			clientAbstract = new ClientTest(Costants.TEST);
		}
		
//		switch (agenzia) {
//		case Costants.TECNORETE:
//			clientAbstract = new Client(Costants.TECNORETE);
//			break;
//
//		case Costants.TECNOCASA:
//			clientAbstract = new Client(Costants.TECNOCASA);
//			break;
//			
//		case Costants.TEST:
//			clientAbstract = new ClientTest(Costants.TEST);
//			break;
//			
//		default:
//			break;
//		}
		
		return clientAbstract;
	}


}
