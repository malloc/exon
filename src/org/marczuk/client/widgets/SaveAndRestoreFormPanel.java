package org.marczuk.client.widgets;

import org.marczuk.client.TableObjectService;
import org.marczuk.client.TableObjectServiceAsync;
import org.marczuk.controller.DataSession;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SaveAndRestoreFormPanel extends VerticalPanel {

	public SaveAndRestoreFormPanel(ClickHandler clickHandler) {
		DecoratorPanel restoreDecoratorPanel = new DecoratorPanel();
		VerticalPanel restoreVerticalPanel = new VerticalPanel();
		FileUploadWidgetFactory widgetFactory = new FileUploadWidgetFactory(clickHandler);
		
		restoreVerticalPanel.add(widgetFactory.createUploadPanel("snp"));		
		restoreVerticalPanel.add(widgetFactory.getRunButton());
		
		restoreDecoratorPanel.add(restoreVerticalPanel);
		
		this.add(restoreDecoratorPanel);
		
		this.add(new Button("Save project to file", new ClickHandler() {
			public void onClick(ClickEvent event) {
				getSavedProjectFile();
			}
		}));
		
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
