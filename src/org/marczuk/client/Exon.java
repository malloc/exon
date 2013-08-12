package org.marczuk.client;

import java.util.List;

import org.marczuk.client.widgets.AnomalyFlexTable;
import org.marczuk.client.widgets.ExonsCellTable;
import org.marczuk.client.widgets.UploadFormPanel;
import org.marczuk.controller.AminoAcid;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Exon implements EntryPoint {
	
	private ExonsCellTable exonsCellTable = new ExonsCellTable();
	private AnomalyFlexTable anomalyFlexTable = new AnomalyFlexTable(exonsCellTable);
	
	public void onModuleLoad() {
		VerticalPanel verticalPanel = new VerticalPanel();
	   
		Button startButton = new Button("Run", new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("resultTable").setVisible(true);
				updateCellTable();
			}
		});
		startButton.setEnabled(false);
		verticalPanel.add(startButton);
	   
		verticalPanel.add(new Button("Pobierz", new ClickHandler() {
			public void onClick(ClickEvent event) {
				getResultFile();
			}
		}));	
		
		//If something is in session
		restoreExonsCellTable();
		
		RootPanel.get("uploadContainer").add(new UploadFormPanel(startButton));
		RootPanel.get("uploadContainer").add(verticalPanel);
	   
//		RootPanel.get("resultTable").setVisible(false);
		RootPanel.get("resultTable").add(exonsCellTable);
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
				RootPanel.get("anomalyTable").remove(anomalyFlexTable);
				anomalyFlexTable = new AnomalyFlexTable(exonsCellTable);
				RootPanel.get("anomalyTable").add(anomalyFlexTable);
				
				saveExonsCellTable(result);
			}
		};
		   
		tableObjectServiceAsync.getAminoAcidList(callback);
	}
	
	private void getResultFile() {
		
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
		   
		tableObjectServiceAsync.getFilePath(anomalyFlexTable.getData(), callback);
	}
	
	private void saveExonsCellTable(List<AminoAcid> aminoAcidList) {
		
		TableObjectServiceAsync tableObjectServiceAsync = GWT.create(TableObjectService.class);
		
		final AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
		   
		tableObjectServiceAsync.setAminoAcidListToSession(aminoAcidList, callback);
	}
	
	private void restoreExonsCellTable() {
		
		TableObjectServiceAsync tableObjectServiceAsync = GWT.create(TableObjectService.class);
		
		final AsyncCallback<List<AminoAcid>> callback = new AsyncCallback<List<AminoAcid>>() {
			
			@Override
			public void onSuccess(List<AminoAcid> result) {
				if(result != null) {
					exonsCellTable.update(result);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
		   
		tableObjectServiceAsync.getAminoAcidListFromSession(callback);
	}
}
