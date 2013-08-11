package org.marczuk.controller;

import java.io.Serializable;

public class ChangedAminoAcid implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int position;
	private String defaultLetter;
	private String changedLetter;
	
	public ChangedAminoAcid() {	}
	
	public ChangedAminoAcid(int position, String defaultLetter, String changedLetter) {
		this.position = position;
		this.defaultLetter = defaultLetter;
		this.changedLetter = changedLetter;
	}
	
	public int getPosition() {
		return position;
	}
	
	public String getDefaultLetter() {
		return defaultLetter;
	}
	
	public String getChangedLetter() {
		return changedLetter;
	}
}
