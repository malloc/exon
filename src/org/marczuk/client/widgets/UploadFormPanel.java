package org.marczuk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UploadFormPanel extends FormPanel {

	public UploadFormPanel() {

	   final UploadFormPanel uploadFormPanel = this;
	   VerticalPanel verticalPanel = new VerticalPanel();
	
	   this.setMethod(FormPanel.METHOD_POST);
	   this.setEncoding(FormPanel.ENCODING_MULTIPART);
	   this.setAction("/filesuploader");

	   this.setWidget(verticalPanel);
	   
	   FileUpload fileUpload = new FileUpload();
	   fileUpload.setName("uploader");
	   verticalPanel.add(fileUpload);
	
	   verticalPanel.add(new Button("Send file", new ClickHandler() {
		   public void onClick(ClickEvent event) {
			   uploadFormPanel.submit();
		   }
	   }));
	   
	   this.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				Window.alert(event.getResults());
			}
	   });
	}
}
