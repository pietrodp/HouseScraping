package it.pdp.webscraper.navigator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.pdp.webscraper.bean.PaginaBean;
import it.pdp.webscraper.utility.MyConfiguration;

public class AbstractNavigator {
	
	
	
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	XPathFactory xpathfactory;
	XPath xpath;
	String basePathAnnuncio = "";
	String primaPagina = "";

	public AbstractNavigator() throws ParserConfigurationException {
		super();
		factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // never forget this!
		builder = factory.newDocumentBuilder();
		xpathfactory = XPathFactory.newInstance();
		xpath = xpathfactory.newXPath();
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
			if(MyConfiguration.getProperty("salvataggio.file").equals("false")) {
				for(int i=1; i<=numeroPagineTotali; i++) {
					pagine.put("pag-"+i, primaPagina+"/pag-"+i);
				}
			}
			return pagine;
		}
	
	//recuper gli indirizzi di tutti gli annunci in una pagina
		public ArrayList<String> getAnnunci(ArrayList<PaginaBean> list){
			ArrayList<String> listURLannunci = new ArrayList<>();
			for (PaginaBean paginaBean : list) {
				InputStream stream = new ByteArrayInputStream(paginaBean.getHtml().getBytes(StandardCharsets.UTF_8));
				Document doc;
				try {

					doc = builder.parse(stream);
					XPathExpression expr = xpath.compile("/html/body/div/@immobileid");
					Object result = expr.evaluate(doc, XPathConstants.NODESET);
					NodeList nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						listURLannunci.add(basePathAnnuncio.replace("#placeHolder#", nodes.item(i).getNodeValue()));
					}

				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}

			}
			return listURLannunci;
		}
	
	

}
