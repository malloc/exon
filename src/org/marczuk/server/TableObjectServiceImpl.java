package org.marczuk.server;

import java.util.List;

import org.marczuk.client.TableObjectService;
import org.marczuk.controller.AminoAcid;
import org.marczuk.controller.Controller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TableObjectServiceImpl extends RemoteServiceServlet implements TableObjectService {
	
	@Override
	public List<AminoAcid> getAminoAcidList() {

		Controller controller = new Controller(this.getThreadLocalRequest());
		
		try {
			List<AminoAcid> aminoList = controller.getAminoList();
			controller.SaveResultToFile();
			return aminoList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getFilePath() {

		Controller controller = new Controller(this.getThreadLocalRequest());
		
		try {
			return controller.getResultFilePath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
