package org.marczuk.client.widgets;

import java.util.List;

import org.marczuk.controller.AminoAcid;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;

public class ExonsCellTable extends CellTable<AminoAcid> {

	public ExonsCellTable() {
	
	   this.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.DISABLED);
	   
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
	   
	   TextColumn<AminoAcid> thirdColumn = new TextColumn<AminoAcid>() {
			@Override
			public String getValue(AminoAcid object) {
				return object.getThird();
			}
	   };
	   
	   TextColumn<AminoAcid> exonColumn = new TextColumn<AminoAcid>() {
			@Override
			public String getValue(AminoAcid object) {
				return object.getExon();
			}
	   };
	   
	   this.addColumn(indexColumn, "Index");
	   this.addColumn(firstColumn, "First");
	   this.addColumn(secondColumn, "Second");
	   this.addColumn(thirdColumn, "Third");
	   this.addColumn(exonColumn, "Exon");
	}
	
	public void update(List<AminoAcid> list) {
	   this.setPageSize(list.size());
       this.setVisibleRange(0, list.size());
	   this.setRowCount(list.size(), true);
	   this.setRowData(0, list);
	}
}
