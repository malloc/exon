package org.marczuk.client;

import java.util.List;

import org.marczuk.controller.AminoAcid;
import org.marczuk.controller.ChangedAminoAcid;
import org.marczuk.controller.DataSession;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TableObjectServiceAsync {

	void getAminoAcidList(AsyncCallback<List<AminoAcid>> callback);
	void getFilePath(List<ChangedAminoAcid> anomalyData, AsyncCallback<String> callback);
	void getAminoAcidListFromSession(AsyncCallback<DataSession> callback);
	void setAminoAcidListToSession(List<AminoAcid> aminoAcidList, AsyncCallback<Boolean> callback);
	void setChangedAminoAcidListToSession(List<ChangedAminoAcid> changedAminoAcids, AsyncCallback<Boolean> callback);
	@SuppressWarnings("rawtypes")
	void keepSessionAlive(AsyncCallback callback);
}
