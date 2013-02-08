package org.marczuk.model;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Model {

	public Model(String sessionID) {
		userDirectory = new File("uploads/" + sessionID);
	}
	
	public String[] getAminoAcidData() throws Exception {
		
		String[] aminoAcidData = new String[4];
		
		getIndexes();
		aminoAcidData[0] = getFirstLetters();
		aminoAcidData[1] = "";
		aminoAcidData[2] = "";
		aminoAcidData[3] = "";
		
		String tempSecondLetter = getGroupFormTxtFile("Primary");
		String tempThirdLetter = getGroupFormTxtFile("Secondary");
		String tempExon = getGroupFormTxtFile("Exon");
		
		int index = 0; //index for temps
		for(int i = 0; i < aminoAcidData[0].length(); i++) {
			if(indexes.contains(i + 1)) {
				aminoAcidData[1] += tempSecondLetter.charAt(index);
				aminoAcidData[2] += tempThirdLetter.charAt(index);
				aminoAcidData[3] += tempExon.charAt(index);
				index++;
			} else {
				aminoAcidData[1] += " ";
				aminoAcidData[2] += " ";
				aminoAcidData[3] += " ";
			}
		}
		
		return aminoAcidData;
	}
	
	private void getIndexes() throws Exception {
		
		File pdbFile = getFile("pdb");
		
		if(pdbFile == null)
			return;
		
		Scanner scanner = new Scanner(pdbFile);
		String temp= "";
		int last = 0;
		
		while(scanner.hasNext()) {
			temp = scanner.nextLine();
			if(temp.contains("ATOM ")) {
				String[] split = temp.split("\\s+", 7);
				boolean insert = false; 
				
				for(int i = 0; i < split.length; i++) {
					if(isNumeric(split[i]) && !insert) {
						insert = true;
						continue;
					}
					if(isNumeric(split[i]) && insert) {
						int current = new Integer(split[i]);
						if(current > last) {
							indexes.add(new Integer(split[i]));
							last = current;
						}
						i = split.length; //break
					}
				}
			}
		}
	}
	
	private String getFirstLetters() throws Exception {
		
		File gbFile = getFile("gb");
		
		if(gbFile == null)
			return null;
		
		Scanner scanner = new Scanner(gbFile);
		String result = "";
		String temp= "";
		boolean read = false;
		
		while(scanner.hasNext()) {
			temp = scanner.nextLine();
			if(temp.contains("/translation=\"")) {
				result += temp.replace("/translation=\"", "");
				read = true;
				continue;
			}
			if(read && temp.contains("\"")) {
				result += temp.replace("\"", "");
				return result.trim().replace(" ", "");
			}
			if(read) {
				result += temp;				
			}
		}
		
		return null;
	}
	
	private String getGroupFormTxtFile(String groupName) throws Exception {
		
		File txtFile = getFile("txt");
		String temp = "";
		String secondLetter = "";
		
		if(txtFile == null)
			return null;
		
		Scanner scanner = new Scanner(txtFile);
		
		while(scanner.hasNext()) {
			temp = scanner.nextLine();
			if(temp.contains(groupName))
				secondLetter += temp.substring(temp.indexOf(": ") + 2, temp.length());
		}
		
		return secondLetter;
	}
 	
	private File getFile(String extension) {
		
		for(File file : userDirectory.listFiles()) {
			if(file.getName().contains("." + extension))
				return file;
		}
		
		return null;
	}
	
	private boolean isNumeric(String str) {
	  
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	private File userDirectory;
	private Set<Integer> indexes = new LinkedHashSet<Integer>();
}
