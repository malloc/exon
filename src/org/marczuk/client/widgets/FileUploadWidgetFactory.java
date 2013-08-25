package org.marczuk.client.widgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;

public class FileUploadWidgetFactory {
	
	public FileUploadWidgetFactory(ClickHandler clickHandler) {
		this.runButton = new Button("Run", clickHandler);
		this.runButton.setEnabled(false);
	}
	
	public HorizontalPanel createUploadPanel(final String name) {
		
		final Label statusLabel = new Label("Upload file: " + name);
		statusLabel.setStyleName("sendFile");
		
		final FormPanel formPanel = new FormPanel();
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setAction("exon/filesuploader");
		formPanel.setHeight("10px");

		formPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
//				if(event.getResults()) {
					//Zabezpieczenie przed kliknięciem wyślij gdy plik jest już poprawnie załadowany
					if(!statusLabel.getText().equals("OK")) {
						statusLabel.setText("OK");
						statusLabel.setStyleName("fileOk");
					
						if(--count == 0) {
							runButton.setEnabled(true);
						}
					}
//				}
			}
		});

		final Button sendFileButton = new Button("Send file", new ClickHandler() {
			public void onClick(ClickEvent event) {
				formPanel.submit();
			}
		}); 
		
		sendFileButton.setEnabled(false);

		final FileUpload fileUpload = new FileUpload();
		fileUpload.setName(name);
		
		fileUpload.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {				
				sendFileButton.setEnabled(false);
				if(statusLabel.getText().equals("OK")) {
					statusLabel.setText("Upload file: " + name);
					count++;
				}
				statusLabel.setStyleName("sendFile");
				
				if(fileUpload.getFilename().contains("." + name))
					sendFileButton.setEnabled(true);
				else
					statusLabel.setText("Wrong file. Upload file: " + name);
			}
		});
		
		formPanel.add(fileUpload);
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		horizontalPanel.setSpacing(10);
		horizontalPanel.add(formPanel);
		horizontalPanel.add(sendFileButton);
		horizontalPanel.add(statusLabel);
		
		count++;
		
		return horizontalPanel;
	}
	
	public HorizontalPanel getRunButton() {
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		horizontalPanel.setSpacing(10);
		horizontalPanel.add(runButton);
		
		return horizontalPanel;
	}
	
	private int count;
	private Button runButton;
}
