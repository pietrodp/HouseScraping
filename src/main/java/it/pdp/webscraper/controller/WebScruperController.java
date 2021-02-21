package it.pdp.webscraper.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import it.pdp.webscraper.bean.AnnuncioBean;
import it.pdp.webscraper.bean.PaginaBean;
import it.pdp.webscraper.clients.ClientAbastract;
import it.pdp.webscraper.clients.ClientFactory;
import it.pdp.webscraper.filter.FilterBean;
import it.pdp.webscraper.filter.FilterEngine;
import it.pdp.webscraper.navigator.INavigator;
import it.pdp.webscraper.navigator.NavigatorFactory;
import it.pdp.webscraper.utility.MyConfiguration;
import it.pdp.webscraper.utility.ScritturaFile;

@RestController
public class WebScruperController {

	@Autowired
	public Environment env;
	private String output;

	@RequestMapping("/")
	String home() throws ParserConfigurationException {

		//readParameters
		String[] agenzieAbilitate = env.getProperty("agenzie.abilitate").split(",");
		
		//set Context
		setContext();

		//Istanzio le agenzie abilitate
		ArrayList<ClientAbastract> clientsAgenzieAbilitate = new ArrayList<>();
		clientsAgenzieAbilitate = readAganciesEnables(agenzieAbilitate);

		//Istanzio i lettori dei DOM
		this.factoryNavigator(agenzieAbilitate);

		//abilitazioni filtri
		FilterEngine filter = new FilterEngine();

		try {
			for(int i=0; i<clientsAgenzieAbilitate.size(); i++) {
				
				//recupero la homePage dal WEB dell'aganzia
				String htmlHomepage =  clientsAgenzieAbilitate.get(i).getHomePage();
				
				if(MyConfiguration.getProperty("salvataggio.file").equals("true")) {
					ScritturaFile.creazioneNewFile("testPage.html");
					ScritturaFile.writeFile(htmlHomepage, "testPage.html");
				}
				
				//Recupero tutte le pagine di annunci presenti sul sito
				ArrayList<PaginaBean> listPagineSito = retrievePagineAnnunci(clientsAgenzieAbilitate.get(i), htmlHomepage);

				//Recupero tutti gli Html degli annunci presenti sul sito
				ArrayList<AnnuncioBean> listAnnunciRecuperati = new ArrayList<>();
				listAnnunciRecuperati = this.retrieveAnnunciAgenzia(clientsAgenzieAbilitate.get(i), listPagineSito);

				//filtro solo gli annunci validi
				HashMap<String, ArrayList<FilterBean>> listAnnunciValidi = new HashMap<String, ArrayList<FilterBean>>();
				listAnnunciValidi = filter.filterAnnunciNonValidi(listAnnunciRecuperati);
				System.out.println("Recuperati "+listAnnunciValidi.size()+" annunci Validi");

				//scrittura output
				output = ScritturaFile.scritturaOutput(listAnnunciValidi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}





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
			System.out.println(pair.getKey() + " = " + pair.getValue() +" ("+count+" di "+listPagineSito.size()+")");
			pagineListaAnnunci.add(new PaginaBean(pair.getKey(), pair.getValue(), html));
			count++;
		}
		
		return pagineListaAnnunci;
	}


	private ArrayList<AnnuncioBean> retrieveAnnunciAgenzia(ClientAbastract agenziaAbilitata, ArrayList<PaginaBean> listPagineSito) {
		int count;
		//recupero gli URL di tutti gli annunci per tutte pagine caricate (tramite xPath)
		ArrayList<String> listURLannunci = agenziaAbilitata.navigator.getAnnunci(listPagineSito);
		System.out.println("Totale annunci disponibili per Napoli: "+listURLannunci.size()+" (Ancora da filtrare)");

		//recupero HTML annunci
		count = 1;
		ArrayList<AnnuncioBean> listAnnunciRecuperati = new ArrayList<>();
		for (String url : listURLannunci) {
			System.out.println("Annunci scaricati "+count+" di "+listURLannunci.size());
			AnnuncioBean annuncioBean = new AnnuncioBean(url, agenziaAbilitata.getPageHtml(url));
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

