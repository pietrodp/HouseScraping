package it.pdp.webscraper.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jetty.util.UrlEncoded;

import it.pdp.webscraper.navigator.NavigatorFactory;
import it.pdp.webscraper.utility.MyConfiguration;

public class ClientTest extends ClientAbastract implements IClient{

	public ClientTest(String agenzia) throws ParserConfigurationException {
		super.agenzia = agenzia;
		super.homePageUrl = MyConfiguration.getProperty("agenzia."+agenzia+".home");
		super.navigator = NavigatorFactory.buildNavigator(agenzia);
	}

	@Override
	public String getHomePage() {
		StringBuilder page = new StringBuilder();
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("\\mock\\testPage.html");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
            while ((line = reader.readLine()) != null) {
                page.append(line);
            }
            reader.close();
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return page.toString();
	}
	
	@Override
	public String getPageHtml(String inputPageUrl) {
		
		inputPageUrl = UrlEncoded.encodeString(inputPageUrl);
		
		StringBuilder page = new StringBuilder();
		try {
			
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("\\mock\\"+inputPageUrl);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
            while ((line = reader.readLine()) != null) {
                page.append(line);
            }
            reader.close();
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	

		System.out.println("Annuncio all'url "+inputPageUrl+" scaricato");
		return page.toString();
	}
	

}
