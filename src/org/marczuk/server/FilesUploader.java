package org.marczuk.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public class FilesUploader extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private boolean saveFileOnDisk(File fileToSave, InputStream inputStream) {
		
		try {
	        OutputStream out = new FileOutputStream(fileToSave);
	        
	    	int read = 0;
	    	byte[] bytes = new byte[1024];
	     
	    	while ((read = inputStream.read(bytes)) != -1) {
	    		out.write(bytes, 0, read);
	    	}
	     
	    	inputStream.close();
	    	out.flush();
	    	out.close();
	    	
	    	return true;
		}
		catch(Exception e) {			
			return false;
		}
	}
	
	private HttpServletResponse setResponseMessage(HttpServletResponse response) throws Exception {
					    	
    	response.setContentType("text/html");
    	response.setCharacterEncoding("UTF-8");

    	PrintWriter printWriter = response.getWriter(); 
    	printWriter.write("Plik załadowany pomyślnie"); 
    	printWriter.close();
    	
    	return response;
	}
	
	private File getOutputFile(String name, HttpServletRequest request) {
		
        File directory = new File("uploads/" + request.getSession().getId());
        directory.mkdir();
        File outputFile = new File(directory, name);
        
        return outputFile;
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!ServletFileUpload.isMultipartContent(request))
			return;
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			FileItemIterator iter = upload.getItemIterator(request);
			
			while (iter.hasNext()) {
			    FileItemStream item = iter.next();		        
		        saveFileOnDisk(getOutputFile(item.getName(), request), item.openStream());

		        //TODO Delevop correct message on fail.
		        setResponseMessage(response);
			    }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}