package org.lysis.reporters.graphicaldevices.fonts;

import java.awt.Font;

public class FontInfo {
	
	public static String getFontFamily(String fontfamily){
		System.setProperty("java.awt.headless", "true");
		Font ft = new Font( fontfamily, 0, 12 );
		return ft.getFamily();
	}
	
}

