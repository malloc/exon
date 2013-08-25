package org.marczuk.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UploadFormPanel extends VerticalPanel {

	public UploadFormPanel(ClickHandler clickHandler) {		
		FileUploadWidgetFactory widgetFactory = new FileUploadWidgetFactory(clickHandler);
		
		this.add(widgetFactory.createUploadPanel("pdb"));
		this.add(widgetFactory.createUploadPanel("gb"));
		this.add(widgetFactory.createUploadPanel("txt"));		
		
		this.add(widgetFactory.getRunButton());
	}
}
