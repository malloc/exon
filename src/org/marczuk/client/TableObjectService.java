package org.marczuk.client;

import java.util.List;

import org.marczuk.controller.AminoAcid;
import org.marczuk.controller.ChangedAminoAcid;
import org.marczuk.controller.DataSession;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("tableObjects")
public interface TableObjectService extends RemoteService {

	List<AminoAcid> getAminoAcidList();
	String getFilePath(List<ChangedAminoAcid> anomalyData);
	DataSession getAminoAcidListFromSession();
	Boolean setAminoAcidListToSession(List<AminoAcid> aminoAcidList);
	Boolean setChangedAminoAcidListToSession(List<ChangedAminoAcid> changedAminoAcids);
	void keepSessionAlive();
}
