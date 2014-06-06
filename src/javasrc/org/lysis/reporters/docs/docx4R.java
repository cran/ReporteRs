/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.docs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.docx4j.docProps.core.CoreProperties;
import org.docx4j.docProps.core.dc.elements.SimpleLiteral;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.DocPropsCorePart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Styles;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblPr;
import org.lysis.reporters.docx4r.elements.DMLGraphics;
import org.lysis.reporters.docx4r.elements.DataTable;
import org.lysis.reporters.docx4r.elements.Image;
import org.lysis.reporters.docx4r.elements.POT;
import org.lysis.reporters.docx4r.elements.PageBreak;
import org.lysis.reporters.docx4r.elements.TableOfContent;
import org.lysis.reporters.docx4r.tools.BookmarkObject;
import org.lysis.reporters.docx4r.tools.DocExplorer;
import org.lysis.reporters.docx4r.tools.Format;
import org.lysis.reporters.tables.FlexTable;
import org.lysis.reporters.texts.ParProperties;


public class docx4R {
	private WordprocessingMLPackage basedoc;

	private int eltIndex;
	private LinkedHashMap<String, String> styleDefinitions;
	
	public docx4R ( ) {
		styleDefinitions = new LinkedHashMap<String, String>();
		eltIndex=0;
	}
	
	private void listStyleNames(){
		Styles styles = basedoc.getMainDocumentPart().getStyleDefinitionsPart().getJaxbElement();      
		for ( org.docx4j.wml.Style s : styles.getStyle() ) {
			
			if( s.getType().equals("paragraph") ){
				styleDefinitions.put( s.getStyleId(), s.getName().getVal() );
			}
		}
	}

	public String[] getStyleNames(){
		listStyleNames();
		String[] stylenames = new String[styleDefinitions.size()*2];
		int i = 0 ;
		for (Iterator<String> it1 = styleDefinitions.keySet().iterator(); it1.hasNext();) {
			stylenames[i] = it1.next();
			i++;
		}
		for (Map.Entry<String, String> entry : styleDefinitions.entrySet()) {
			stylenames[i] = entry.getValue();
			i++;
		}
		return stylenames;
	}

	private boolean existsStyleNames(String stylename){

		for (Iterator<String> it1 = styleDefinitions.keySet().iterator(); it1.hasNext();) {
			if( it1.next().equals(stylename)) return true;
		}
		return false;
	}

	public int[] getSectionDimensions(){
		int[] out = new int[6];
		List<SectionWrapper> sections = basedoc.getDocumentModel().getSections();
		SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
		out[0] = -1;out[1] = -1;out[2] = -1;out[3] = -1;out[4] = -1;out[5] = -1;

		if (sectPr != null ){
			out[0] = sectPr.getPgSz().getW().intValue();
			out[1] = sectPr.getPgSz().getH().intValue();
			out[2] = sectPr.getPgMar().getTop().intValue();
			out[3] = sectPr.getPgMar().getRight().intValue();
			out[4] = sectPr.getPgMar().getBottom().intValue();
			out[5] = sectPr.getPgMar().getLeft().intValue();
		}
		
		return out;

	}
	public void setBaseDocument(String baseDocFileName) throws Exception{
		try {
			basedoc = WordprocessingMLPackage.load(new FileInputStream(new File(baseDocFileName)));
		} catch (FileNotFoundException e) {
			throw new Exception ("Cannot load base document [" +  baseDocFileName + "]. File is not found.");
		} catch (Docx4JException e) {
			throw new Exception ("Cannot load base document [" +  baseDocFileName + "]. File is found but a Docx4J exception occured.");
		}
		listStyleNames();
	}
	
	public WordprocessingMLPackage getBaseDocument() {
		return basedoc;
	}
	
	public void incrementElementIndex( int inc) {
		eltIndex = eltIndex + inc;
	}
	
	public int getElementIndex( ) {
		return eltIndex;
	}
	
	public void writeDocxToStream(String target) throws Exception{
		File f = new File(target);
		try {
			basedoc.save(f);
		} catch (Docx4JException e) {
			throw new Exception ("Cannot save document [" +  target + "]. A Docx4J exception occured.");
		}
	}

	public void setDocPropertyTitle(String value){
		DocPropsCorePart docProps = basedoc.getDocPropsCorePart();
		CoreProperties cp = docProps.getJaxbElement();
		org.docx4j.docProps.core.dc.elements.ObjectFactory dcElfactory = new org.docx4j.docProps.core.dc.elements.ObjectFactory();
		SimpleLiteral literal = dcElfactory.createSimpleLiteral();
		literal.getContent().add( value );
		cp.setTitle(dcElfactory.createTitle(literal));
	}
	
	public void setDocPropertyCreator(String value){
		DocPropsCorePart docProps = basedoc.getDocPropsCorePart();
		CoreProperties cp = docProps.getJaxbElement();
		org.docx4j.docProps.core.dc.elements.ObjectFactory dcElfactory = new org.docx4j.docProps.core.dc.elements.ObjectFactory();
		SimpleLiteral literal = dcElfactory.createSimpleLiteral();
		literal.getContent().add(value);
		cp.setCreator(literal);//(dcElfactory.createCreator(literal));
	}

	public void addPageBreak( ) throws Exception{
		eltIndex++;
		basedoc.getMainDocumentPart().addObject( PageBreak.getBreak() );
	}
	public void add( Object obj ) throws Exception{
		eltIndex++;
		basedoc.getMainDocumentPart().addObject(obj);
	}
	public void addTableOfContents( ) throws Exception{
		eltIndex++;
		TableOfContent.addTableOfContents(basedoc.getMainDocumentPart());
	}

	public void addTableOfContents( String stylename ) throws Exception{
		eltIndex++;
		if ( !existsStyleNames (stylename ) ) throw new Exception(stylename + " does not exist.");
		TableOfContent.addTableOfContents(basedoc.getMainDocumentPart(), stylename);
	}

	
	public void deleteBookmark( String bookmark ){
		BookmarkObject bo = DocExplorer.getBookmarkObject(bookmark, basedoc);

		if( bo.exists() ){
			P p = bo.getP();
			((ContentAccessor)p.getParent()).getContent().remove(p);
		}
	}
	
	public void deleteBookmarkNextContent( String bookmark ){
		BookmarkObject bo = DocExplorer.getBookmarkObject(bookmark, basedoc);

		if( bo.exists() ){
			P p = bo.getP();
		    int i = ((ContentAccessor)p.getParent()).getContent().indexOf(p);
		    if( ((ContentAccessor)p.getParent()).getContent().size() > (i+1) )
		    	((ContentAccessor)p.getParent()).getContent().remove(i+1);
		}
	}
	
	public void add(DataTable dt, ParProperties pp) throws Exception{
		eltIndex++;
		String textalign = pp.getTextalign();
		
		Tbl tbl = dt.getTbl();
		TblPr tblpr = tbl.getTblPr();
		
		Jc alignment = new Jc();
		if( textalign.equals("left")) alignment.setVal(JcEnumeration.LEFT);
		else if( textalign.equals("center")) alignment.setVal(JcEnumeration.CENTER);
		else if( textalign.equals("right")) alignment.setVal(JcEnumeration.RIGHT);
		else if( textalign.equals("justify")) alignment.setVal(JcEnumeration.BOTH);
		
		tblpr.setJc(alignment);
		basedoc.getMainDocumentPart().addObject(tbl);
	}
	
	public void insert(String bookmark, DataTable table, ParProperties pp) throws Exception {
		eltIndex++;
		BookmarkObject bo = DocExplorer.getBookmarkObject(bookmark, basedoc);

		String textalign = pp.getTextalign();
		
		Tbl tbl = table.getTbl();
		TblPr tblpr = tbl.getTblPr();
		
		Jc alignment = new Jc();
		if( textalign.equals("left")) alignment.setVal(JcEnumeration.LEFT);
		else if( textalign.equals("center")) alignment.setVal(JcEnumeration.CENTER);
		else if( textalign.equals("right")) alignment.setVal(JcEnumeration.RIGHT);
		else if( textalign.equals("justify")) alignment.setVal(JcEnumeration.BOTH);
		
		tblpr.setJc(alignment);

		if( bo.exists() ){
			P p = bo.getP();
		    int i = ((ContentAccessor)p.getParent()).getContent().indexOf(p);
		    ((ContentAccessor)p.getParent()).getContent().add(i+1, tbl);
		} else throw new Exception("can't find bookmark '" + bookmark + "'." );
	}

	public void add( FlexTable  dt, ParProperties pp) throws Exception{
		eltIndex++;
		String textalign = pp.getTextalign();
		
		Tbl tbl = dt.getDocxTbl();
		TblPr tblpr = tbl.getTblPr();
		
		Jc alignment = new Jc();
		if( textalign.equals("left")) alignment.setVal(JcEnumeration.LEFT);
		else if( textalign.equals("center")) alignment.setVal(JcEnumeration.CENTER);
		else if( textalign.equals("right")) alignment.setVal(JcEnumeration.RIGHT);
		else if( textalign.equals("justify")) alignment.setVal(JcEnumeration.BOTH);
		
		tblpr.setJc(alignment);
		basedoc.getMainDocumentPart().addObject(tbl);
	}
	
	public void add( String bookmark, FlexTable  dt, ParProperties pp) throws Exception{
		
		eltIndex++;
		BookmarkObject bo = DocExplorer.getBookmarkObject(bookmark, basedoc);

		String textalign = pp.getTextalign();
		
		Tbl tbl = dt.getDocxTbl();
		TblPr tblpr = tbl.getTblPr();
		
		Jc alignment = new Jc();
		if( textalign.equals("left")) alignment.setVal(JcEnumeration.LEFT);
		else if( textalign.equals("center")) alignment.setVal(JcEnumeration.CENTER);
		else if( textalign.equals("right")) alignment.setVal(JcEnumeration.RIGHT);
		else if( textalign.equals("justify")) alignment.setVal(JcEnumeration.BOTH);
		
		tblpr.setJc(alignment);

		if( bo.exists() ){
			P p = bo.getP();
		    int i = ((ContentAccessor)p.getParent()).getContent().indexOf(p);
		    ((ContentAccessor)p.getParent()).getContent().add(i+1, tbl);
		} else throw new Exception("can't find bookmark '" + bookmark + "'." );

	}
	
	public void add( POT pot) {
		eltIndex++;
		for( int i = 0 ; i < pot.getLength() ; i++ )
			basedoc.getMainDocumentPart().addObject(pot.getP(i));
	}
	
	public void insert( String bookmark, POT pot) throws Exception{
		eltIndex++;
		BookmarkObject bo = DocExplorer.getBookmarkObject(bookmark, basedoc);
		
		if( bo.exists() ){
			P p = bo.getP();
			
		    int i = ((ContentAccessor)p.getParent()).getContent().indexOf(p);
		    ((ContentAccessor)p.getParent()).getContent().remove(i);
		    pot.setBookmark(bookmark, bo.getBookmarkID());
		    for( int pid = 0 ; pid < pot.getLength() ; pid++ ) 
		    	((ContentAccessor)p.getParent()).getContent().add(i+pid, pot.getP(pid));
		} else throw new Exception("can't find bookmark '" +bookmark+"'." );
	}
	


	
	public void addImage ( String[] filename, int[] dims, String textalign, int paddingbottom
			, int paddingtop, int paddingleft, int paddingright ) throws Exception {
		int width = dims[0];
		int height = dims[1];
		PPr ppr = Format.getParProperties(textalign, paddingbottom, paddingtop, paddingleft, paddingright);

	    for( int f = 0 ; f < filename.length ; f++ ){
	    	Image img = new Image(filename[f]);
	    	basedoc.getMainDocumentPart().addObject(img.addImageToPackage(basedoc, eltIndex+1, eltIndex + 2, width, height, ppr));
	    	eltIndex = eltIndex + 2;
	    }		
	}

	public void insertImage ( String bookmark, String[] filename, int[] dims, String textalign, int paddingbottom
			, int paddingtop, int paddingleft, int paddingright ) throws Exception {
		BookmarkObject bo = DocExplorer.getBookmarkObject(bookmark, basedoc);

		int width = dims[0];
		int height = dims[1];
		PPr ppr = Format.getParProperties(textalign, paddingbottom, paddingtop, paddingleft, paddingright);

		if( bo.exists() ){
			P p = bo.getP();
		    int i = ((ContentAccessor)p.getParent()).getContent().indexOf(p);
		    ((ContentAccessor)p.getParent()).getContent().remove(i);
		    for( int f = 0 ; f < filename.length ; f++ ){
		    	Image img = new Image(filename[f]);
		    	if( f == 0 ) img.setBookmark(bookmark, bo.getBookmarkID());
		    	((ContentAccessor)p.getParent()).getContent().add(i+f, img.addImageToPackage(basedoc, eltIndex+1, eltIndex + 2, width, height, ppr));
		    	eltIndex = eltIndex + 2;
		    }
		} else throw new Exception("can't find bookmark '" +bookmark+"'." );
	}

	
	public void addDML ( String[] filename, int[] dims, String textalign, int paddingbottom
			, int paddingtop, int paddingleft, int paddingright ) throws Exception {
		int width = dims[0];
		int height = dims[1];
		PPr ppr = Format.getParProperties(textalign, paddingbottom, paddingtop, paddingleft, paddingright);
	    for( int f = 0 ; f < filename.length ; f++ ){
			DMLGraphics dml = new DMLGraphics(filename[f]);
			P altp = dml.getP(width, height, eltIndex, ppr);			
			this.add(altp);
	    	eltIndex = eltIndex + 2;
	    }		
	}

	public void insertDML ( String bookmark, String[] filename, int[] dims, String textalign, int paddingbottom
			, int paddingtop, int paddingleft, int paddingright ) throws Exception {
		BookmarkObject bo = DocExplorer.getBookmarkObject(bookmark, basedoc);

		PPr ppr = Format.getParProperties(textalign, paddingbottom, paddingtop, paddingleft, paddingright);
		if( bo.exists() ){
			P p = bo.getP();
			int width = dims[0];
			int height = dims[1];
		    int i = ((ContentAccessor)p.getParent()).getContent().indexOf(p);
		    ((ContentAccessor)p.getParent()).getContent().remove(i);

		    for( int f = 0 ; f < filename.length ; f++ ){
				DMLGraphics dml = new DMLGraphics(filename[f]);
				if( f == 0 ) dml.setBookmark(bookmark, bo.getBookmarkID());
				
		    	((ContentAccessor)p.getParent()).getContent().add(i+f, dml.getP(width, height, eltIndex, ppr));
		    	eltIndex = eltIndex + 2;
		    }
		} else throw new Exception("can't find bookmark '" +bookmark+"'." );
	}

}

