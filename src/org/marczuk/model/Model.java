package org.marczuk.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpSession;

import org.marczuk.controller.AminoAcid;
import org.marczuk.controller.ChangedAminoAcid;
import org.marczuk.controller.DataSession;

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
	
	public File saveResultToFile(String fileName, List<AminoAcid> aminoAcidList, List<ChangedAminoAcid> anomalyData) throws Exception {
		
		File file = new File(userDirectory, fileName);
        String[] color = {"blue", "cyan", "orange", "red", "violet", "yellow", "green", "magenta", "purple", "redorange", "white"};
        Map<String, String> resultMap = new TreeMap<String, String>();
        
        for(AminoAcid aminoAcid : aminoAcidList) {
        	String position = resultMap.get(aminoAcid.getExon());
        	
        	if(position == null) {
        		resultMap.put(aminoAcid.getExon(), String.valueOf(aminoAcid.getPosition()));
        	} else {        		
        		position += "+" + aminoAcid.getPosition();
        		resultMap.put(aminoAcid.getExon(), position);
        	}
        }
        
		FileWriter outFile = new FileWriter(file);
        PrintWriter out = new PrintWriter(outFile);
        
        out.println("load " + getFile("pdb").getName());
        out.println("color white");

        String name = getFile("pdb").getName().replace(".pdb", "");
        
        //Write exons in file
        for (String key : resultMap.keySet()) {
        	if(!key.equals(" ")) {
	        	out.print("select exon" + key + ", /" + name + "//A/");
	        	out.println(resultMap.get(key));
	        	out.println("color " + color[Integer.parseInt(key)] + ", exon" + key);
        	}
        }
        
        //Write anomaly in file
        for(ChangedAminoAcid changedAminoAcid : anomalyData) {
        	out.print("select " + changedAminoAcid.getChangedLetter() + ", /" + name + "//A/");
        	out.println(changedAminoAcid.getPosition());
        	out.println("color grey, " + changedAminoAcid.getChangedLetter());
        	out.println("show dots, " + changedAminoAcid.getChangedLetter());
        }
        
        out.close();

		return zipFile(file, getFile("pdb"));
	}
	
	public File saveSessionToFile(DataSession dataSession) {
		
		try {
			File outputFile = new File(userDirectory, "save.snp");
			FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream (fileOutputStream);
			objectOutputStream.writeObject(dataSession);
			
			return getFile("snp");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Boolean restoreSessionFromFile(HttpSession httpSession) {
		
		try {
			FileInputStream fileInputStream = new FileInputStream(getFile("snp"));
			ObjectInputStream objectInputStream = new ObjectInputStream (fileInputStream);

			DataSession dataSession = (DataSession) objectInputStream.readObject();
			
			httpSession.setAttribute("amino", dataSession);
			
			return true;

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
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
 	
	public File getFile(String extension) {
		
		for(File file : userDirectory.listFiles()) {
			if(file.getName().contains("." + extension))
				return file;
		}
		
		return null;
	}
	
	public File getFile(String fileName, String extension) {
		
		for(File file : userDirectory.listFiles()) {
			if(file.getName().contains(fileName + "." + extension))
				return file;
		}
		
		return null;
	}
	
	private boolean isNumeric(String str) {
	  
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	private File zipFile(File ... files) {		
		File zipFile = new File(userDirectory , "result.zip");
       
		byte[] buffer = new byte[1024];
       
		try {
		FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
         
			for (int i = 0; i < files.length; i++) {
				FileInputStream fileInputStream = new FileInputStream(files[i]);
				zipOutputStream.putNextEntry(new ZipEntry(files[i].getName()));
				int length;
	
				while((length = fileInputStream.read(buffer)) > 0) {
					zipOutputStream.write(buffer, 0, length);
				}
	
				zipOutputStream.closeEntry();
				fileInputStream.close();
			}
			zipOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return zipFile;
	}
	
	private File userDirectory;
	private Set<Integer> indexes = new LinkedHashSet<Integer>();
}
