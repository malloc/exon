package org.marczuk.client.widgets;

import java.util.List;

import org.marczuk.controller.AminoAcid;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;

public class ExonsCellTable extends CellTable<AminoAcid> {

	public ExonsCellTable() {
	
	   indexCount = 0;
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
	   this.addColumn(firstColumn, "Seq (CDS)");
	   this.addColumn(secondColumn, "Seq (pdb)");
	   this.addColumn(thirdColumn, "2D");
	   this.addColumn(exonColumn, "Exon number");
	   
	   this.setStyleName("CSSTableGenerator");
	}
	
	public void update(List<AminoAcid> list) {
	   this.setPageSize(list.size());
       this.setVisibleRange(0, list.size());
	   this.setRowCount(list.size(), true);
	   this.setRowData(0, list);
	   
	   indexCount = list.size();
	}
	
	public int getIndexCount() {
		return indexCount;
	}
	
	private int indexCount;
}
