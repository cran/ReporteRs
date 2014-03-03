/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.texts;

public class TextProperties {
	private int size;
	private boolean bold;
	private boolean italic;
	private boolean underlined;
	private String color;
	private String fontfamily;
	private String valign;
	
	public int getSize() {
		return size;
	}

	public boolean isBold() {
		return bold;
	}
	public String getValign(){
		return valign;
	}
	public boolean isSub() {
		return valign.equals("subscript");
	}
	
	public boolean isSuper() {
		return valign.equals("superscript");
	}
	
	public boolean isItalic() {
		return italic;
	}

	public boolean isUnderlined() {
		return underlined;
	}

	public String getColor() {
		return color;
	}

	public String getFontfamily() {
		return fontfamily;
	}

	public TextProperties(int size, boolean bold, boolean italic,
			boolean underlined, String color, String fontfamily, String valign) {
		super();
		this.size = size;
		this.bold = bold;
		this.italic = italic;
		this.underlined = underlined;
		this.color = color;
		this.fontfamily = fontfamily;
		this.valign = valign;
	}

}
