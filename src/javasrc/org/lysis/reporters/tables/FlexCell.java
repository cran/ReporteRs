/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.tables;

import java.math.BigInteger;
import java.util.LinkedHashMap;

import org.docx4j.dml.CTTableCell;
import org.docx4j.dml.CTTableCellProperties;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.TcPrInner.GridSpan;
import org.docx4j.wml.TcPrInner.VMerge;
import org.lysis.reporters.html4r.elements.HTML4R;
import org.lysis.reporters.html4r.tools.Format;
import org.lysis.reporters.texts.ParagraphsSection;

public class FlexCell implements HTML4R, Cloneable{
	private ParagraphsSection section;
	private CellProperties cellProproperties;
	private int colspan;
	private int rowspan;
	
	public FlexCell(ParagraphsSection p, CellProperties cp){
		setParagraphsSection(p);
		setCellProperties(cp);
		colspan = 1;
		rowspan = 1;		
	}
	
	
	
	public int getColspan(){
		return colspan;
	}
	
	public void setColspan(int value){
		colspan = value;
	}
	public void setParagraphsSection(ParagraphsSection p){
		section = p;
	}
	
	public void setCellProperties(CellProperties cp){
		cellProproperties = cp;
	}
	public void setBorderBottom(BorderProperties bp){
		cellProproperties.setBorderBottom(bp);
	}
	public void setBorderTop(BorderProperties bp){
		cellProproperties.setBorderTop(bp);
	}
	public void setBorderLeft(BorderProperties bp){
		cellProproperties.setBorderLeft(bp);
	}
	public void setBorderRight(BorderProperties bp){
		cellProproperties.setBorderRight(bp);
	}
	public void setRowspan(int value){
		rowspan = value;
	}
	
	public String toString(){
		String out = "";
		out += section.toString();
		return out;
	}

	@Override
	public String getHTML() {
		if( rowspan < 1 ) return "";
		if( colspan < 1 ) return "";
		LinkedHashMap<String, String> cpr = Format.getCellProperties(
				cellProproperties.getBorderBottom().getBorderColor(), cellProproperties.getBorderBottom().getBorderStyle(), cellProproperties.getBorderBottom().getBorderWidth()
				, cellProproperties.getBorderLeft().getBorderColor(), cellProproperties.getBorderLeft().getBorderStyle(), cellProproperties.getBorderLeft().getBorderWidth()
				, cellProproperties.getBorderTop().getBorderColor(), cellProproperties.getBorderTop().getBorderStyle(), cellProproperties.getBorderTop().getBorderWidth()
				, cellProproperties.getBorderRight().getBorderColor(), cellProproperties.getBorderRight().getBorderStyle(), cellProproperties.getBorderRight().getBorderWidth()
				, cellProproperties.getVerticalAlign()
				, cellProproperties.getPaddingBottom()
				, cellProproperties.getPaddingTop()
				, cellProproperties.getPaddingLeft()
				, cellProproperties.getPaddingRight(), cellProproperties.getBackgroundColor()
				);
		
		String out = "<td style=\"" + Format.getJSString(cpr) + "\"";
		if( colspan > 1 ) out += " colspan=\"" + colspan + "\"";
		if( rowspan > 1 ) out += " rowspan=\"" + rowspan + "\"";

		out += ">";
		out += section.getHTML();
		out += "</td>";
		
		return out;
	}


	public Tc getTc(){
		Tc tc = new Tc();
		tc.getContent().addAll(section.getP());
		TcPr tcPr = org.lysis.reporters.docx4r.tools.Format.getCellProperties(
			  cellProproperties.getBorderBottom().getBorderColor(), cellProproperties.getBorderBottom().getBorderStyle(), cellProproperties.getBorderBottom().getBorderWidth()
			, cellProproperties.getBorderLeft().getBorderColor(), cellProproperties.getBorderLeft().getBorderStyle(), cellProproperties.getBorderLeft().getBorderWidth()
			, cellProproperties.getBorderTop().getBorderColor(), cellProproperties.getBorderTop().getBorderStyle(), cellProproperties.getBorderTop().getBorderWidth()
			, cellProproperties.getBorderRight().getBorderColor(), cellProproperties.getBorderRight().getBorderStyle(), cellProproperties.getBorderRight().getBorderWidth()
			, cellProproperties.getVerticalAlign()
			, cellProproperties.getPaddingBottom()
			, cellProproperties.getPaddingTop()
			, cellProproperties.getPaddingLeft()
			, cellProproperties.getPaddingRight(), cellProproperties.getBackgroundColor()
			);
		if( colspan > 1 ){
			GridSpan gridSpan = new GridSpan();
	        gridSpan.setVal(BigInteger.valueOf(colspan));
	        tcPr.setGridSpan(gridSpan);
		}
		
		if( rowspan > 1 ){
			VMerge merge = new VMerge();
			merge.setVal("restart");
			tcPr.setVMerge(merge);
		} else if( rowspan < 1 ){
			VMerge merge = new VMerge();
			merge.setVal("continue");
			tcPr.setVMerge(merge);
		}
		tc.setTcPr(tcPr);
		return tc;
	}
	
	public CTTableCell getCTTableCell() throws Exception{
		CTTableCell tc = new CTTableCell();
		tc.setTxBody(section.getCTTextBody());
		CTTableCellProperties tcPr = org.lysis.reporters.pptx4r.tools.Format.getCellProperties(
			  cellProproperties.getBorderBottom().getBorderColor(), cellProproperties.getBorderBottom().getBorderStyle(), cellProproperties.getBorderBottom().getBorderWidth()
			, cellProproperties.getBorderLeft().getBorderColor(), cellProproperties.getBorderLeft().getBorderStyle(), cellProproperties.getBorderLeft().getBorderWidth()
			, cellProproperties.getBorderTop().getBorderColor(), cellProproperties.getBorderTop().getBorderStyle(), cellProproperties.getBorderTop().getBorderWidth()
			, cellProproperties.getBorderRight().getBorderColor(), cellProproperties.getBorderRight().getBorderStyle(), cellProproperties.getBorderRight().getBorderWidth()
			, cellProproperties.getVerticalAlign()
			, cellProproperties.getPaddingBottom()
			, cellProproperties.getPaddingTop()
			, cellProproperties.getPaddingLeft()
			, cellProproperties.getPaddingRight(), cellProproperties.getBackgroundColor()
			);
		
		if( colspan > 1 ){
			tc.setGridSpan(colspan);
		} else if( colspan < 1 ){
			tc = new CTTableCell();
			tc.setHMerge(true);
			CTTableCellProperties temp = new CTTableCellProperties();
			temp.setNoFill(tcPr.getNoFill());
			tc.setTcPr(temp);
			return tc;
		}

		if( rowspan > 1 )
			tc.setRowSpan(rowspan);
		else if( rowspan < 1 ){
			tc.setVMerge(true);
		}
		if( colspan > 0 )
			tc.setTcPr(tcPr);
		return tc;
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
	
	public FlexCell clone() {
		FlexCell fc = new FlexCell(this.section, this.cellProproperties);
	    return fc;
  	}
	
}
