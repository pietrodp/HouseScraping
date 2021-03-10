package it.pdp.webscraper.bean.decorator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import it.pdp.webscraper.costants.Costants;

public class ExtraInfoTestDecorator extends ExtraInfoDecorator{
	
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	XPathFactory xpathfactory;
	XPath xpath;
	String basePathAnnuncio = "";
	String primaPagina = "";
	InputStream stream = null;
	Document doc = null;
	
	public ExtraInfoTestDecorator(GenericAnnuncio genericAnnuncio) throws ParserConfigurationException, SAXException, IOException {
		this.genericAnnuncio = genericAnnuncio;
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		builder = factory.newDocumentBuilder();
		xpathfactory = XPathFactory.newInstance();
		xpath = xpathfactory.newXPath();
		stream = new ByteArrayInputStream(this.genericAnnuncio.html.getBytes(StandardCharsets.UTF_8));
		doc = builder.parse(stream);
	}

	@Override
	public Integer getPrezzo() {
		String prezzo="0";
		
		try {
			XPathExpression expr = xpath.compile(Costants.PREZZO_TEST_DECORATOR);
			Object result = expr.evaluate(doc, XPathConstants.STRING);
			prezzo = (String) result;
			prezzo = prezzo.replaceAll("\\D+","");
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}	
		return Integer.parseInt(prezzo);
	}

	@Override
	public String getIdAnnuncio() {
		String idAnnuncio = "N/A";
		
		try {
			XPathExpression expr = xpath.compile(Costants.ID_ANNUNCIO_TEST_DECORATOR);
			Object result = expr.evaluate(doc, XPathConstants.STRING);
			idAnnuncio = ((String) result).trim();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return idAnnuncio;
	}

	@Override
	public String getIndirizzo() {
		String indirizzo = "N/A";
		
		try {
			XPathExpression expr = xpath.compile(Costants.INDIRIZZO_TEST_DECORATOR);
			Object result = expr.evaluate(doc, XPathConstants.STRING);
			indirizzo = ((String) result).trim();
			indirizzo = indirizzo.replace("\t", "");
			indirizzo = indirizzo.replace(":", "");
			indirizzo = indirizzo.trim().replaceAll(" +", " ");

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return indirizzo;
	}

}

