package org.marczuk.server;

import java.util.List;

import org.marczuk.client.TableObjectService;
import org.marczuk.controller.AminoAcid;
import org.marczuk.controller.ChangedAminoAcid;
import org.marczuk.controller.Controller;
import org.marczuk.controller.DataSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TableObjectServiceImpl extends RemoteServiceServlet implements TableObjectService {
	
	private static final long serialVersionUID = 1L;

	@Override
	public List<AminoAcid> getAminoAcidList() {

		Controller controller = new Controller(this.getThreadLocalRequest());
		
		try {
			List<AminoAcid> aminoList = controller.getAminoList();
			return aminoList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getFilePath(List<ChangedAminoAcid> anomalyData) {

		Controller controller = new Controller(this.getThreadLocalRequest());
		
		try {
			controller.SaveResultToFile(anomalyData);
			return controller.getResultFilePath();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public DataSession getAminoAcidListFromSession() {
		
		Controller controller = new Controller(this.getThreadLocalRequest());
		
		return controller.restoreAminoAcidListFromSession();
	}

	@Override
	public Boolean setAminoAcidListToSession(List<AminoAcid> aminoAcidList) {

		Controller controller = new Controller(this.getThreadLocalRequest());
		controller.saveAminoAcidListToSession(aminoAcidList);
		
		return null;
	}
	
	@Override
	public Boolean setChangedAminoAcidListToSession(List<ChangedAminoAcid> changedAminoAcids) {

		Controller controller = new Controller(this.getThreadLocalRequest());
		controller.saveChangedAminoAcidListToSession(changedAminoAcids);
		
		return null;
	}

	@Override
	public void keepSessionAlive() {	}
}
