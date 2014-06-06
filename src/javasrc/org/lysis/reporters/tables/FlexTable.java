/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.tables;

import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.dml.CTTable;
import org.docx4j.dml.CTTableCol;
import org.docx4j.dml.CTTableGrid;
import org.docx4j.dml.CTTableProperties;
import org.docx4j.dml.CTTableRow;
import org.docx4j.dml.Graphic;
import org.docx4j.dml.GraphicData;
import org.docx4j.jaxb.Context;
import org.docx4j.wml.CTTblLayoutType;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.STTblLayoutType;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;
import org.lysis.reporters.html4r.elements.HTML4R;
import org.lysis.reporters.texts.ParProperties;
import org.lysis.reporters.texts.Paragraph;
import org.lysis.reporters.texts.ParagraphsSection;
import org.lysis.reporters.texts.TextProperties;
import org.pptx4j.pml.CTGraphicalObjectFrame;

public class FlexTable implements HTML4R {

	private LinkedHashMap<Integer, FlexRow> headerRowList;
	private int headerLines;
	private LinkedHashMap<Integer, FlexRow> footerRowList;
	private int footerLines;

	private int nrow, ncol;
	private ParagraphsSection[][] cellTextValue;
	private CellProperties[][] cellPropertiesIndex;
	protected LinkedHashMap<Integer, int[]> rowSpanInstructions ;
	protected LinkedHashMap<Integer, int[]> colSpanInstructions ;
	protected double[] widths ;
	
	public FlexTable(int n, int c
			, TextProperties tp, ParProperties pp, CellProperties cp ) {
		cellTextValue = new ParagraphsSection[n][c];
		widths = new double[c];
		for( int j = 0 ; j < c ; j++ ) widths[j] = -1;
		
		cellPropertiesIndex = new CellProperties[n][c];
		for(int i = 0 ; i < n ; i++ ){
			for( int j = 0 ; j < c ; j++ ){
				Paragraph par = new Paragraph();
				try {
					par.addText("", tp);
				} catch (IOException e) {
				}
				cellTextValue[i][j] = new ParagraphsSection(pp);
				cellTextValue[i][j].addParagraph(par);
				cellPropertiesIndex[i][j] = cp.getClone();
			}
		}
		
		headerLines = 0;
		headerRowList = new LinkedHashMap<Integer, FlexRow>();
		footerLines = 0;
		footerRowList = new LinkedHashMap<Integer, FlexRow>();
		nrow = n;
		ncol = c;
		rowSpanInstructions = new LinkedHashMap<Integer, int[]>();
		colSpanInstructions = new LinkedHashMap<Integer, int[]>();
	}
	public void setWidths( double[] w ) {
		widths = w;
	}
	
	public void setRowSpanInstructions( int colindex, int[] x ) {
		rowSpanInstructions.put(colindex, x);
	}
	public void setColSpanInstructions( int rowindex, int[] x ) {
		colSpanInstructions.put(rowindex, x);
	}
	
	public void addHeader(FlexRow fr) {
		headerRowList.put(headerLines, fr);
		headerLines++;
	}
	
	public void addFooter(FlexRow fr) {
		footerRowList.put(footerLines, fr);
		footerLines++;
	}
	
	public void resetHeader() {
		headerRowList.clear();
		headerLines=0;
	}
		
	public void addBodyText(int[] i, int[] j, String par[], TextProperties tp, boolean newPar) throws IOException{
		
		int li = i.length;
		int lj = j.length;
		for(int row = 0 ; row < li ; row++ ){
			for( int col = 0 ; col < lj ; col++ ){
				ParagraphsSection value = cellTextValue[i[row]][j[col]];
				if( newPar ) {
					Paragraph p = new Paragraph();
					p.addText(par[row * lj + col], tp);
					value.addParagraph(p);
				} else {
					Paragraph p = value.getLast();
					p.addText(par[row * lj + col], tp);
				}
			}
		}
	}
	
	public void setRowsColors(int[] i, String[] colors){
		int li = i.length;
		for(int row = 0 ; row < li ; row++ ){
			for(int col = 0 ; col < ncol ; col++ ){
				cellPropertiesIndex[i[row]][col].setBackgroundColor(colors[row]);
			}
		}
	}
	
	public void setColumnsColors(int[] j, String[] colors){
		int lj = j.length;
		for(int col = 0 ; col < lj ; col++ ){
			for(int row = 0 ; row < nrow ; row++ ){
				cellPropertiesIndex[row][j[col]].setBackgroundColor(colors[col]);
			}
		}
	}
	
	public void setOddEvenColor(String odd_color, String even_color){
		for(int row = 0 ; row < nrow ; row = row +2 ){
			for(int col = 0 ; col < ncol ; col++ ){
				cellPropertiesIndex[row][col].setBackgroundColor(odd_color);
			}
		}
		for(int row = 1 ; row < nrow ; row = row +2 ){
			for(int col = 0 ; col < ncol ; col++ ){
				cellPropertiesIndex[row][col].setBackgroundColor(even_color);
			}
		}
	}

	public void setBodyBorders(BorderProperties inner_v, BorderProperties inner_h, BorderProperties outer_v, BorderProperties outer_h){
		
		for(int row = 0 ; row < nrow ; row++ ){
			for(int col = 0 ; col < ncol ; col++ ){
				cellPropertiesIndex[row][col].setBorderLeft(inner_v);
				cellPropertiesIndex[row][col].setBorderRight(inner_v);
				cellPropertiesIndex[row][col].setBorderTop(inner_h);
				cellPropertiesIndex[row][col].setBorderBottom(inner_h);
			}
		}
		
		for(int col = 0 ; col < ncol ; col++ ){
			cellPropertiesIndex[0][col].setBorderTop(outer_h);
			cellPropertiesIndex[nrow-1][col].setBorderBottom(outer_h);
		}

		for(int row = 0 ; row < nrow ; row++ ){
			cellPropertiesIndex[row][0].setBorderLeft(outer_v);
			cellPropertiesIndex[row][ncol-1].setBorderRight(outer_v);
		}
	}
	
	public void setHeaderBorders(BorderProperties inner_v, BorderProperties inner_h, BorderProperties outer_v, BorderProperties outer_h){
		
		if( headerLines < 1) return;
		
		FlexRow workingRow;
		FlexCell fc;
		int ncol;
		
		for( int i = 0 ; i < headerLines ; i++ ){
			workingRow = headerRowList.get(i);
			ncol = workingRow.size();
			if( ncol > 0 ){
				for( int j = 1 ; j <= ncol ; j++ ){
					fc = workingRow.getCell(j);
					
					if( i == 0 ) fc.setBorderTop(outer_h);
					else fc.setBorderTop(inner_h);
					
					if( i == (headerLines-1) ) fc.setBorderBottom(outer_h);
					else fc.setBorderBottom(inner_h);
					
					if( j == 1 ) fc.setBorderLeft(outer_v);
					else fc.setBorderLeft(inner_v);
					
					if( j == ncol ) fc.setBorderRight(outer_v);
					else fc.setBorderRight(inner_v);
				}
			}
		}
		
	}
	
	public void setFooterBorders(BorderProperties inner_v, BorderProperties inner_h, BorderProperties outer_v, BorderProperties outer_h){
		
		if( footerLines < 1) return;
		
		FlexRow workingRow;
		FlexCell fc;
		int ncol;
		
		for( int i = 0 ; i < footerLines ; i++ ){
			workingRow = footerRowList.get(i);
			ncol = workingRow.size();
			if( ncol > 0 ){
				for( int j = 1 ; j <= ncol ; j++ ){
					fc = workingRow.getCell(j);
					
					if( i == 0 ) fc.setBorderTop(outer_h);
					else fc.setBorderTop(inner_h);
					
					if( i == (footerLines-1) ) fc.setBorderBottom(outer_h);
					else fc.setBorderBottom(inner_h);
					
					if( j == 1 ) fc.setBorderLeft(outer_v);
					else fc.setBorderLeft(inner_v);
					
					if( j == ncol ) fc.setBorderRight(outer_v);
					else fc.setBorderRight(inner_v);
				}
			}
		}
		
	}
	
	public void setCellProperties(int[] i, int[] j, CellProperties cp ){

		int li = i.length;
		int lj = j.length;
		

		for(int row = 0 ; row < li ; row++ ){
			for( int col = 0 ; col < lj ; col++ ){
				cellPropertiesIndex[i[row]][j[col]] = cp;
			}
		}
	}
	
	public void setParProperties(int[] i, int[] j, ParProperties pp ){
		int li = i.length;
		int lj = j.length;
		for(int row = 0 ; row < li ; row++ ){
			for( int col = 0 ; col < lj ; col++ ){
				cellTextValue[i[row]][j[col]].setParProperties(pp);
			}
		}
	}
	
	public void setTextProperties(int[] i, int[] j, TextProperties tp ){
		int li = i.length;
		int lj = j.length;
		for(int row = 0 ; row < li ; row++ ){
			for( int col = 0 ; col < lj ; col++ ){
				cellTextValue[i[row]][j[col]].setTextProperties(tp);
			}
		}
	}
	
	
	public int headerSize(){
		return headerLines;
	}
	
	public String toString(){
		String out = "";
		for(int i = 0 ; i < nrow ; i++ ){
			out += "\n";
			for( int j = 0 ; j < ncol ; j++ ){
				out += "\t" + cellTextValue[i][j].toString();
			}
		}

		return out;
	}

	
	private String HeaderHTML( ) {
		//Grouped Headers
		String out = "";
		out += "<thead>";
		for( int i = 0 ; i < headerLines ; i++ ){
			out += headerRowList.get(i).getHTML();
		} 
		out += "</thead>";
		return out;
	}
	
	private String FooterHTML( ) {
		//Grouped Headers
		String out = "";
		out += "<tfoot>";
		for( int i = 0 ; i < footerLines ; i++ ){
			out += footerRowList.get(i).getHTML();
		} 
		out += "</tfoot>";
		return out;
	}
	private void HeaderDOCX( Tbl reviewtable ){
		if( headerLines > 0 )
		for( int i = 0 ; i < headerLines ; i++ ){
			Tr workingRow = headerRowList.get(i).getTr();
			TrPr trpr = new TrPr();
			List<JAXBElement<?>> cnfStyleOrDivIdOrGridBefore = trpr.getCnfStyleOrDivIdOrGridBefore();
			cnfStyleOrDivIdOrGridBefore.add(Context.getWmlObjectFactory().createCTTrPrBaseTblHeader(Context.getWmlObjectFactory().createBooleanDefaultTrue()));
			workingRow.setTrPr(trpr);
			reviewtable.getContent().add(workingRow);
		}
	}
	
	private void FooterDOCX( Tbl reviewtable ){
		if( footerLines > 0 )
		for( int i = 0 ; i < footerLines ; i++ ){
			Tr workingRow = footerRowList.get(i).getTr();
			reviewtable.getContent().add(workingRow);
		}
	}
	
	private void HeaderPPTX( CTTable reviewtable ) throws Exception{
		if( headerLines < 1 ) return;

		for( int i = 0 ; i < headerLines ; i++ ){
			CTTableRow workingRow = headerRowList.get(i).getCTTableRow();
			reviewtable.getTr().add(workingRow);
		}
		
	}

	private void FooterPPTX( CTTable reviewtable ) throws Exception{
		if( footerLines < 1 ) return;

		for( int i = 0 ; i < footerLines ; i++ ){
			CTTableRow workingRow = footerRowList.get(i).getCTTableRow();
			reviewtable.getTr().add(workingRow);
		}
		
	}
	private void BodyDOCX( Tbl reviewtable ){
		for( int i = 0 ; i < nrow ; i++ ){
			Tr workingRow = new Tr();
			for( int j = 0 ; j < ncol ; j++ ){
				FlexCell fc = new FlexCell(cellTextValue[i][j], cellPropertiesIndex[i][j] );
				
				if( rowSpanInstructions.containsKey( j ) ){
					fc.setRowspan(rowSpanInstructions.get( j )[i]);
				}
				if( colSpanInstructions.containsKey( i ) ){
					fc.setColspan(colSpanInstructions.get( i )[j]);
				}
				if( !colSpanInstructions.containsKey( i ) )
					workingRow.getContent().add(fc.getTc());
				else if( colSpanInstructions.containsKey( i ) && colSpanInstructions.get( i )[j] > 0)
					workingRow.getContent().add(fc.getTc()); 
			}
			reviewtable.getContent().add(workingRow);
		} 
	}
	
	private void BodyPPTX( CTTable reviewtable ) throws Exception{
		for( int i = 0 ; i < nrow ; i++ ){
			CTTableRow workingRow = new CTTableRow();
			for( int j = 0 ; j < ncol ; j++ ){
				FlexCell fc = new FlexCell(cellTextValue[i][j], cellPropertiesIndex[i][j] );
				
				if( rowSpanInstructions.containsKey( j ) ){
					fc.setRowspan(rowSpanInstructions.get( j )[i]);
				}
				if( colSpanInstructions.containsKey( i ) ){
					fc.setColspan(colSpanInstructions.get( i )[j]);
				}

				workingRow.getTc().add(fc.getCTTableCell());
			}
			reviewtable.getTr().add(workingRow);
		} 
	}
	
	private String BodyHTML( ) {
		String out = "";
		out += "<tbody>";
		for( int i = 0 ; i < nrow ; i++ ){
			out += "<tr>";
			for( int j = 0 ; j < ncol ; j++ ){
				FlexCell fc = new FlexCell(cellTextValue[i][j], cellPropertiesIndex[i][j] );
				
				if( rowSpanInstructions.containsKey( j ) ){
					fc.setRowspan(rowSpanInstructions.get( j )[i]);
				}
				if( colSpanInstructions.containsKey( i ) ){
					fc.setColspan(colSpanInstructions.get( i )[j]);
				}
				out +=  fc.getHTML();
			}
			out += "</tr>";
		} 
		out += "</tbody>";
		return out;
	}
	
	private CTTableGrid getCTTableGrid( long width){
		CTTableGrid tg = new CTTableGrid();
		for(int i = 0 ; i < ncol ; i++ ){
			List<CTTableCol> gc = tg.getGridCol();
			CTTableCol tc = new CTTableCol();
			if( widths[i] < 0 ) tc.setW(width);
			else tc.setW((long) (widths[i] * 914400) );
			gc.add( tc );
		}
		return tg;
	}
	
	private CTTblLayoutType getCTTblLayoutType() {
		CTTblLayoutType layoutType = new CTTblLayoutType();
		if( widths[0] > 0 ){
			layoutType.setType(STTblLayoutType.FIXED);
		} else {
			layoutType.setType(STTblLayoutType.AUTOFIT);
		}
		return layoutType;
	}
	private TblGrid getTblGrid() {
		TblGrid tblGrid = new TblGrid();
		if( widths[0] > 0 ){			
			for (int i = 0 ; i < ncol; i++) {
				TblGridCol gridCol = Context.getWmlObjectFactory().createTblGridCol();
				gridCol.setW(BigInteger.valueOf( (long)(widths[i] * 1440 ) ));
				tblGrid.getGridCol().add(gridCol);
			}
		}
		return tblGrid;
	}
	
	public CTTable getPptxTbl(long width) throws Exception {
		CTTable newTable = new CTTable();
		CTTableProperties tablpro = new CTTableProperties();
		newTable.setTblPr(tablpro);
		newTable.setTblGrid(getCTTableGrid(width));
		
		if( nrow > 0 ){
			HeaderPPTX(newTable);
			BodyPPTX(newTable);
			FooterPPTX(newTable);
		}
		return newTable;
	} 
	
	public CTGraphicalObjectFrame getShape(long idx, long shape_id, long width) throws Exception{
		// instatiation the factory for later object creation.
		org.docx4j.dml.ObjectFactory dmlFactory = new org.docx4j.dml.ObjectFactory();
		org.pptx4j.pml.ObjectFactory pmlFactory = new org.pptx4j.pml.ObjectFactory();
		CTGraphicalObjectFrame graphicFrame = pmlFactory.createCTGraphicalObjectFrame();
		
		org.pptx4j.pml.CTGraphicalObjectFrameNonVisual nvGraphicFramePr = pmlFactory.createCTGraphicalObjectFrameNonVisual();

		org.docx4j.dml.CTNonVisualDrawingProps cNvPr = dmlFactory.createCTNonVisualDrawingProps();

		org.docx4j.dml.CTNonVisualGraphicFrameProperties cNvGraphicFramePr = dmlFactory.createCTNonVisualGraphicFrameProperties();
		org.docx4j.dml.CTGraphicalObjectFrameLocking graphicFrameLocks = new org.docx4j.dml.CTGraphicalObjectFrameLocking();
		
		org.docx4j.dml.CTTransform2D xfrm = dmlFactory.createCTTransform2D();
		Graphic graphic = dmlFactory.createGraphic();
		GraphicData graphicData = dmlFactory.createGraphicData();
		
		
		graphicFrame.setNvGraphicFramePr(nvGraphicFramePr);
		nvGraphicFramePr.setCNvPr(cNvPr);
		cNvPr.setName("nvGraphicFrame " + shape_id);
		cNvPr.setId(shape_id);
		
		nvGraphicFramePr.setCNvGraphicFramePr(cNvGraphicFramePr);
		cNvGraphicFramePr.setGraphicFrameLocks(graphicFrameLocks);
		graphicFrameLocks.setNoGrp(true);
		nvGraphicFramePr.setNvPr(pmlFactory.createNvPr());
		
		graphicFrame.setXfrm(xfrm);
		
		graphicFrame.setGraphic(graphic);
		
		graphic.setGraphicData(graphicData);
		graphicData.setUri("http://schemas.openxmlformats.org/drawingml/2006/table");
		
		CTTable ctTable = getPptxTbl( new Double(width/ncol).longValue());
		JAXBElement<CTTable> tbl = dmlFactory.createTbl(ctTable);
		graphicData.getAny().add(tbl);

		return graphicFrame;
		}
	

	public Tbl getDocxTbl(){
		Tbl newTable = new Tbl();
		HeaderDOCX(newTable);
		BodyDOCX(newTable);
		FooterDOCX(newTable);
		TblPr tblpr = new TblPr();
		Jc alignment = new Jc();
		alignment.setVal(JcEnumeration.CENTER);
		tblpr.setJc(alignment);
		tblpr.setTblLayout(getCTTblLayoutType());
		newTable.setTblGrid(getTblGrid());
		newTable.setTblPr(tblpr);
		return newTable;
	}
	
	@Override
	public String getHTML() {
		String out = "<table>";
		if( widths[0] > 0 ){
			for( int j = 0 ; j < ncol ; j++ ){
				out += "<col width=\"" + (int)(72.2*widths[j]) + "\">";
			}
		}
		out += HeaderHTML( );
		out += BodyHTML( );
		out += FooterHTML( );
		out += "</table>";
		return out;
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
