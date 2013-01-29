package org.marczuk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

public class Exon implements EntryPoint {

	public void onModuleLoad() {

	   final FormPanel form = new FormPanel();
	   VerticalPanel vPanel = new VerticalPanel();

	   form.setMethod(FormPanel.METHOD_POST);
	   form.setEncoding(FormPanel.ENCODING_MULTIPART);
	   form.setAction("/filesuploader");
	   
	   //Receive information from server
	   form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				Window.alert(event.getResults());
			}
	   });
	   
	   form.setWidget(vPanel);
	   
	   FileUpload fileUpload = new FileUpload();
	   fileUpload.setName("uploader");
	   vPanel.add(fileUpload);
	   
	   Label maxUpload =new Label();
	   maxUpload.setText("Maximum upload file size: 1MB, Session: ");
	   vPanel.add(maxUpload);
	
	   vPanel.add(new Button("Submit", new ClickHandler() {
		   public void onClick(ClickEvent event) {
			   Cookies.setCookie("GWT", "Test");
			   form.submit();
		   }
	   }));
	   
	   RootPanel.get("uploadContainer").add(form);
	}
}
