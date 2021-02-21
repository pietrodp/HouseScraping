package it.pdp.webscraper.clients;

import java.io.IOException;

import org.eclipse.jetty.util.UrlEncoded;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import it.pdp.webscraper.navigator.INavigator;
import it.pdp.webscraper.utility.MyConfiguration;
import it.pdp.webscraper.utility.ScritturaFile;

public abstract class ClientAbastract implements IClient{
	
	public String homePageUrl;
	public String agenzia;
	public INavigator navigator;
	
	
	public String getHomePage() {
		HtmlPage page = null;

		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		try {
			page = client.getPage(homePageUrl);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.close();
		}

		return page.asXml();
	}

	public String getPageHtml(String inputPageUrl) {
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);

		HtmlPage page = null;

		try {
			page = client.getPage(inputPageUrl);
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}

		System.out.println("Annuncio all'url "+inputPageUrl+" scaricato");
		
		if(MyConfiguration.getProperty("salvataggio.file").equals("true")) {
			ScritturaFile.creazioneNewFile(UrlEncoded.encodeString(inputPageUrl));
			ScritturaFile.writeFile(page.asXml(), UrlEncoded.encodeString(inputPageUrl));
		}
			
		return page.asXml();
	}
}
