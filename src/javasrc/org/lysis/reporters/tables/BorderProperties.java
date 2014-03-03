/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.tables;

public class BorderProperties {
	private String borderColor;
	private String borderStyle;
	private int borderWidth;

	public BorderProperties(String borderColor, String borderStyle,
			int borderWidth) {
		super();
		this.borderColor = borderColor;
		this.borderStyle = borderStyle;
		this.borderWidth = borderWidth;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public String getBorderStyle() {
		return borderStyle;
	}

	public int getBorderWidth() {
		return borderWidth;
	}

}
