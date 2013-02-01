package org.marczuk.client;

import java.util.ArrayList;
import java.util.List;

import org.marczuk.controller.AminoAcid;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Exon implements EntryPoint {

	private TableObjectServiceAsync tableObjectSvc = GWT.create(TableObjectService.class);
	private TableObjectServiceAsync forTabletest = GWT.create(TableObjectService.class);
	
	public void onModuleLoad() {

	   //Cell Table
		
	   final CellTable<AminoAcid> cellTable = new CellTable<AminoAcid>();
	   cellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
	   
	   TextColumn<AminoAcid> indexColumn = new TextColumn<AminoAcid>() {
			@Override
			public String getValue(AminoAcid object) {
				return new Integer(object.getPosition()).toString();
			}
	   };
	   
	   TextColumn<AminoAcid> firstColumn = new TextColumn<AminoAcid>() {
			@Override
			public String getValue(AminoAcid object) {
				return object.getFirst();
			}
	   };
	   
	   TextColumn<AminoAcid> secondColumn = new TextColumn<AminoAcid>() {
			@Override
			public String getValue(AminoAcid object) {
				return object.getSecond();
			}
	   };
	   
	   cellTable.addColumn(indexColumn, "Index");
	   cellTable.addColumn(firstColumn, "First");
	   cellTable.addColumn(secondColumn, "Second");
		
	   //Cell Table
		
	   final FormPanel form = new FormPanel();
	   VerticalPanel vPanel = new VerticalPanel();

	   form.setMethod(FormPanel.METHOD_POST);
	   form.setEncoding(FormPanel.ENCODING_MULTIPART);
	   form.setAction("/filesuploader");
	   
	   final Label maxUpload =new Label();
	   
	   
	   if (tableObjectSvc == null) {
		   tableObjectSvc = GWT.create(TableObjectService.class);
	   }
	   
	   //file upload
	   final AsyncCallback<String> callback = new AsyncCallback<String>() {
		      public void onFailure(Throwable caught) {
		    	  caught.printStackTrace();
		    	  System.out.println("Error");
		      }

				@Override
				public void onSuccess(String result) {
			        maxUpload.setText(result);	
				}
	   };
	   
	   //table test
	   final AsyncCallback<List<AminoAcid>> callback2 = new AsyncCallback<List<AminoAcid>>() {
		      public void onFailure(Throwable caught) {
		    	  caught.printStackTrace();
		    	  System.out.println("Error");
		      }

				@Override
				public void onSuccess(List<AminoAcid> result) {
					   cellTable.setRowCount(result.size(), true);
					   cellTable.setRowData(0, result);	
				}
	   };
	   	   
	   //Receive information from server
	   form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				Window.alert(event.getResults());
				tableObjectSvc.getServerMessage(callback);
			}
	   });

	   form.setWidget(vPanel);
	   
	   FileUpload fileUpload = new FileUpload();
	   fileUpload.setName("uploader");
	   vPanel.add(fileUpload);
	   
	   maxUpload.setText("Maximum upload file size: 1MB, Session: ");
	   vPanel.add(maxUpload);
	
	   vPanel.add(new Button("Submit", new ClickHandler() {
		   public void onClick(ClickEvent event) {
			   form.submit();
			   
			   forTabletest.aminoList(callback2);
		   }
	   }));
	   
	   RootPanel.get("uploadContainer").add(form);
	   RootPanel.get("tableTest").add(cellTable);
	}
}
