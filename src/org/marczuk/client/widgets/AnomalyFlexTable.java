package org.marczuk.client.widgets;

import java.util.ArrayList;
import java.util.List;

import org.marczuk.client.TableObjectService;
import org.marczuk.client.TableObjectServiceAsync;
import org.marczuk.controller.ChangedAminoAcid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
	
	public List<ChangedAminoAcid> getData() {
		List<ChangedAminoAcid> list = new ArrayList<ChangedAminoAcid>();
		
		for (int i = 1; i < flexTable.getRowCount(); i++) {
			list.add(new ChangedAminoAcid(Integer.parseInt(flexTable.getHTML(i, 0)), 
					flexTable.getHTML(i, 1), 
					flexTable.getHTML(i, 2)));
		}
		
		return list;
	}
	
	public void setData(List<ChangedAminoAcid> list) {
		for(ChangedAminoAcid acid : list) {
			addChangedLetter(String.valueOf(acid.getPosition()), acid.getDefaultLetter(), acid.getChangedLetter());
		}
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
				addChangedLetter(positionBox.getValue(positionBox.getSelectedIndex()), 
						defaultLetterBox.getText(), changedLetterBox.getText());
				
				saveChangedAminoAcidTable(getData());
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
	
	private void addChangedLetter(String position, String defaultLetter, String changedLetter) {
		final int row = flexTable.getRowCount();
		final Button deleteButton = new Button("Delete");
	
		flexTable.setText(row, 0, position);	
		flexTable.setText(row, 1, defaultLetter);
		flexTable.setText(row, 2, changedLetter);
		flexTable.setWidget(row, 3, deleteButton);
		
		deleteButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for(int i = 1; i < flexTable.getRowCount(); i++) {
					if(flexTable.getWidget(i, 3).hashCode() == deleteButton.hashCode())
						flexTable.removeRow(i);
				}
				saveChangedAminoAcidTable(getData());
			}
		});
	}
	
	private void saveChangedAminoAcidTable(List<ChangedAminoAcid> aminoAcidList) {
		
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
		   
		tableObjectServiceAsync.setChangedAminoAcidListToSession(aminoAcidList, callback);
	}
	
	private ListBox positionBox;
	private Label defaultLetterBox;
	private TextBox changedLetterBox;
	private Button addButton;
	private FlexTable flexTable;
	private ExonsCellTable exonsCellTable;
}
