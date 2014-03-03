/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.docs;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.lysis.reporters.html4r.elements.MenuBar;
import org.lysis.reporters.html4r.tools.utils;




public class html4R {
	private LinkedHashMap<String, HTMLPageContent> pages;
	private LinkedHashMap<String, String> links;
	private String title;
	private int index;
	public html4R ( String title, String charset) throws IOException {
		pages  = new LinkedHashMap<String, HTMLPageContent>();
		links  = new LinkedHashMap<String, String>();
		this.title = title;
		index = 0;
	}
	public void addNewPage ( String title, HTMLPageContent page){
		
		index++;
		String filename = "page_" + index + "_" + utils.generateUniqueId() + ".html";
		pages.put(title, page);
		links.put(title, filename);
		
	}

	public int writeDocument(String directory) {
		
		MenuBar mb = new MenuBar(title);
		for (Iterator<String> it1 = pages.keySet().iterator(); it1.hasNext();) {
			String id = it1.next();
			mb.addTitle(id, links.get(id));
		}
		for (Iterator<String> it1 = pages.keySet().iterator(); it1.hasNext();) {
			String id = it1.next();
			HTMLPageContent doc = pages.get(id);
			try {
				doc.setMenuBar((MenuBar)mb.clone());
			} catch (IOException e) {
				return( HTMLPageContent.error );
			}
			try {
				doc.setActiveMenuBarTitle(id);
			} catch (IOException e) {
				return( HTMLPageContent.error );
			}
		}
		
		for (Iterator<String> it1 = pages.keySet().iterator(); it1.hasNext();) {
			String id = it1.next();
			HTMLPageContent doc = pages.get(id);
			try {
				doc.writeHtml( directory + "/" + links.get(id) );
			} catch (IOException e) {
				return( HTMLPageContent.error );
			} 
		}
		return( HTMLPageContent.noproblem );
		
	}



}

