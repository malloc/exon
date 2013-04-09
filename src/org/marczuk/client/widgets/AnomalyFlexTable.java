package org.marczuk.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AnomalyFlexTable extends VerticalPanel {

	public AnomalyFlexTable() {
		this.add(getFlexTable());
		this.add(getAddNewHorizontalPanel());
	}
	
	private FlexTable getFlexTable() {
		flexTable = new FlexTable();
		
		flexTable.setText(0, 0, "Position");
		flexTable.setText(0, 1, "Default letter");
		flexTable.setText(0, 2, "Changed letter");
		
		System.out.println(flexTable.getText(0, 0));
		
		return flexTable;
	}
	
	private HorizontalPanel getAddNewHorizontalPanel() {
		HorizontalPanel addNewPanel = new HorizontalPanel();
		
		final TextBox positionBox = new TextBox();
		final TextBox defaultLetterBox = new TextBox();
		final TextBox changedLetterBox = new TextBox();
		
		Button addButton = new Button("Add new");
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final int row = flexTable.getRowCount();
				final Button deleteButton = new Button("Delete");

				flexTable.setText(row, 0, positionBox.getText());	
				flexTable.setText(row, 1, defaultLetterBox.getText());
				flexTable.setText(row, 2, changedLetterBox.getText());
				flexTable.setWidget(row, 3, deleteButton);
				
				deleteButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						for(int i = 1; i < flexTable.getRowCount(); i++) {
							if(flexTable.getWidget(i, 3).hashCode() == deleteButton.hashCode())
								flexTable.removeRow(i);
						}
					}
				});
			}
		});
		
		addNewPanel.add(positionBox);
		addNewPanel.add(defaultLetterBox);
		addNewPanel.add(changedLetterBox);
		addNewPanel.add(addButton);
		
		return addNewPanel;
	}
	
	private FlexTable flexTable;
}
