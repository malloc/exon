package org.marczuk.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TableObjectServiceAsync {

	void getServerMessage(AsyncCallback<String> callback);
}
