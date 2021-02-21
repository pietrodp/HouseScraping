package it.pdp.webscraper.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import it.pdp.webscraper.filter.FilterBean;

public class ScritturaFile {
	
	static String filtroMandatoryPlaceHolder = "<font size=\"+2\"><B>#filterSelected#</B></font>";

	public static void creazioneNewFile(String nomeFile) {
		String path = "D:\\mock\\"+nomeFile;
		try {
			File file = new File(path);
			if (file.exists())
				System.out.println("Il file " + path + " esiste");
			else if (file.createNewFile())
				System.out.println("Il file " + path + " è stato creato");
			else
				System.out.println("Il file " + path + " non può essere creato");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeFile(String input, String nomeFile) {
		String path = "D:\\mock\\"+nomeFile;
		try {
			File file = new File(path);
			FileWriter fw = new FileWriter(file);
			fw.write(input);
			fw.flush();
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String scritturaOutput(HashMap<String, ArrayList<FilterBean>> listAnnunciValidi) {
		
		String output = "";
		
		output+="<html><body><table border='1' cellpadding=\"10\">";
		output+="<tr><th>Annuncio</th><th>Parole Trovate</th></tr>";
		Iterator<Entry<String, ArrayList<FilterBean>>> iterator = listAnnunciValidi.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ArrayList<FilterBean>> pair = (Map.Entry<String, ArrayList<FilterBean>>) iterator.next();
            output+="<tr><td><a href=\""+pair.getKey()+"\">"+pair.getKey()+"</a></td><td>"+generaHtmlFiltri(pair.getValue()).toString()+"</td></tr>";
            
        }
        output+="</table></body></html>";
        
        creazioneNewFile("Risultati.html");
        writeFile(output, "Risultati.html");
        
        
		return output;
	}

	private static StringBuilder generaHtmlFiltri(ArrayList<FilterBean> value) {
		StringBuilder htmlfiltri = new StringBuilder();
		for (FilterBean filterBean : value) {
			htmlfiltri.append(addBold(filterBean)).append(" - ");
		}
		return htmlfiltri.deleteCharAt(htmlfiltri.length()-1);
	}

	private static String addBold(FilterBean filterBean) {
		if(filterBean.isMandatory())
			return filtroMandatoryPlaceHolder.replace("#filterSelected#", filterBean.getFiltro());
		return filterBean.getFiltro();
	}
}
