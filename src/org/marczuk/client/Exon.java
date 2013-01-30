package org.marczuk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

public class Exon implements EntryPoint {

	private TableObjectServiceAsync tableObjectSvc = GWT.create(TableObjectService.class);
	
	public void onModuleLoad() {

	   final FormPanel form = new FormPanel();
	   VerticalPanel vPanel = new VerticalPanel();

	   form.setMethod(FormPanel.METHOD_POST);
	   form.setEncoding(FormPanel.ENCODING_MULTIPART);
	   form.setAction("/filesuploader");
	   
	   final Label maxUpload =new Label();
	   
	   
	   if (tableObjectSvc == null) {
		   tableObjectSvc = GWT.create(TableObjectService.class);
	   }
	   
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
		   }
	   }));
	   
	   RootPanel.get("uploadContainer").add(form);
	}
}
