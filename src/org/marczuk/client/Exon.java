package org.marczuk.client;

import java.util.List;

import org.marczuk.client.widgets.ExonsCellTable;
import org.marczuk.client.widgets.UploadFormPanel;
import org.marczuk.controller.AminoAcid;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Exon implements EntryPoint {
	
	private final ExonsCellTable exonsCellTable = new ExonsCellTable();
	
	public void onModuleLoad() {	   
	   
	   VerticalPanel verticalPanel = new VerticalPanel();
	   verticalPanel.add(new Button("Literki", new ClickHandler() {
		   public void onClick(ClickEvent event) {
			   updateCellTable();
		   }
	   }));	   
	   
	   RootPanel.get("uploadContainer").add(new UploadFormPanel());
	   RootPanel.get("tableTest").add(verticalPanel);
	   RootPanel.get("tableTest").add(exonsCellTable);
	}
	
	private void updateCellTable() {
		
		TableObjectServiceAsync tableObjectServiceAsync = GWT.create(TableObjectService.class);
		
		final AsyncCallback<List<AminoAcid>> callback = new AsyncCallback<List<AminoAcid>>() {
		   
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
			
			@Override
			public void onSuccess(List<AminoAcid> result) {
				exonsCellTable.update(result);
			}
		};
		   
		tableObjectServiceAsync.getAminoAcidList(callback);
	}
}
