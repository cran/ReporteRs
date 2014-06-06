/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.pptx4r.tools;

import java.awt.Color;
import java.math.BigInteger;

import org.docx4j.XmlUtils;
import org.docx4j.dml.CTLineProperties;
import org.docx4j.dml.CTPresetLineDashProperties;
import org.docx4j.dml.CTSRgbColor;
import org.docx4j.dml.CTSolidColorFillProperties;
import org.docx4j.dml.CTTableCellProperties;
import org.docx4j.dml.CTTextCharacterProperties;
import org.docx4j.dml.CTTextParagraphProperties;
import org.docx4j.dml.CTTextSpacing;
import org.docx4j.dml.CTTextSpacingPoint;
import org.docx4j.dml.STPresetLineDashVal;
import org.docx4j.dml.STTextAlignType;
import org.docx4j.dml.STTextAnchoringType;
import org.docx4j.dml.STTextUnderlineType;
import org.docx4j.dml.TextFont;
import org.docx4j.wml.HpsMeasure;
import org.pptx4j.jaxb.Context;

public class Format {
	
	public static CTSolidColorFillProperties getCol( String myColor ) throws Exception{
		System.setProperty("java.awt.headless", "true");
		CTSolidColorFillProperties prop = new CTSolidColorFillProperties();

		CTSRgbColor rgb = new CTSRgbColor();
        
		Color color = null;
		try{
		java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(myColor.toLowerCase()); // toLowerCase because the color fields are RED or red, not Red
		color = (Color)field.get(null);
		} catch( java.lang.NoSuchFieldException e ){
			color = Color.decode(myColor);
		}
		rgb.setVal(new byte[]{(byte)color.getRed(), (byte)color.getGreen(), (byte)color.getBlue()});

		prop.setSrgbClr( rgb );
		return prop;
	}
	


	
	public static CTTextCharacterProperties getTextProperties(String color,int fontsize, boolean strbold, boolean italic, boolean underlined, String fontfamily, String valign) throws Exception{
		CTTextCharacterProperties runProperties = new CTTextCharacterProperties();
		
		HpsMeasure size = new HpsMeasure();
		size.setVal( BigInteger.valueOf( fontsize*2 ) );
		runProperties.setSz(fontsize*100);
		
		TextFont tf = new TextFont();
		tf.setTypeface(fontfamily);
		runProperties.setCs(tf);
		runProperties.setLatin(tf);
		runProperties.setEa(tf);
		
		if( strbold ){
			runProperties.setB(strbold);
		}
		
		if( italic ){
			runProperties.setI( italic );
		}
		if( underlined ){
			runProperties.setU(STTextUnderlineType.SNG);
		}
		
		if( valign.equals("subscript")){
			runProperties.setBaseline(-25000);
		} else if( valign.equals("superscript")){
			runProperties.setBaseline(30000);
		}
		runProperties.setSolidFill(getCol(color));
		
		return runProperties;
	}

	public static CTTextParagraphProperties getParProperties(String textalign,int paddingbottom
			, int paddingtop, int paddingleft, int paddingright){
		CTTextParagraphProperties parProperties = new CTTextParagraphProperties();
		
		if( textalign.equals("left")) parProperties.setAlgn(STTextAlignType.L);
		else if( textalign.equals("center")) parProperties.setAlgn(STTextAlignType.CTR);
		else if( textalign.equals("right")) parProperties.setAlgn(STTextAlignType.R);
		else if( textalign.equals("justify")) parProperties.setAlgn(STTextAlignType.JUST);

		CTTextSpacing spaceafter = new CTTextSpacing();
		CTTextSpacingPoint pointafter = new CTTextSpacingPoint();
		pointafter.setVal(paddingbottom*100);
		spaceafter.setSpcPts(pointafter);
		parProperties.setSpcAft(spaceafter);

		CTTextSpacing spacebefore = new CTTextSpacing();
		CTTextSpacingPoint pointbefore = new CTTextSpacingPoint();
		pointbefore.setVal(paddingtop*100);
		spacebefore.setSpcPts(pointbefore);
		parProperties.setSpcBef(spacebefore);
		

		parProperties.setMarL(paddingleft*12700);
		parProperties.setMarR(paddingleft*12700);
              
        return parProperties;

	}


	
	public static CTTableCellProperties getCellProperties(String borderBottomColor, String borderBottomStyle, int borderBottomWidth
			, String borderLeftColor, String borderLeftStyle, int borderLeftWidth
			, String borderTopColor, String borderTopStyle, int borderTopWidth
			, String borderRightColor, String borderRightStyle, int borderRightWidth, 
			String verticalAlign, int paddingBottom, int paddingTop, int paddingLeft, int paddingRight
			, String backgroundColor
			) throws Exception {
		CTTableCellProperties tcPr = new CTTableCellProperties();
		
		tcPr.setSolidFill(getCol(backgroundColor));
		tcPr.setLnB(getBorder(borderBottomColor, borderBottomStyle, borderBottomWidth, "B"));
		tcPr.setLnT(getBorder(borderTopColor, borderTopStyle, borderTopWidth, "T"));
		tcPr.setLnR(getBorder(borderRightColor, borderRightStyle, borderRightWidth, "R"));
		tcPr.setLnL(getBorder(borderLeftColor, borderLeftStyle, borderLeftWidth, "L"));
				
	    if( verticalAlign.equals("center") )
	    	tcPr.setAnchor(STTextAnchoringType.CTR);
	    else if( verticalAlign.equals("middle") )
	    	tcPr.setAnchor(STTextAnchoringType.CTR);
	    else if( verticalAlign.equals("top") )
	    	tcPr.setAnchor(STTextAnchoringType.T);
	    else if( verticalAlign.equals("bottom") )
	    	tcPr.setAnchor(STTextAnchoringType.B);
	    else tcPr.setAnchor(STTextAnchoringType.CTR);
	    tcPr.setMarB(new Integer(paddingBottom*12700));
	    tcPr.setMarT(new Integer(paddingTop*12700));
	    tcPr.setMarR(new Integer(paddingRight*12700));
	    tcPr.setMarL(new Integer(paddingLeft*12700));

	    return tcPr;
	}
	
	public static CTLineProperties getBorder (String borderColor, String borderStyle, int borderWidth, String borderSuffix) throws Exception {
		CTLineProperties border = new CTLineProperties();
		if( borderWidth > 0 && !borderStyle.equals("none") ){
			border.setSolidFill(getCol(borderColor));
			border.setW(borderWidth*12700);
			CTPresetLineDashProperties lineStyle = new CTPresetLineDashProperties();
			
		    if( borderStyle.equals("solid") )
		    	lineStyle.setVal(STPresetLineDashVal.SOLID);
		    else if( borderStyle.equals("dotted") )
		    	lineStyle.setVal( STPresetLineDashVal.SYS_DOT);
		    else if( borderStyle.equals("dashed") )
		    	lineStyle.setVal( STPresetLineDashVal.SYS_DASH);
		    else lineStyle.setVal(STPresetLineDashVal.SOLID);
	    
		    border.setPrstDash(lineStyle);
		    
		} else {
			String borderStr = "<a:ln" + borderSuffix + " xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" w=\"12700\" cap=\"flat\" cmpd=\"sng\" algn=\"ctr\"><a:noFill /><a:prstDash val=\"solid\" /><a:round /><a:headEnd type=\"none\" w=\"med\" len=\"med\" /><a:tailEnd type=\"none\" w=\"med\" len=\"med\" /></a:ln" + borderSuffix + ">";
			border = ((CTLineProperties)XmlUtils.unmarshalString(borderStr, Context.jcPML, CTLineProperties.class) );
		}
	    return border;
	}
}
