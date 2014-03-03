/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.html4r.elements;

import org.lysis.reporters.html4r.tools.utils;


public class Title implements HTML4R{
	private String value;
	private int level;
	private String uid;

	public Title(String str, int lev){
		value = org.apache.commons.lang.StringEscapeUtils.escapeHtml(str);
		level = lev;
		uid = utils.generateUniqueId();
	}
	
	public String getUID(){
		return uid;
	}
	public int getLevel(){
		return level;
	}
	public String getValue(){
		return value;
	}
	

	@Override
	public String getHTML() {
		return utils.title(value, level, uid);
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
