/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.docx4r.elements;

import java.math.BigInteger;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBElement;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.CTMarkupRange;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;
import org.lysis.reporters.docx4r.tools.Format;



public class POT {

	private String style;
	private MainDocumentPart mdp;
	private LinkedHashMap<Integer, P> pList;
	private int index;

	private boolean hasBookmark;
	private JAXBElement<CTBookmark> bmStart;
	private JAXBElement<CTMarkupRange> bmEnd;
	
	public POT(WordprocessingMLPackage doc, String stylename){
		style = stylename;
		mdp = doc.getMainDocumentPart();
		pList = new LinkedHashMap<Integer, P>();
		index = -1;
		hasBookmark = false;
	}
	
	public void setBookmark(String bookmark, BigInteger bookmarkID){
		hasBookmark = true;
		
		org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
		CTBookmark bm = new CTBookmark();
		bm.setId(bookmarkID);
		bm.setName(bookmark);		
		bmStart =  factory.createBodyBookmarkStart(bm);

		CTMarkupRange mr = factory.createCTMarkupRange();
		mr.setId(bookmarkID);
		bmEnd = factory.createBodyBookmarkEnd(mr);
	}
	
	public void addP ( ){
		index++;
		P p = mdp.createStyledParagraphOfText(style, "");
		pList.put(index, p);
	}
	
	public void addPot ( String value, int size, boolean bold, boolean italic, boolean underlined, String color, String fontfamily, String valign ){
		P p = pList.get(index);

		R run = new R();
		Text text = new Text();
		text.setValue( value );
		text.setSpace("preserve");
		run.getContent().add(text);
		RPr rpr = Format.getTextProperties(color, size, bold, italic, underlined, fontfamily, valign);
		run.setRPr(rpr);
		p.getContent().add(run);
	}
	
	public void addText ( String value ){
		P p = pList.get(index);
		R run = new R();
		Text text = new Text();
		text.setValue( value );
		text.setSpace("preserve");
		run.getContent().add(text);
		p.getContent().add(run);
	}
	
	public int getLength(){
		return index + 1;
	}
	
	public P getP( int i) {
		P out = pList.get(i);
		if( hasBookmark && i == 0 && getLength() == 1 ){
			out.getContent().add(0, bmStart);
			out.getContent().add(index+1, bmEnd);
		} else if( hasBookmark && i == 0 && getLength() > 1 ){
			out.getContent().add(0, bmStart);
		} else if( hasBookmark && i == index && getLength() > 1 ){
			out.getContent().add(index+1, bmEnd);
		}
		return out;
	}

}
