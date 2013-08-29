package org.marczuk.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class DownloadPanel extends HorizontalPanel {

	public DownloadPanel(ClickHandler clickHandler) {
		
		this.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		this.setStyleName("downloadPanel");
		this.add(new Label("Download result containing: pymol sript and CVS file "));
		this.add(new Button("Download", clickHandler));
	}
}
