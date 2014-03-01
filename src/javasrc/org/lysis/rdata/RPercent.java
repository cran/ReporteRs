/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.rdata;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

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
import org.lysis.reporters.tables.TableLayoutBase;
import org.lysis.reporters.tables.TableLayoutDOCX;


public class RPercent implements RAtomicInterface{
	Vector<Double> data;
	
	public RPercent(Vector<Double> data){
		this.data = data;
	}
	private String format(TableLayoutBase tf, double value){
		NumberFormat nf;
		Locale locale =  new Locale(tf.getLocale_language(),tf.getLocale_region());
		nf = NumberFormat.getNumberInstance(locale);
		nf.setMaximumFractionDigits(new Integer(tf.getFractionPercentDigit()));
		nf.setMinimumFractionDigits(new Integer(tf.getFractionPercentDigit()));
		return nf.format(value) + tf.getPercentAddsymbol();
	}
	@Override
	public void print() {
		for( int i = 0 ; i < size() ; i ++ ){
			System.out.println(data.get(i).toString());
		}
	}

	@Override
	public int size() {
		return data.size();
	}


	
	public Object get(int i) {
		return data.get(i);
	}


	@Override
	public P getP(int i, TableLayoutDOCX tf) {
		P p = new P();
		R run = new R();
		Text text = new Text();
		PPr parProperties = tf.getPercentPar();
		RPr textProperties = tf.getPercentText();
	
		if( ((Double)(data.get(i))).isNaN() ) text.setValue("NA");
		else text.setValue(format(tf,data.get(i)));
		
		run.getContent().add(text);
		run.setRPr(textProperties);
		p.getContent().add(run);
		p.setPPr(parProperties);
		return p;
	}

	@Override
	public P getP(int i, TableLayoutDOCX tf, String fontColor) {
		P p = new P();
		R run = new R();
		Text text = new Text();
		PPr parProperties = tf.getPercentPar();
		RPr textProperties = XmlUtils.deepCopy(tf.getPercentText());
	
		org.docx4j.wml.Color col = new org.docx4j.wml.Color();
		col.setVal(fontColor);
		textProperties.setColor(col);
		
		if( ((Double)(data.get(i))).isNaN() ) text.setValue("NA");
		else text.setValue(format(tf,data.get(i)));
		
		run.getContent().add(text);
		run.setRPr(textProperties);
		p.getContent().add(run);
		p.setPPr(parProperties);
		return p;
	}
	public TcPr getCellProperties(TableLayoutDOCX tf) {
		return tf.getPercentCell();
	}
	@Override
	public CTTableCellProperties getCellProperties(
			org.lysis.reporters.tables.TableLayoutPPTX tf) {
		return tf.getPercentCell();
	}
	@Override
	public CTTextBody getP(int i, org.lysis.reporters.tables.TableLayoutPPTX tf)
			throws Exception {
		CTTextBody p = new CTTextBody();
		CTTextBodyProperties pp = new CTTextBodyProperties();
		CTTextListStyle ls = new CTTextListStyle();
		p.setBodyPr(pp);
		p.setLstStyle(ls);
		
		CTRegularTextRun textRun;
		
		Long temp = new Long( data.get(i).longValue() );
		BigInteger bi = new BigInteger("-2147483648");
		if( bi.compareTo(new BigInteger(temp.toString())) == 0 ) textRun = org.lysis.reporters.pptx4r.elements.Utils.getRun("NA");
		else textRun = org.lysis.reporters.pptx4r.elements.Utils.getRun(format(tf,data.get(i)));//

		
		textRun.setRPr(tf.getPercentText());
		
		CTTextParagraph textPar = new CTTextParagraph();
		List<Object> runs = textPar.getEGTextRun();
		runs.add(textRun);
		
		textPar.setPPr(tf.getPercentPar());
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
		
		CTRegularTextRun textRun;
		
		Long temp = new Long( data.get(i).longValue() );
		BigInteger bi = new BigInteger("-2147483648");
		if( bi.compareTo(new BigInteger(temp.toString())) == 0 ) textRun = org.lysis.reporters.pptx4r.elements.Utils.getRun("NA");
		else textRun = org.lysis.reporters.pptx4r.elements.Utils.getRun(format(tf,data.get(i)));//

		CTTextCharacterProperties chartext = XmlUtils.deepCopy(tf.getPercentText());
		chartext.setSolidFill(Format.getCol(fontColor));
		textRun.setRPr(chartext);
		
		CTTextParagraph textPar = new CTTextParagraph();
		List<Object> runs = textPar.getEGTextRun();
		runs.add(textRun);
		
		textPar.setPPr(tf.getPercentPar());
		p.getP().add(textPar);
		return p;		

	}


	@Override
	public LinkedHashMap<String, String> getCellProperties(
			org.lysis.reporters.tables.TableLayoutHTML tf) {
		return tf.getPercentCell();
	}

	@Override
	public String getTdCssClass() {
		return "PercentCell";
	}
	@Override
	public String getHTML(int i, org.lysis.reporters.tables.TableLayoutHTML tf) {
		String out = "";
		out += "<p class=\"PercentPar\"><span class=\"PercentText\">";
		out += format(tf, data.get(i));
		out += "</span></p>";
		return out;
	}

	@Override
	public String getHTML(int i, String fontColor, org.lysis.reporters.tables.TableLayoutHTML tf) {

		String out = "";
		out += "<p class=\"PercentPar\"><span class=\"PercentText\" style=\"color:" + fontColor + ";\">";
		out += format(tf, data.get(i));
		out += "</span></p>";
		return out;
	}	


}
