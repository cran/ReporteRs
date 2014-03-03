/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.pptx4r.elements;

import javax.xml.bind.JAXBException;

import org.docx4j.dml.CTRegularTextRun;

public class Utils {
	public static CTRegularTextRun getRun( String text ) throws JAXBException{
		CTRegularTextRun ct = new CTRegularTextRun();
		ct.setT(text);
		return ct;
	}

}
