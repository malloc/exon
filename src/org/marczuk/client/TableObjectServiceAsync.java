package org.marczuk.client;

import java.util.List;

import org.marczuk.controller.AminoAcid;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TableObjectServiceAsync {

	void getServerMessage(AsyncCallback<String> callback);

	void aminoList(AsyncCallback<List<AminoAcid>> callback);
}
