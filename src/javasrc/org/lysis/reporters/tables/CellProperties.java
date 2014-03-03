/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.tables;


public class CellProperties {
	private BorderProperties borderBottom;
	private BorderProperties borderLeft;
	private BorderProperties borderTop;
	private BorderProperties borderRight;
	private String verticalAlign;
	private int paddingBottom;
	private int paddingTop;
	private int paddingLeft;
	private int paddingRight;
	private String backgroundColor;
	
	public CellProperties(BorderProperties borderBottom,
			BorderProperties borderLeft, BorderProperties borderTop,
			BorderProperties borderRight, String verticalAlign,
			int paddingBottom, int paddingTop, int paddingLeft,
			int paddingRight, String backgroundColor) {
		super();
		this.borderBottom = borderBottom;
		this.borderLeft = borderLeft;
		this.borderTop = borderTop;
		this.borderRight = borderRight;
		this.verticalAlign = verticalAlign;
		this.paddingBottom = paddingBottom;
		this.paddingTop = paddingTop;
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
		this.backgroundColor = backgroundColor;
	}


	
	public String getVerticalAlign() {
		return verticalAlign;
	}
	public int getPaddingBottom() {
		return paddingBottom;
	}
	public int getPaddingTop() {
		return paddingTop;
	}
	public int getPaddingLeft() {
		return paddingLeft;
	}
	public int getPaddingRight() {
		return paddingRight;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}



	public BorderProperties getBorderBottom() {
		return borderBottom;
	}



	public void setBorderBottom(BorderProperties borderBottom) {
		this.borderBottom = borderBottom;
	}



	public BorderProperties getBorderLeft() {
		return borderLeft;
	}



	public void setBorderLeft(BorderProperties borderLeft) {
		this.borderLeft = borderLeft;
	}



	public BorderProperties getBorderTop() {
		return borderTop;
	}



	public void setBorderTop(BorderProperties borderTop) {
		this.borderTop = borderTop;
	}



	public BorderProperties getBorderRight() {
		return borderRight;
	}



	public void setBorderRight(BorderProperties borderRight) {
		this.borderRight = borderRight;
	}

}
