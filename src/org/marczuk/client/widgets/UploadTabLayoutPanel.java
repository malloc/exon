package org.marczuk.client.widgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class UploadTabLayoutPanel extends TabLayoutPanel {

	public UploadTabLayoutPanel(double barHeight, Unit barUnit) {
		super(barHeight, barUnit);
	}

	public UploadTabLayoutPanel() {
		super(2.5, Unit.EM);
		this.setAnimationDuration(1000);
		this.getElement().getStyle().setMarginBottom(10.0, Unit.PX);
		this.setHeight("243px");
	}
}
