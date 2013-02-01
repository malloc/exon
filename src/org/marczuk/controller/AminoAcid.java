package org.marczuk.controller;

import java.io.Serializable;

public class AminoAcid implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int position;
	private String first;
	private String second;
	private int exon;
	
	public AminoAcid() {	}

	public AminoAcid(int position, String first, String second, int exon) {

		this.position = position;
		this.first = first;
		this.second = second;
		this.exon = exon;
	}

	public int getPosition() {
		return position;
	}

	public String getFirst() {
		return first;
	}

	public String getSecond() {
		return second;
	}

	public int getExon() {
		return exon;
	}
}
