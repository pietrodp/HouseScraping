package it.pdp.webscraper.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import it.pdp.webscraper.bean.AnnuncioBean;
import it.pdp.webscraper.bean.PaginaBean;
import it.pdp.webscraper.bean.decorator.DecoratoreAnnunci;
import it.pdp.webscraper.clients.ClientAbastract;
import it.pdp.webscraper.clients.ClientFactory;
import it.pdp.webscraper.entity.Agenzia;
import it.pdp.webscraper.entity.Annuncio;
import it.pdp.webscraper.filter.FilterBean;
import it.pdp.webscraper.filter.FilterEngine;
import it.pdp.webscraper.navigator.INavigator;
import it.pdp.webscraper.navigator.NavigatorFactory;
import it.pdp.webscraper.repository.AgenziaRepository;
import it.pdp.webscraper.repository.AnnuncioRepository;
import it.pdp.webscraper.utility.MyConfiguration;
import it.pdp.webscraper.utility.ScritturaFile;

@RestController
public class WebScruperController {

	private static final Logger LOGGER=LoggerFactory.getLogger(WebScruperController.class);
	
	@Autowired
	public Environment env;
	private String output;
	
	@Autowired
	AgenziaRepository agenziaRepository;
	@Autowired
	AnnuncioRepository annuncioRepository;

	@RequestMapping("/")
	String home() throws ParserConfigurationException {

		//readParameters
		String[] agenzieAbilitate = env.getProperty("agenzie.abilitate").split(",");

		//set Context
		setContext();

		//Istanzio le agenzie abilitate
		ArrayList<ClientAbastract> clientsAgenzieAbilitate = readAganciesEnables(agenzieAbilitate);

		//Istanzio i lettori dei DOM
		this.factoryNavigator(agenzieAbilitate);

		//abilitazioni filtri
		FilterEngine filter = new FilterEngine();

		try {
			for(int i=0; i<clientsAgenzieAbilitate.size(); i++) {

				//recupero la homePage dal WEB dell'aganzia
				String htmlHomepage =  clientsAgenzieAbilitate.get(i).getHomePage();

				salvataggioTestPage(htmlHomepage);

				//Recupero tutte le pagine di annunci presenti sul sito
				ArrayList<PaginaBean> listPagineSito = retrievePagineAnnunci(clientsAgenzieAbilitate.get(i), htmlHomepage);

				//Recupero tutti gli Html degli annunci presenti sul sito
				ArrayList<AnnuncioBean> listAnnunciRecuperati = this.retrieveAnnunciAgenzia(clientsAgenzieAbilitate.get(i), listPagineSito);

				//filtro solo gli annunci validi
				ArrayList<AnnuncioBean> listAnnunciValidi = filter.filterAnnunciNonValidi(listAnnunciRecuperati);
				
				ArrayList<AnnuncioBean> annunciDecorati = DecoratoreAnnunci.decorator(listAnnunciValidi);
				
				save(annunciDecorati);
				LOGGER.info("Salvataggio su DB completato per agenzia "+clientsAgenzieAbilitate.get(i));

				//scrittura file html di output
//				scriviOutputFileHtml(listAnnunciValidi);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}




	private void save(ArrayList<AnnuncioBean> annunciDecorati) {
		
		Agenzia agenzia = new Agenzia(annunciDecorati.get(0).getNomeAgenzia());
		agenziaRepository.save(agenzia);
		for (AnnuncioBean annuncioBean : annunciDecorati) {
			annuncioRepository.save(new Annuncio(annuncioBean.getNomeAgenzia(), annuncioBean.getUrl(), annuncioBean.getMatchedFilters(), agenzia));
		}
	}





	private void salvataggioTestPage(String htmlHomepage) {
		if(Boolean.TRUE.equals(Boolean.valueOf(MyConfiguration.getProperty("salvataggio.file")))) {
			ScritturaFile.creazioneNewFile("testPage.html");
			ScritturaFile.writeFile(htmlHomepage, "testPage.html");
		}
	}





//	private void scriviOutputFileHtml(HashMap<String, ArrayList<FilterBean>> listAnnunciValidi) {
//		if(Boolean.TRUE.equals(Boolean.valueOf(MyConfiguration.getProperty("scrittura.file.output")))) {
//			output = ScritturaFile.scritturaOutput(listAnnunciValidi);
//		}
//	}





	private ArrayList<PaginaBean> retrievePagineAnnunci(ClientAbastract agenziaAbilitata, String htmlHomepage)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		//recupero gli URL di tutte le pagine di annunci del sito (tramite xPath)
		HashMap<String, String> listPagineSito = agenziaAbilitata.navigator.getPages(htmlHomepage);
		ArrayList<PaginaBean> pagineListaAnnunci = new ArrayList<>(); 

		//recupero HTML di ogni pagina del sito precedentemente identificata (dal Web)
		Iterator<Entry<String, String>> iterator = listPagineSito.entrySet().iterator();
		int count=1;
		while (iterator.hasNext()) {
			Map.Entry<String, String> pair = (Map.Entry<String, String>) iterator.next();
			String html = agenziaAbilitata.getPageHtml(pair.getValue());
			pagineListaAnnunci.add(new PaginaBean(pair.getKey(), pair.getValue(), html));
			LOGGER.info(pair.getKey() + " = " + pair.getValue() +" ("+count+" di "+listPagineSito.size()+")");
			count++;
		}

		return pagineListaAnnunci;
	}


	private ArrayList<AnnuncioBean> retrieveAnnunciAgenzia(ClientAbastract agenziaAbilitata, ArrayList<PaginaBean> listPagineSito) {
		int count;
		//recupero gli URL di tutti gli annunci per tutte pagine caricate (tramite xPath)
		ArrayList<AnnuncioBean> listURLannunci = agenziaAbilitata.navigator.getAnnunciUrl(listPagineSito);
		LOGGER.info("Totale annunci disponibili per Napoli: "+listURLannunci.size()+" (Ancora da filtrare)");

		//recupero HTML annunci
		count = 1;
		ArrayList<AnnuncioBean> listAnnunciRecuperati = new ArrayList<>();
		for (AnnuncioBean annuncioBean : listURLannunci) {
			LOGGER.info("Annunci scaricati "+count+" di "+listURLannunci.size());
			annuncioBean.setHtml(agenziaAbilitata.getPageHtml(annuncioBean.getUrl()));
			annuncioBean.setAgenzia(agenziaAbilitata.agenzia);
			listAnnunciRecuperati.add(annuncioBean);
			count++;
		}

		return listAnnunciRecuperati;
	}


	private ArrayList<ClientAbastract> readAganciesEnables(String[] agenzieAbilitate) throws ParserConfigurationException {
		ArrayList<ClientAbastract> clientsAgenzieAbilitate = new ArrayList<ClientAbastract>();
		for (int i = 0; i < agenzieAbilitate.length; i++) {
			clientsAgenzieAbilitate.add(ClientFactory.buildClient(agenzieAbilitate[i]));
		}
		return clientsAgenzieAbilitate;
	}

	private ArrayList<INavigator> factoryNavigator(String[] agenzieAbilitate) throws ParserConfigurationException {
		ArrayList<INavigator> collectionNavigator = new ArrayList<>();
		for (int i = 0; i < agenzieAbilitate.length; i++) {
			collectionNavigator.add(NavigatorFactory.buildNavigator(agenzieAbilitate[i]));
		}
		return collectionNavigator;
	}


	private void setContext() {
		MyConfiguration myConfiguration = new MyConfiguration();
		myConfiguration.setEnvironment(env);
	}
}

