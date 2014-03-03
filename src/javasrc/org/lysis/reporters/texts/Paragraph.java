/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.texts;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.docx4j.dml.CTRegularTextRun;
import org.docx4j.dml.CTTextCharacterProperties;
import org.docx4j.dml.CTTextParagraph;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;
import org.lysis.reporters.html4r.elements.HTML4R;
import org.lysis.reporters.html4r.tools.Format;
import org.lysis.reporters.html4r.tools.utils;
import org.lysis.reporters.pptx4r.elements.Utils;


public class Paragraph implements HTML4R{
	private LinkedHashMap<Integer, String> textList;
	private LinkedHashMap<Integer, TextProperties> formatList;
	private int index;

	public Paragraph( ) {
		textList = new LinkedHashMap<Integer, String>();
		formatList = new LinkedHashMap<Integer, TextProperties>();
		index = 0;
	}

	public void addText ( String value, TextProperties tp ) throws IOException{
		index++;
		textList.put(index, value );
		formatList.put(index, tp);
	}
	
	public void setTextProperties(TextProperties tp){
		for (Iterator<Integer> it1 = formatList.keySet().iterator(); it1.hasNext();) {
			int key = it1.next();
			formatList.put(key, tp);
		}
	}
	
	public void addText ( String value ) throws IOException{
		index++;
		textList.put(index, value );
		formatList.put(index, null);
	}

	public String toString(){
		String out = "";
		if( index > 0 )
			for( int i = 1 ; i <= index ; i++ ){
				out += textList.get(i);
			}
		return out;
	}

	@Override
	public String getHTML() {
		String out = "";
		if( index > 0 ){
			for( int i = 1 ; i <= index ; i++ ){
				if( formatList.get(i) != null ){
					TextProperties tp = formatList.get(i);
					LinkedHashMap<String, String> rpr = Format.getTextProperties(tp.getColor(), tp.getSize(), tp.isBold(), tp.isItalic(), tp.isUnderlined(), tp.getFontfamily(), tp.getValign());
					out += utils.span_style( textList.get(i), Format.getJSString(rpr) );
				} else out += utils.span( textList.get(i) );
			}
		}
		return out;
	}

	public P getP(){
		P p = new P();
		if( index > 0 ){
			for( int i = 1 ; i <= index ; i++ ){
				if( formatList.get(i) != null ){
					TextProperties tp = formatList.get(i);
					R run = new R();
					Text text = new Text();
					text.setValue( textList.get(i) );
					text.setSpace("preserve");
					run.getContent().add(text);
					RPr rpr = org.lysis.reporters.docx4r.tools.Format.getTextProperties(tp.getColor(), tp.getSize(), tp.isBold(), tp.isItalic(), tp.isUnderlined(), tp.getFontfamily(), tp.getValign());
					run.setRPr(rpr);
					p.getContent().add(run);
				} else {
					R run = new R();
					Text text = new Text();
					text.setValue( textList.get(i) );
					text.setSpace("preserve");
					run.getContent().add(text);
					p.getContent().add(run);
				}
			}
		}
		return p;
	}
	public CTTextParagraph getCTTextParagraph() throws Exception{
		CTTextParagraph textPar = new CTTextParagraph();

		
		if( index > 0 ){
			List<Object> runs = textPar.getEGTextRun();
			for( int i = 1 ; i <= index ; i++ ){
				if( formatList.get(i) != null ){
					TextProperties tp = formatList.get(i);
					CTTextCharacterProperties rpr = org.lysis.reporters.pptx4r.tools.Format.getTextProperties(tp.getColor(), tp.getSize(), tp.isBold(), tp.isItalic(), tp.isUnderlined(), tp.getFontfamily(), tp.getValign());
					CTRegularTextRun textRun = Utils.getRun(textList.get(i));
					textRun.setRPr(rpr);
					runs.add(textRun);
				} else {
					CTRegularTextRun textRun = Utils.getRun(textList.get(i));
					runs.add(textRun);
				}
			}
		}
		return textPar;
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
