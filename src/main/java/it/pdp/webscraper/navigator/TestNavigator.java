package it.pdp.webscraper.navigator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.pdp.webscraper.bean.PaginaBean;
import it.pdp.webscraper.costants.Costants;
import it.pdp.webscraper.utility.MyConfiguration;

public class TestNavigator extends AbstractNavigator implements INavigator{

	ArrayList<String> listURLannunci = new ArrayList<>();
	private final String primaPagina = MyConfiguration.getProperty("agenzia.tecnorete.home");


	public TestNavigator() throws ParserConfigurationException {
		super.basePathAnnuncio="https://www.tecnorete.it/vendita/appartamenti/napoli/napoli/#placeHolder#.html";
	}

	
	//Recupero dalla home page il numero tutale di pagine di annunci
	public HashMap<String, String> getPages(String input) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		HashMap<String, String> pagine = new HashMap<String, String>();

		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		Document doc = builder.parse(stream);

		XPathExpression expr = xpath.compile("/html/body/nav/div/ul/li[last()]/a/@href");
		Object result = expr.evaluate(doc, XPathConstants.STRING);
		String url = (String) result;
		Integer numeroPagineTotali = Integer.valueOf(url.substring(url.indexOf("pag-")+4));
		System.out.println("Ci sono "+(numeroPagineTotali+1)+" pagine di inserzioni");

		pagine.put("pag-0", primaPagina);
		return pagine;
	}
	

}
