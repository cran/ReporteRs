/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.html4r.elements;

import java.util.Vector;

import org.lysis.reporters.html4r.tools.utils;

public class ImagesList implements HTML4R{
	private Vector<String> img64;
	private int width;
	private int height;
	
	public ImagesList(int w, int h){
		img64 = new Vector<String>();
		width = w;
		height = h;
	}
	
	public void addImage(String img){
		img64.add(img);
	}

	@Override
	public String getHTML() {
		String out = "";
		for( int i = 0 ; i < img64.size() ; i++ ){
			out += "<img src=\"data:image/png;base64," + img64.get(i) + "\" height=\"" + height + "\" width=\"" + width + "\">";
		}
		return utils.div( out );
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
