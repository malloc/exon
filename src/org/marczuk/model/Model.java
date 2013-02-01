package org.marczuk.model;

import java.io.File;
import java.util.Scanner;

public class Model {

	public Model(String sessionID) {
		userDirectory = new File("uploads/" + sessionID);
	}
	
	public String getAminoAcidData() throws Exception {
		
		return getFirstLetters();
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
	
	private File getFile(String extension) {
		
		for(File file : userDirectory.listFiles()) {
			if(file.getName().contains("." + extension))
				return file;
		}
		
		return null;
	}
	
	private File userDirectory;
}
