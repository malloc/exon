package org.marczuk.client.widgets;

import org.marczuk.client.TableObjectService;
import org.marczuk.client.TableObjectServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SaveAndRestoreFormPanel extends HorizontalPanel {

	public SaveAndRestoreFormPanel(ClickHandler clickHandler) {
		DecoratorPanel restoreDecoratorPanel = new DecoratorPanel();
		DecoratorPanel saveDecoratorPanel = new DecoratorPanel();
		VerticalPanel restoreVerticalPanel = new VerticalPanel();
		VerticalPanel saveVerticalPanel = new VerticalPanel();
		
		Label downloadLabel = new Label("Restore project from file");
		downloadLabel.setHorizontalAlignment(ALIGN_CENTER);
		downloadLabel.setHeight("40px");
		restoreVerticalPanel.add(downloadLabel);
		
		Label saveLabel = new Label("Save project to file");
		saveLabel.setHorizontalAlignment(ALIGN_CENTER);
		saveLabel.setHeight("40px");
		saveVerticalPanel.add(saveLabel);
		
		saveVerticalPanel.setSize("200px", "139px");
		saveVerticalPanel.setHorizontalAlignment(ALIGN_CENTER);
		
		FileUploadWidgetFactory widgetFactory = new FileUploadWidgetFactory(clickHandler);
		
		restoreVerticalPanel.add(widgetFactory.createUploadPanel("snp"));		
		restoreVerticalPanel.add(widgetFactory.getRunButton());
		
		restoreDecoratorPanel.add(restoreVerticalPanel);
		saveDecoratorPanel.add(saveVerticalPanel);
		
		saveVerticalPanel.add(new Button("Save project to file", new ClickHandler() {
			public void onClick(ClickEvent event) {
				getSavedProjectFile();
			}
		}));

		this.add(restoreDecoratorPanel);
		this.add(saveDecoratorPanel);
		
		this.setSpacing(23);
		
	}
	
	private void getSavedProjectFile() {
		
		TableObjectServiceAsync tableObjectServiceAsync = GWT.create(TableObjectService.class);
		
		final AsyncCallback<String> callback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				Window.open(result, "_blank", null);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
		
		tableObjectServiceAsync.getSavedSessionPath(callback);
	}
}
