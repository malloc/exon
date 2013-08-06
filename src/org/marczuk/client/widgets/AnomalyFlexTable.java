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
		flexTable.setText(0, 1, "Default");
		flexTable.setText(0, 2, "Changeds");
		
		flexTable.setStyleName("CSSTableGenerator");
		
		return flexTable;
	}
	
	private HorizontalPanel getAddNewHorizontalPanel() {
		HorizontalPanel addNewPanel = new HorizontalPanel();
		
		positionBox = new ListBox();
		for(int i = 0; i < exonsCellTable.getIndexCount(); i++)
			positionBox.addItem(String.valueOf(i + 1));
		
		positionBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				setDefaultLetterFromSelection();
			}
		});
		
		defaultLetterBox = new Label();
		changedLetterBox = new TextBox();
		addButton = new Button("Add new");
		setDefaultLetterFromSelection();
		
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
		addNewPanel.setStyleName("CSSTableGenerator");
		
		return addNewPanel;
	}
	
	private void setDefaultLetterFromSelection() {
		if(exonsCellTable.getVisibleItemCount() > 0) {
			String defaultLetter = exonsCellTable.getVisibleItem(positionBox.getSelectedIndex()).getThird();
			defaultLetterBox.setText(defaultLetter);
			
			if(defaultLetter.equals(" ")) {
				changedLetterBox.setEnabled(false);
				addButton.setEnabled(false);
			} else {
				changedLetterBox.setEnabled(true);
				addButton.setEnabled(true);				
			}
		}
	}
	
	private ListBox positionBox;
	private Label defaultLetterBox;
	private TextBox changedLetterBox;
	private Button addButton;
	private FlexTable flexTable;
	private ExonsCellTable exonsCellTable;
}
