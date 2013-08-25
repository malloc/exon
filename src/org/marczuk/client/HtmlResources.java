package org.marczuk.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface HtmlResources extends ClientBundle {

	public static final HtmlResources INSTANCE = GWT.create(HtmlResources.class);
	
	public static final String HELP_HTML = "help.html"; 
	
	@Source(HELP_HTML)
	public TextResource getHelpHtml();
}
