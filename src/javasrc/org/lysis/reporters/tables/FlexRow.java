/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.tables;

import java.util.LinkedHashMap;

import org.docx4j.dml.CTTableRow;
import org.docx4j.wml.Tr;
import org.lysis.reporters.html4r.elements.HTML4R;

public class FlexRow implements HTML4R{
	
	private LinkedHashMap<Integer, FlexCell> cellList;
	private int index;
	
	public FlexRow(){
		index = 0;
		cellList = new LinkedHashMap<Integer, FlexCell>();
	}
	
	public void add(FlexCell fc){
		index++;
		cellList.put(index, fc);
	}
	public void add(FlexCell fc, int i){
		cellList.put(i, fc);
	}
	public int size(){
		return index ;
	}
	public int weight(){
		int out = 0;
		if( index > 0 )
			for(int i = 1 ; i < index + 1 ; i++ ){
				out += cellList.get(i).getColspan();
			}
		
		return out ;
	}
	public FlexCell getCell(int index){
		return cellList.get(index);
	}
	
	public String toString(){
		String out = "";
		if( index > 0 )
			for(int i = 1 ; i < index + 1 ; i++ ){
				out += cellList.get(i).toString();
			}
		return out;
	}

	@Override
	public String getHTML() {
		
		
		String out = "<tr>";
		for( int i = 1 ; i <= index ; i++ ){
			out += cellList.get(i).getHTML();
		}
			
		out += "</tr>";
		
		return out;
	}

	public Tr getTr(){
		Tr workingRow = new Tr();
		for( int i = 1 ; i <= index ; i++ ){
			workingRow.getContent().add(cellList.get(i).getTc());
		}
		return workingRow;
	}
	
	public CTTableRow getCTTableRow() throws Exception{
		CTTableRow workingRow = new CTTableRow();
		for( int i = 1 ; i <= index ; i++ ){
			FlexCell temp = cellList.get(i);
			workingRow.getTc().add(temp.getCTTableCell());
			if( temp.getColspan() > 1 ) {
				for(int p = 1 ; p < temp.getColspan() ; p++ ){
					FlexCell temp1 = temp.clone();
					temp1.setColspan(0);
					workingRow.getTc().add( temp1.getCTTableCell() );
				}
			}
			
		}
		return workingRow;
	}
	
	@Override
	public String getCSS() {
		return "";
	}

	@Override
	public String getJS() {
		return "";
	}

	@Override
	public boolean hasJS() {
		return false;
	}

	@Override
	public boolean hasHTML() {
		return true;
	}

	@Override
	public boolean hasCSS() {
		return false;
	}
	
}
