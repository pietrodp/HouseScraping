package it.pdp.webscraper.filter;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.pdp.webscraper.bean.AnnuncioBean;
import it.pdp.webscraper.controller.WebScruperController;
import it.pdp.webscraper.utility.MyConfiguration;

public class FilterEngine {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(FilterEngine.class);

	ArrayList<FilterBean> filtri = new ArrayList<FilterBean>();

	public FilterEngine() {
		buildFilter();
	}


	public ArrayList<AnnuncioBean> filterAnnunciNonValidi(ArrayList<AnnuncioBean> listAnnunciRecuperati) {
		
		ArrayList<AnnuncioBean> listaAnnunciValidi = new ArrayList<AnnuncioBean>();

		for (AnnuncioBean annuncioBean : listAnnunciRecuperati) {

			ArrayList<FilterBean> filtriMatched = new ArrayList<>();
			for (FilterBean filterBean : filtri) {
				if(annuncioBean.getHtml().contains(filterBean.getFiltro())) {
					filtriMatched.add(filterBean);
				}
			}
			if(filtriMatched.size()>0) {
				annuncioBean.setMatchedFilters(filtriMatched);
				listaAnnunciValidi.add(annuncioBean);
			}
		}
		LOGGER.info("Recuperati "+listaAnnunciValidi.size()+" annunci Validi");
		return listaAnnunciValidi;
	}



	private void buildFilter() {
		String[] filtriMandatory = MyConfiguration.getProperty("filtri.mandatory").split(",");
		String[] filtriOpzionali = MyConfiguration.getProperty("filtri.opzionali").split(",");

		for (int i = 0; i < filtriMandatory.length; i++) {
			filtri.add(new FilterBean(Boolean.TRUE, filtriMandatory[i]));
		}

		for (int i = 0; i < filtriOpzionali.length; i++) {
			filtri.add(new FilterBean(Boolean.FALSE, filtriOpzionali[i]));
		}
	}
}
