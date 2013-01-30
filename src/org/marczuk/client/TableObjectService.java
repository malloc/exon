package org.marczuk.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("tableObjects")
public interface TableObjectService extends RemoteService {

	String getServerMessage();
}
