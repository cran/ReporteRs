/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.rdata;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.dml.CTRegularTextRun;
import org.docx4j.dml.CTTableCellProperties;
import org.docx4j.dml.CTTextBody;
import org.docx4j.dml.CTTextBodyProperties;
import org.docx4j.dml.CTTextCharacterProperties;
import org.docx4j.dml.CTTextListStyle;
import org.docx4j.dml.CTTextParagraph;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Text;
import org.lysis.reporters.pptx4r.tools.Format;
import org.lysis.reporters.tables.TableLayoutDOCX;


public class RDate implements RAtomicInterface{
	Vector<String> data;
	
	public RDate(Vector<String> data){
		this.data = data;
	}
	
	@Override
	public void print() {
		for( int i = 0 ; i < size() ; i ++ ){
			System.out.println(data.get(i));
		}
	}

	@Override
	public int size() {
		return data.size();
	}


	@Override
	public Object get(int i) {
		return data.get(i);
	}


	public P getP(int i, TableLayoutDOCX tf) {
		P p = new P();
		R run = new R();
		Text text = new Text();
		PPr parProperties = tf.getDatePar();
		RPr textProperties = tf.getDateText();

		text.setValue(data.get(i));
		run.getContent().add(text);
		run.setRPr(textProperties);
		p.getContent().add(run);
		p.setPPr(parProperties);
		return p;
	}
	public P getP(int i, TableLayoutDOCX tf, String fontColor) {
		P p = new P();
		R run = new R();
		Text text = new Text();
		PPr parProperties = tf.getDatePar();
		RPr textProperties = XmlUtils.deepCopy(tf.getDateText());
		org.docx4j.wml.Color col = new org.docx4j.wml.Color();
		col.setVal(fontColor);
		textProperties.setColor(col);

		
		text.setValue(data.get(i));
		run.getContent().add(text);
		run.setRPr(textProperties);
		p.getContent().add(run);
		p.setPPr(parProperties);
		return p;
	}

	@Override
	public TcPr getCellProperties(TableLayoutDOCX tf) {
		return tf.getDateCell();
	}

	@Override
	public CTTextBody getP(int i, org.lysis.reporters.tables.TableLayoutPPTX tf) throws JAXBException {
		CTTextBody p = new CTTextBody();
		CTTextBodyProperties pp = new CTTextBodyProperties();
		CTTextListStyle ls = new CTTextListStyle();
		p.setBodyPr(pp);
		p.setLstStyle(ls);
		
		CTRegularTextRun textRun = org.lysis.reporters.pptx4r.elements.Utils.getRun(data.get(i));
		textRun.setRPr(tf.getDateText());
		
		CTTextParagraph textPar = new CTTextParagraph();
		List<Object> runs = textPar.getEGTextRun();
		runs.add(textRun);
		
		textPar.setPPr(tf.getDatePar());
		p.getP().add(textPar);
		return p;		
	}

	@Override
	public CTTextBody getP(int i, org.lysis.reporters.tables.TableLayoutPPTX tf,
			String fontColor) throws Exception {
		CTTextBody p = new CTTextBody();
		CTTextBodyProperties pp = new CTTextBodyProperties();
		CTTextListStyle ls = new CTTextListStyle();
		p.setBodyPr(pp);
		p.setLstStyle(ls);
		
		CTRegularTextRun textRun = org.lysis.reporters.pptx4r.elements.Utils.getRun(data.get(i));
		CTTextCharacterProperties chartext = XmlUtils.deepCopy(tf.getDateText());
		chartext.setSolidFill(Format.getCol(fontColor));
		textRun.setRPr(chartext);
		
		CTTextParagraph textPar = new CTTextParagraph();
		List<Object> runs = textPar.getEGTextRun();
		runs.add(textRun);
		
		textPar.setPPr(tf.getDatePar());
		p.getP().add(textPar);
		return p;
	}

	@Override
	public CTTableCellProperties getCellProperties(org.lysis.reporters.tables.TableLayoutPPTX tf) {
		return tf.getDateCell();
	}


	@Override
	public LinkedHashMap<String, String> getCellProperties(
			org.lysis.reporters.tables.TableLayoutHTML tf) {
		return tf.getDateCell();
	}

	@Override
	public String getTdCssClass() {
		return "DateCell";
	}
	
	@Override
	public String getHTML(int i, org.lysis.reporters.tables.TableLayoutHTML tf) {
		String out = "";
		out+= "<p class=\"DatePar\"><span class=\"DateText\">";
		out += org.apache.commons.lang.StringEscapeUtils.escapeHtml(data.get(i));
		out+= "</span></p>";
		return out;
	}

	@Override
	public String getHTML(int i, String fontColor, org.lysis.reporters.tables.TableLayoutHTML tf) {
		String out = "";
		out+= "<p class=\"DatePar\"><span class=\"DateText\" style=\"color:" + fontColor + ";\">";
		out += org.apache.commons.lang.StringEscapeUtils.escapeHtml(data.get(i));
		out+= "</span></p>";
		return out;
	}
}
