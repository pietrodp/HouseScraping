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

import it.pdp.webscraper.bean.AnnuncioBean;
import it.pdp.webscraper.bean.PaginaBean;
import it.pdp.webscraper.utility.MyConfiguration;

public abstract class AbstractNavigator {
	
	
	
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
	
	
	protected abstract HashMap<String, String> getPages(String input) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException ;
	
	
	//recuper gli indirizzi di tutti gli annunci in una pagina
		public ArrayList<AnnuncioBean> getAnnunciUrl(ArrayList<PaginaBean> list){
			ArrayList<AnnuncioBean> listAnnunciWithURL = new ArrayList<>();
			for (PaginaBean paginaBean : list) {
				InputStream stream = new ByteArrayInputStream(paginaBean.getHtml().getBytes(StandardCharsets.UTF_8));
				Document doc;
				try {

					doc = builder.parse(stream);
					XPathExpression expr = xpath.compile("/html/body/div/@immobileid");
					Object result = expr.evaluate(doc, XPathConstants.NODESET);
					NodeList nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						listAnnunciWithURL.add(new AnnuncioBean(basePathAnnuncio.replace("#placeHolder#", nodes.item(i).getNodeValue())));
					}

				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}

			}
			return listAnnunciWithURL;
		}
	
	

}
