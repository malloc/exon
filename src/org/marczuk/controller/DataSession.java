package org.marczuk.controller;

import java.io.Serializable;
import java.util.List;

public class DataSession implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public List<ChangedAminoAcid> getChangedAminoAcidList() {
		return changedAminoAcidList;
	}
	
	public void setChangedAminoAcidList(List<ChangedAminoAcid> changedAminoAcidList) {
		this.changedAminoAcidList = changedAminoAcidList;
	}
	
	public List<AminoAcid> getAminoAcidList() {
		return aminoAcidList;
	}
	
	public void setAminoAcidList(List<AminoAcid> aminoAcidList) {
		this.aminoAcidList = aminoAcidList;
	}
	
	public String getPdbName() {
		return pdbName;
	}

	public void setPdbName(String pdbName) {
		this.pdbName = pdbName;
	}

	public String getPdbContent() {
		return pdbContent;
	}

	public void setPdbContent(String pdbContent) {
		this.pdbContent = pdbContent;
	}

	private List<ChangedAminoAcid> changedAminoAcidList;
	private List<AminoAcid> aminoAcidList;
	private String pdbName;
	private String pdbContent;
}
