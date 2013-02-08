package org.marczuk.controller;

import java.util.ArrayList;
import java.util.List;

import org.marczuk.model.Model;

public class Controller {

	public Controller(String sessionID) {
		this.model = new Model(sessionID);
	}
	
	public List<AminoAcid> getAminoList() throws Exception {
		
		if(aminoAcidList != null) {
			return aminoAcidList;
		}
		else {
			fillAminoAcidList();
			return aminoAcidList;
		}
	}
	
	private void fillAminoAcidList() throws Exception {
		String[] aminoAcidData = model.getAminoAcidData();
		aminoAcidList = new ArrayList<AminoAcid>();

		for(int i = 0; i < aminoAcidData[0].length(); i++)
			aminoAcidList.add(new AminoAcid(i + 1, aminoAcidData[0].substring(i, i + 1)
					, aminoAcidData[1].substring(i, i + 1)
					, aminoAcidData[2].substring(i, i + 1)
					, aminoAcidData[3].substring(i, i + 1)
					));
	}
	
	private List<AminoAcid> aminoAcidList = null;
	private Model model;
}
