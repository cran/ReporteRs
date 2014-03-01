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


public class RInteger implements RAtomicInterface{
	Vector<Integer> data;
	
	public RInteger(Vector<Integer> data){
		this.data = data;
	}
	private String format(TableLayoutBase tf, double value){
		NumberFormat nf;
		Locale locale =  new Locale(tf.getLocale_language(),tf.getLocale_region());
		nf = NumberFormat.getIntegerInstance(locale);
		nf.setGroupingUsed(false);
		return nf.format(value);
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
		
		PPr parProperties = tf.getIntegerPar();
		RPr textProperties = tf.getIntegerText();
		
		Long temp = new Long( data.get(i).longValue() );
		BigInteger bi = new BigInteger("-2147483648");
		int test = bi.compareTo(new BigInteger(temp.toString())) ;

		if( test==0 ) text.setValue("NA");
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
		
		PPr parProperties = tf.getIntegerPar();
		RPr textProperties = XmlUtils.deepCopy(tf.getIntegerText());

		org.docx4j.wml.Color col = new org.docx4j.wml.Color();
		col.setVal(fontColor);
		textProperties.setColor(col);

		Long temp = new Long( data.get(i).longValue() );
		BigInteger bi = new BigInteger("-2147483648");
		if( bi.compareTo(new BigInteger(temp.toString())) == 0 ) text.setValue("NA");
		else text.setValue(format(tf,data.get(i)));
			
		run.getContent().add(text);
		run.setRPr(textProperties);
		p.getContent().add(run);
		p.setPPr(parProperties);
		return p;
	}
	public TcPr getCellProperties(TableLayoutDOCX tf) {
		return tf.getIntegerCell();
	}
	@Override
	public CTTableCellProperties getCellProperties(
			org.lysis.reporters.tables.TableLayoutPPTX tf) {
		return tf.getIntegerCell();
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
		
		textRun.setRPr(tf.getIntegerText());
		
		CTTextParagraph textPar = new CTTextParagraph();
		List<Object> runs = textPar.getEGTextRun();
		runs.add(textRun);
		
		textPar.setPPr(tf.getIntegerPar());
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

		CTTextCharacterProperties chartext = XmlUtils.deepCopy(tf.getIntegerText());
		chartext.setSolidFill(Format.getCol(fontColor));
		textRun.setRPr(chartext);
		
		CTTextParagraph textPar = new CTTextParagraph();
		List<Object> runs = textPar.getEGTextRun();
		runs.add(textRun);
		
		textPar.setPPr(tf.getIntegerPar());
		p.getP().add(textPar);
		return p;		

	}


	@Override
	public LinkedHashMap<String, String> getCellProperties(
			org.lysis.reporters.tables.TableLayoutHTML tf) {
		return tf.getIntegerCell();
	}

	@Override
	public String getTdCssClass() {
		return "IntegerCell";

	}
	@Override
	public String getHTML(int i, org.lysis.reporters.tables.TableLayoutHTML tf) {
		Long temp = new Long( data.get(i).longValue() );
		BigInteger bi = new BigInteger("-2147483648");
		
		String out = "";
		out += "<p class=\"IntegerPar\"><span class=\"IntegerText\">";
		if( bi.compareTo(new BigInteger(temp.toString())) == 0 ) 
			out += "NA";
		else out += format(tf,data.get(i));
		out += "</span></p>";
		return out;
	}

	@Override
	public String getHTML(int i, String fontColor, org.lysis.reporters.tables.TableLayoutHTML tf) {
		Long temp = new Long( data.get(i).longValue() );
		BigInteger bi = new BigInteger("-2147483648");
		
		String out = "";
		out+= "<p class=\"IntegerPar\"><span class=\"IntegerText\" style=\"color:" + fontColor + ";\">";
		if( bi.compareTo(new BigInteger(temp.toString())) == 0 ) 
			out += "NA";
		else out += format(tf,data.get(i));
		out+= "</span></p>";
		return out;
	}	


}

