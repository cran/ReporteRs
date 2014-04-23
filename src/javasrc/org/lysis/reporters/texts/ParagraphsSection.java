/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.texts;

import java.util.LinkedHashMap;
import java.util.Vector;

import org.docx4j.dml.CTTextBody;
import org.docx4j.dml.CTTextBodyProperties;
import org.docx4j.dml.CTTextListStyle;
import org.docx4j.dml.CTTextParagraph;
import org.docx4j.dml.CTTextParagraphProperties;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.lysis.reporters.html4r.elements.HTML4R;
import org.lysis.reporters.html4r.tools.Format;

public class ParagraphsSection implements HTML4R{
	private LinkedHashMap<Integer, Paragraph> parList;
	private int parIndex;
	private ParProperties parProp;
	
	public ParagraphsSection( ParProperties parProp ){
		parIndex = 0;
		parList = new LinkedHashMap<Integer, Paragraph>();
		setParProperties( parProp );
	}
	
	public void addParagraph ( Paragraph par ){
		parIndex++;
		parList.put(parIndex, par);
	}
	public int size(){
		return parIndex ;
	}
	
	public Paragraph get(int i){
		return parList.get(i);
	}
	public Paragraph getLast(){
		return parList.get(parIndex);
	}
	public void setParProperties( ParProperties parProp ){
		this.parProp = parProp;
	}
	
	public void setTextProperties(TextProperties tp){
		if( parIndex > 0 )
			for(int i = 1 ; i < parIndex +1 ; i++ ){
				parList.get(i).setTextProperties(tp);
			}
	}
	
	public String toString(){
		String out = "";
		if( parIndex > 0 )
			for(int i = 1 ; i < parIndex +1 ; i++ ){
				out += parList.get(i).toString() ;
			}
		return out;
	}


	@Override
	public String getHTML() {
		LinkedHashMap<String, String> ppr = Format.getParProperties(parProp.getTextalign()
				, parProp.getPaddingbottom()
				, parProp.getPaddingtop(), parProp.getPaddingleft()
				, parProp.getPaddingright());
		
		String out = "<div>";
		String styleStr = Format.getJSString(ppr);
		if( parIndex > 0 ){
			for(int i = 1 ; i < parIndex +1 ; i++ ){
				out += "<p style=\"" + styleStr + "\">" + parList.get(i).getHTML() + "</p>";
			}
		}
		
		out += "</div>";
		
		return out;
	}

	public CTTextBody getCTTextBody() throws Exception{
		CTTextParagraphProperties rpr = org.lysis.reporters.pptx4r.tools.Format.getParProperties(parProp.getTextalign()
				, parProp.getPaddingbottom()
				, parProp.getPaddingtop(), parProp.getPaddingleft()
				, parProp.getPaddingright());
		Vector<CTTextParagraph> out = new Vector<CTTextParagraph>();
		if( parIndex > 0 ){
			for(int i = 1 ; i < parIndex + 1 ; i++ ){
				CTTextParagraph temp = parList.get(i).getCTTextParagraph();
				temp.setPPr(rpr);
				out.add( temp );
			}
		}
		CTTextBody tb = new CTTextBody();
		
		CTTextBodyProperties pp = new CTTextBodyProperties();
		CTTextListStyle ls = new CTTextListStyle();
		tb.setBodyPr(pp);
		tb.setLstStyle(ls);
		
		tb.getP().addAll(out);
		return tb;
	}
	
	public Vector<P> getP(){
		
		PPr rpr = org.lysis.reporters.docx4r.tools.Format.getParProperties(parProp.getTextalign()
				, parProp.getPaddingbottom()
				, parProp.getPaddingtop(), parProp.getPaddingleft()
				, parProp.getPaddingright());
		
		Vector<P> out = new Vector<P>();
		
		if( parIndex > 0 ){
			for(int i = 1 ; i < parIndex +1 ; i++ ){
				P temp = parList.get(i).getP();
				temp.setPPr(rpr);
				out.add( temp );
			}
		}
		
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
