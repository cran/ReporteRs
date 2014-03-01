/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.docx4r.elements;

import org.docx4j.wml.Br;
import org.docx4j.wml.P;

import org.docx4j.wml.STBrType;

public class PageBreak {

	/**
	 * @param args
	 */
	public static P getBreak(){
		P p = new P();
		Br br = new Br();
		br.setType(STBrType.PAGE); 
		p.getContent().add(br);
		return p;
		
	}

}
