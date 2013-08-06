package org.marczuk.client.widgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AnomalyFlexTable extends VerticalPanel {

	public AnomalyFlexTable(ExonsCellTable exonsCellTable) {
		this.exonsCellTable = exonsCellTable;

		this.add(getFlexTable());
		this.add(getAddNewHorizontalPanel());
	}
	
	private FlexTable getFlexTable() {
		flexTable = new FlexTable();
		
		flexTable.setText(0, 0, "Position");
		flexTable.setText(0, 1, "Default letter");
		flexTable.setText(0, 2, "Changed letter");
		
		return flexTable;
	}
	
	private HorizontalPanel getAddNewHorizontalPanel() {
		HorizontalPanel addNewPanel = new HorizontalPanel();
		
		final ListBox positionBox = new ListBox();
		for(int i = 0; i < exonsCellTable.getIndexCount(); i++)
			positionBox.addItem(String.valueOf(i + 1));
		
		positionBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				defaultLetterBox.setText(exonsCellTable.getVisibleItem(positionBox.getSelectedIndex()).getThird());
			}
		});
		
		defaultLetterBox = new Label();
		final TextBox changedLetterBox = new TextBox();
		if(exonsCellTable.getVisibleItemCount() > 0)
			defaultLetterBox.setText(exonsCellTable.getVisibleItem(positionBox.getSelectedIndex()).getThird());
		
		Button addButton = new Button("Add new");
		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final int row = flexTable.getRowCount();
				final Button deleteButton = new Button("Delete");

				flexTable.setText(row, 0, positionBox.getValue(positionBox.getSelectedIndex()));	
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
	
	private Label defaultLetterBox;
	private FlexTable flexTable;
	private ExonsCellTable exonsCellTable;
}
