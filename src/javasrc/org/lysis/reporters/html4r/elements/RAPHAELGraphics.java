/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.html4r.elements;

import java.util.Iterator;
import java.util.LinkedHashMap;

import org.lysis.reporters.html4r.tools.utils;

public class RAPHAELGraphics implements HTML4R{
	private LinkedHashMap<String, String> raphael_cmds;
	public RAPHAELGraphics(){
		raphael_cmds = new LinkedHashMap<String, String>();
	}
	
	public void registerGraphic(String id, String file){
		raphael_cmds.put(id, file);
	}


	@Override
	public String getHTML() {
		String out = "";
		out += utils.div_open("container");
		for (Iterator<String> it1 = raphael_cmds.keySet().iterator(); it1.hasNext();) {
			String id = it1.next();
			out += "<div id=\"" + id + "\" width=\"100%\"></div>";
		}
		out += utils.div_close();
		return out;
	}

	@Override
	public String getCSS() {
		return "";
	}

	@Override
	public String getJS() {
		String out = "";
		for (Iterator<String> it1 = raphael_cmds.keySet().iterator(); it1.hasNext();) {
			String id = it1.next();
			out += raphael_cmds.get(id) + "\n";
		}
		return out;
	}

	@Override
	public boolean hasJS() {
		return true;
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
