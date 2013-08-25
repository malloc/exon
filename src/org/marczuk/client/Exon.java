package org.marczuk.client;

import java.util.List;

import org.marczuk.client.widgets.AnomalyFlexTable;
import org.marczuk.client.widgets.ExonsCellTable;
import org.marczuk.client.widgets.Menu;
import org.marczuk.client.widgets.SaveAndRestoreFormPanel;
import org.marczuk.client.widgets.UploadFormPanel;
import org.marczuk.client.widgets.UploadTabLayoutPanel;
import org.marczuk.controller.AminoAcid;
import org.marczuk.controller.DataSession;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Exon implements EntryPoint {
	
	private ExonsCellTable exonsCellTable = new ExonsCellTable();
	private AnomalyFlexTable anomalyFlexTable = new AnomalyFlexTable(exonsCellTable);
	
	public void onModuleLoad() {
		
		RootPanel.get("menu").add(new Menu());
		VerticalPanel verticalPanel = new VerticalPanel();
	   
		ClickHandler runClickHandler = new ClickHandler() {
			public void onClick(ClickEvent event) {
				updateCellTable();
			}
		};
	   
		verticalPanel.add(new Button("Pobierz", new ClickHandler() {
			public void onClick(ClickEvent event) {
				getResultFile();
			}
		}));	
		
		//If something is in session
		restoreExonsCellTable();
		
		UploadTabLayoutPanel uploadTabLayoutPanel = new UploadTabLayoutPanel();
		uploadTabLayoutPanel.add(new UploadFormPanel(runClickHandler), "New project");
		uploadTabLayoutPanel.add(new SaveAndRestoreFormPanel(runClickHandler), "Save or restore project");
		
		RootPanel.get("uploadContainer").add(uploadTabLayoutPanel);
		RootPanel.get("uploadContainer").add(verticalPanel);
	   
		RootPanel.get("resultTable").setVisible(false);
		RootPanel.get("resultTable").add(exonsCellTable);
		
		Timer timer = new Timer() {
			@Override
			public void run() {
				refreshPage();
			}
		};
		
		timer.scheduleRepeating(1000 * 60 * 3); //Wyślij żadanie co 3 minuty
		timer.run();
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
				RootPanel.get("resultTable").setVisible(true);
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
		
		final AsyncCallback<DataSession> callback = new AsyncCallback<DataSession>() {
			
			@Override
			public void onSuccess(DataSession result) {
				if(result != null) {
					exonsCellTable.update(result.getAminoAcidList());
					RootPanel.get("resultTable").setVisible(true);
					RootPanel.get("anomalyTable").remove(anomalyFlexTable);
					anomalyFlexTable = new AnomalyFlexTable(exonsCellTable);
					anomalyFlexTable.setData(result.getChangedAminoAcidList());
					RootPanel.get("anomalyTable").add(anomalyFlexTable);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
		   
		tableObjectServiceAsync.getAminoAcidListFromSession(callback);
	}
	
	private void refreshPage() {
		
		TableObjectServiceAsync tableObjectServiceAsync = GWT.create(TableObjectService.class);
		
		final AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {	}
			
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}
		};
		   
		tableObjectServiceAsync.keepSessionAlive(callback);
	}
}
