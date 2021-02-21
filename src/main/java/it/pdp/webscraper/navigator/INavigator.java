package it.pdp.webscraper.navigator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import it.pdp.webscraper.bean.PaginaBean;

public interface INavigator {

	HashMap<String, String> getPages(String htmlHomepage) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException;

	ArrayList<String> getAnnunci(ArrayList<PaginaBean> listPagineSito);

}
