package it.pdp.webscraper.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import it.pdp.webscraper.bean.AnnuncioBean;
import it.pdp.webscraper.utility.MyConfiguration;

public class FilterEngine {

	ArrayList<FilterBean> filtri = new ArrayList<FilterBean>();
	HashMap<String, ArrayList<FilterBean>> annunciCorrispondenti = new HashMap<>();

	public FilterEngine() {
		buildFilter();
	}


	public HashMap<String, ArrayList<FilterBean>> filterAnnunciNonValidi(ArrayList<AnnuncioBean> listAnnunciRecuperati) {

		for (AnnuncioBean annuncioBean : listAnnunciRecuperati) {

			ArrayList<FilterBean> filtriMatched = new ArrayList<>();
			for (FilterBean filterBean : filtri) {
				if(annuncioBean.getHtml().contains(filterBean.getFiltro())) {
					filtriMatched.add(filterBean);
				}
			}
			if(filtriMatched.size()>0) {
				annunciCorrispondenti.put(annuncioBean.getUrl(), filtriMatched);
			}
		}
		return annunciCorrispondenti;
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
