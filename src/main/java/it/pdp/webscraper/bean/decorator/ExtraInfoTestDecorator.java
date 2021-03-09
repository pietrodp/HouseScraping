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

public class ExtraInfoTestDecorator extends ExtraInfoDecorator{
	
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	XPathFactory xpathfactory;
	XPath xpath;
	String basePathAnnuncio = "";
	String primaPagina = "";
	
	public ExtraInfoTestDecorator(GenericAnnuncio genericAnnuncio) throws ParserConfigurationException {
		this.genericAnnuncio = genericAnnuncio;
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		builder = factory.newDocumentBuilder();
		xpathfactory = XPathFactory.newInstance();
		xpath = xpathfactory.newXPath();
	}

	@Override
	public Integer getPrezzo() {
		InputStream stream = new ByteArrayInputStream(this.genericAnnuncio.html.getBytes(StandardCharsets.UTF_8));
		String prezzo="0";
		
//		if(this.genericAnnuncio.html.contains("€"))
//			System.out.println(this.genericAnnuncio.html);
		
		try {
			Document doc = builder.parse(stream);
			XPathExpression expr = xpath.compile("//span[@class='current-price']");
			Object result = expr.evaluate(doc, XPathConstants.STRING);
			prezzo = (String) result;
			prezzo = prezzo.replaceAll("\\D+","");
		} catch (SAXException | IOException | XPathExpressionException e) {
			e.printStackTrace();
		}	
		return Integer.parseInt(prezzo);
	}

}

