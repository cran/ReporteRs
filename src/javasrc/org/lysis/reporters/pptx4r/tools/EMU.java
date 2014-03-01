/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.pptx4r.tools;

public class EMU {
	private static long inch = 914400;
	private static long centimeter = 360000;

	public static long getFromInch( double x ){
		return (new Double( x * inch )).longValue();
	}
	public static long getFromCm( double x ){
		return (new Double( x * centimeter )).longValue();
	}
}
