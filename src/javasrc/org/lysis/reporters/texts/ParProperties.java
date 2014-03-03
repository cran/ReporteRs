/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.texts;

public class ParProperties {
	private String textalign;
	private int paddingbottom;
	private int paddingtop;
	private int paddingleft;
	private int paddingright;

	public String getTextalign() {
		return textalign;
	}

	public int getPaddingbottom() {
		return paddingbottom;
	}

	public int getPaddingtop() {
		return paddingtop;
	}

	public int getPaddingleft() {
		return paddingleft;
	}

	public int getPaddingright() {
		return paddingright;
	}

	public ParProperties(String textalign, int paddingbottom, int paddingtop,
			int paddingleft, int paddingright) {
		super();
		this.textalign = textalign;
		this.paddingbottom = paddingbottom;
		this.paddingtop = paddingtop;
		this.paddingleft = paddingleft;
		this.paddingright = paddingright;
	}

}
