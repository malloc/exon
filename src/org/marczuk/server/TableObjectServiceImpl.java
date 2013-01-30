package org.marczuk.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.marczuk.client.TableObjectService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TableObjectServiceImpl extends RemoteServiceServlet implements TableObjectService {

	private static final long serialVersionUID = 1L;

	@Override
	public String getServerMessage() {
		
		File dir = new File("uploads/" + this.getThreadLocalRequest().getSession().getId());
		File test = new File(dir, "test.txt");
		
		String result = "";

		try {
			System.out.println(test.getAbsolutePath());
			Scanner scanner = new Scanner(test);
			result = scanner.nextLine();
		} catch (FileNotFoundException e) {
			System.out.println("Ni ma takiego pliku :(");
		}
		
		return result;
	}
}
