package org.marczuk.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.marczuk.controller.AminoAcid;
import org.marczuk.controller.ChangedAminoAcid;
import org.marczuk.controller.DataSession;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;

public class Model {

	public Model(String sessionID, String realPath) {
		userDirectory = new File(realPath + "/uploads/" + sessionID);
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
	
	public File saveResultToFile(List<AminoAcid> aminoAcidList, List<ChangedAminoAcid> anomalyData) throws Exception {
		
		File scriptFile = new File(userDirectory, "script.pml");
		File csvFile = new File(userDirectory, "snp.csv");
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
        
        //Write script into file
		FileWriter outFile = new FileWriter(scriptFile);
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
        if(anomalyData != null) {
	        for(ChangedAminoAcid changedAminoAcid : anomalyData) {
	        	String changeTitle = changedAminoAcid.getDefaultLetter() + " -> " 
	        			+ changedAminoAcid.getChangedLetter() 
	        			+ " [" + changedAminoAcid.getPosition() + "]";
	        			
	        	out.print("select " + changeTitle + ", /" + name + "//A/");
	        	out.println(changedAminoAcid.getPosition());
	        	out.println("color grey, " + changeTitle);
	        	out.println("show dots, " + changeTitle);
	        }
        }
        
        out.close();
        
        //Write csv file
		FileWriter outcsvFile = new FileWriter(csvFile);
        PrintWriter outcsv = new PrintWriter(outcsvFile);
        
        outcsv.println("\"Index\", \"Seq (CDS)\", \"Seq (pdb)\", \"2D\", \"Exon number\", \"Change\"");
        
        for(AminoAcid aminoAcid : aminoAcidList) {
        	
        	String change = " ";
        	if(anomalyData != null) {
	        	for(ChangedAminoAcid changedAminoAcid : anomalyData) {
	        		if(changedAminoAcid.getPosition() == aminoAcid.getPosition())
	        			change = changedAminoAcid.getChangedLetter();
	        	}
        	}
        	
        	outcsv.println("\"" + aminoAcid.getPosition() + "\", \"" + 
        			aminoAcid.getFirst() + "\", \"" +
        			aminoAcid.getSecond() + "\", \"" +
        			aminoAcid.getThird() + "\", \"" +
        			aminoAcid.getExon() + "\", \"" +
        			change + "\"");
        }
        
        outcsv.close();

		return zipFile(scriptFile, csvFile, getFile("pdb"));
	}
	
	public File saveSessionToFile(DataSession dataSession) {
		
		//Dodaj zawartość pliku pdb do save'a
		try {
			Scanner scanner = new Scanner(getFile("pdb"));
			StringBuilder content = new StringBuilder();
			while(scanner.hasNext()) {
				content.append(scanner.nextLine() + "\n");
			}
			dataSession.setPdbContent(content.toString());
			dataSession.setPdbName(getFile("pdb").getName());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		//Właściwe tworzenie save'a
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
		
		//Właściwe przywracanie sesji
		try {
			FileInputStream fileInputStream = new FileInputStream(getFile("snp"));
			ObjectInputStream objectInputStream = new ObjectInputStream (fileInputStream);

			DataSession dataSession = (DataSession) objectInputStream.readObject();
			
			httpSession.setAttribute("amino", dataSession);
			
			//Przywracanie pliku pdb
			File pdbFile = new File(userDirectory, dataSession.getPdbName());
			FileWriter fileWriter = new FileWriter(pdbFile);
	        PrintWriter printWriter = new PrintWriter(fileWriter);
	        printWriter.print(dataSession.getPdbContent());
	        printWriter.close();
			
			return true;

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public File getUserDirectory() {
		return userDirectory;
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
