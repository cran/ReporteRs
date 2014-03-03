/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.html4r.elements;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.lysis.reporters.html4r.tools.Format;
import org.lysis.reporters.html4r.tools.utils;


public class POT implements HTML4R{
	private String parent_type;
	private LinkedHashMap<Integer, String> textList;
	private LinkedHashMap<Integer, LinkedHashMap<String, String>> formatList;
	private int index;

	public POT( String parentType ) {
		textList = new LinkedHashMap<Integer, String>();
		formatList = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();
		index = -1;
		parent_type = parentType;
	}

	public void addPot ( String value, int size, boolean bold, boolean italic, boolean underlined, String color, String fontfamily, String valign ) throws IOException{
		index++;
		LinkedHashMap<String, String> rpr = Format.getTextProperties(color, size, bold, italic, underlined, fontfamily, valign);
		textList.put(index, org.apache.commons.lang.StringEscapeUtils.escapeHtml(value) );
		formatList.put(index, rpr);
	}
	
	public void addText ( String value ) throws IOException{
		index++;
		textList.put(index, org.apache.commons.lang.StringEscapeUtils.escapeHtml(value) );
		formatList.put(index, null);
	}



	@Override
	public String getHTML() {
		String out = "";
		if( parent_type.equals("div") ) out += "<p>";
		else if( parent_type.equals("ul") ) out += "<li>";
		else if( parent_type.equals("ol") ) out += "<li>";
		else if( parent_type.equals("pre") ) out += "";
		else out += "<p>";

		for(int i = 0 ; i < index +1 ; i++ ){
			if( formatList.get(i) == null ){
				out += utils.span( textList.get(i) );
			}
			else {
				out += utils.span_style( textList.get(i)
						, org.lysis.reporters.html4r.tools.Format.getJSString(formatList.get(i)) 
						);
			}
		}
		
		if( parent_type.equals("div") ) out += "</p>";
		else if( parent_type.equals("ul") ) out += "</li>";
		else if( parent_type.equals("ol") ) out += "</li>";
		else if( parent_type.equals("pre") ) out += "";
		else out += "</p>";


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
