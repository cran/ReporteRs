/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */
package org.lysis.reporters.html4r.tools;

import org.apache.commons.lang.RandomStringUtils;

public class utils {
	public static final int ID_LENGTH = 10;

	public static String generateUniqueId() {
	    return "UID" + RandomStringUtils.randomAlphanumeric(ID_LENGTH);
	}
	
	public static String span(String text){
		return "<span>" + text + "</span>";
	}
	public static String span_style(String text, String style){
		return "<span style=\"" + style + "\">" + text + "</span>";
	}
	public static String div(String text){
		return "<div>" + text + "</div>";
	}
	public static String pre(String text){
		return "<pre>" + text + "</pre>";
	}
	public static String div(String text, String _class){
		return "<div class=\"" + _class + "\">" + text + "</div>";
	}
	public static String div_open(){
		return "<div>";
	}

	public static String div_open(String _class){
		return "<div class=\"" + _class + "\">";
	}
	public static String div_close(){
		return "</div>";
	}

	public static String ul_open(String _class){
		return "<ul class=\"" + _class + "\">";
	}
	public static String ul_close(){
		return "</ul>";
	}
	
	public static String ol_open(String _class){
		return "<ol class=\"" + _class + "\">";
	}
	public static String ol_close(){
		return "</ol>";
	}
	public static String pre_open(String _class){
		return "<pre class=\"" + _class + "\">";
	}
	public static String pre_close(){
		return "</pre>";
	}
	
	public static String title(String title, int level, String id){
		return "<h" + level + " id=\"" + id + "\">" + title + "</h" + level + ">";
	}
}
