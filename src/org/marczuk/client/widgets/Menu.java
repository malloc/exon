package org.marczuk.client.widgets;

import org.marczuk.client.HtmlResources;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;

public class Menu extends MenuBar {

	public Menu() {
		this.addItem(new MenuItem("Main", new Command() {
			@Override
			public void execute() {
				RootPanel.get("projectContainer").setVisible(true);
				RootPanel.get("htmlContainer").setVisible(false);
			}
		}));
		this.addItem(new MenuItem("Help", new Command() {
			@Override
			public void execute() {
				RootPanel.get("projectContainer").setVisible(false);
				
				RootPanel htmlContainer = RootPanel.get("htmlContainer");
				htmlContainer.clear();
				HTML htmlPanel = new HTML();
				String html = HtmlResources.INSTANCE.getHelpHtml().getText();
				htmlPanel.setHTML(html);
				htmlContainer.add(htmlPanel);
				htmlContainer.setVisible(true);
			}
		}));
		
		this.addStyleName("menu");
	}
}
