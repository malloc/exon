package org.marczuk.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.marczuk.model.Model;

public class Controller {

	public Controller(HttpServletRequest request) {
		this.sessionID = request.getSession().getId();
		this.model = new Model(sessionID);
		this.AppPath = request.getScheme() + "://" 
				+ request.getServerName() + ":" + request.getServerPort() 
	            + request.getContextPath() + "/";
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
	
	public void SaveResultToFile() throws Exception {
		model.saveToFile("result.zip", "Your session ID was: " + sessionID);
	}
	
	public String getResultFilePath() throws Exception {
		return AppPath + model.getFile("result", "zip").getPath();
	}
	
	private List<AminoAcid> aminoAcidList = null;
	private String sessionID;
	private String AppPath;
	private Model model;
}
