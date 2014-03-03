/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.html4r.elements;

import java.util.LinkedHashMap;

import org.lysis.reporters.html4r.tools.utils;



public class SetOfPot implements HTML4R{
	private String class_;
	private LinkedHashMap<Integer, POT> potList;
	private int index;
	String type;
	
	public SetOfPot(String type, String class_){
		this.class_=class_;
		this.type = type;
		index = -1;
		potList = new LinkedHashMap<Integer, POT>();
	}
	
	public void addP ( POT pot ){
		index++;
		potList.put(index, pot);
	}
	
	private POT getP( int i) {
		return potList.get(i);
	}



	@Override
	public String getHTML() {
		String out = "";
		if( type.equals("div") ) out += utils.div_open(class_);
		else if( type.equals("ul") ) out += utils.ul_open(class_);
		else if( type.equals("ol") ) out += utils.ol_open(class_);
		else if( type.equals("pre") ) out += utils.pre_open(class_);
		else out += utils.div_open(class_);
		
		for(int i = 0 ; i <= index ; i++ ){
			if( type.equals("pre") )out += "\n";
			out += getP(i).getHTML();
		}
		if( type.equals("div") ) out += utils.div_close();
		else if( type.equals("ul") ) out += utils.ul_close();
		else if( type.equals("ol") ) out += utils.ol_close();
		else if( type.equals("pre") ) out += utils.pre_close();
		else out += utils.div_close();
		
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
	public boolean hasCSS() {
		return false;
	}

	@Override
	public boolean hasHTML() {
		return true;
	}

}
