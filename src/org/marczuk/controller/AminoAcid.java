package org.marczuk.controller;

import java.io.Serializable;

public class AminoAcid implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int position;
	private String first;
	private String second;
	private String third;
	private String exon;
	
	public AminoAcid() {	}

	public AminoAcid(int position, String first, String second, String third, String exon) {

		this.position = position;
		this.first = first;
		this.second = second;
		this.third = third;
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

	public String getThird() {
		return third;
	}
	
//	public void setSecond(String second) {
//		this.second = second;
//	}

	public String getExon() {
		return exon;
	}
}
