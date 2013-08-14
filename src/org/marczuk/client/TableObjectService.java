package org.marczuk.client;

import java.util.List;

import org.marczuk.controller.AminoAcid;
import org.marczuk.controller.ChangedAminoAcid;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("tableObjects")
public interface TableObjectService extends RemoteService {

	List<AminoAcid> getAminoAcidList();
	String getFilePath(List<ChangedAminoAcid> anomalyData);
	List<AminoAcid> getAminoAcidListFromSession();
	Boolean setAminoAcidListToSession(List<AminoAcid> aminoAcidList);
	void keepSessionAlive();
}
