/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.html4r.elements;

public interface HTML4R {

	public String getHTML();
	public String getCSS();
	public String getJS();
	
	public boolean hasJS();
	public boolean hasHTML();
	public boolean hasCSS();
	
	
}
