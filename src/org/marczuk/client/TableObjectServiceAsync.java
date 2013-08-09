package org.marczuk.client;

import java.util.List;

import org.marczuk.controller.AminoAcid;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TableObjectServiceAsync {

	void getAminoAcidList(AsyncCallback<List<AminoAcid>> callback);
	void getFilePath(String test, AsyncCallback<String> callback);
}
