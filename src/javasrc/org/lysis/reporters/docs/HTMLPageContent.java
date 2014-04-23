/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.docs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.lysis.reporters.html4r.elements.DataTable;
import org.lysis.reporters.html4r.elements.HTML4R;
import org.lysis.reporters.html4r.elements.MenuBar;
import org.lysis.reporters.html4r.elements.RScript;
import org.lysis.reporters.html4r.elements.ImagesList;
import org.lysis.reporters.html4r.elements.SetOfPot;
import org.lysis.reporters.html4r.elements.SetOfRaphaelPlots;
import org.lysis.reporters.html4r.elements.TOC;
import org.lysis.reporters.html4r.elements.Title;
import org.lysis.reporters.tables.FlexTable;


public class HTMLPageContent {
	
	public static int error = 0;
	public static int noproblem = 1;
	public static int fileproblem = 2;
	
	private TOC titles;
	private String title;
	private MenuBar mb;
	private LinkedHashMap<Integer, HTML4R> content;
	private int ncontent;
	private String charset;
	private Vector<String> cssfiles;
	private Vector<String> jsfiles;
	private Vector<String> jscodes;
	private Vector<String> csscodes;

	public HTMLPageContent ( String title, String charset) throws IOException {
		this.title = title;
		titles = new TOC(this.title);
		content = new LinkedHashMap<Integer, HTML4R>();
		ncontent = 0;
		setCharset(charset);
		cssfiles = new Vector<String>();
		jsfiles = new Vector<String>();
		jscodes = new Vector<String>();
		csscodes = new Vector<String>();
	}
	
	private int addHTML4R( HTML4R elt )  {
		
		if( elt instanceof org.lysis.reporters.html4r.elements.Title ){
			titles.addTitle((org.lysis.reporters.html4r.elements.Title)elt);
		}
		content.put(ncontent, elt);
		if( elt.hasJS() ) jscodes.add(elt.getJS());
		if( elt.hasCSS() ) csscodes.add(elt.getCSS());
		ncontent++;
		return noproblem;
	}
	
	public int add( Title elt )  {
		return addHTML4R(elt);
	}
	
	public int add( DataTable elt )  {
		return addHTML4R(elt);
	}
	
	public int add( FlexTable elt )  {
		return addHTML4R(elt);
	}
	
	public int add( SetOfPot elt )  {
		return addHTML4R(elt);
	}
	
	public int add( ImagesList elt )  {
		return addHTML4R(elt);
	}
	
	public int add( SetOfRaphaelPlots elt )  {
		return addHTML4R(elt);
	}
	
	public int add( RScript elt )  {
		return addHTML4R(elt);
	}
	
	public void setCharset(String charset ){
		this.charset = charset;
	}

	public void addStylesheet( String file ){
		if( !cssfiles.contains(file) ) cssfiles.add(file);
	}
	
	public void addJavascript( String file ){
		if( !jsfiles.contains(file) ) jsfiles.add(file);
	}
	
	private String getStylesheets(){
		String out = "";
		for( int i = 0 ; i < cssfiles.size() ; i++ ){
			out += "<link rel=\"stylesheet\" href=\"" + cssfiles.get(i) + "\" type=\"text/css\" media=\"all\" />";
		}
		return out;
	}
	
	private String getScripts(){
		String out = "";
		for( int i = 0 ; i < jsfiles.size() ; i++ ){
			out += "<script type=\"text/javascript\" src=\"" + jsfiles.get(i) + "\"></script>";
		}
		return out;
	}
	
	public void setMenuBar( MenuBar mb ) throws IOException {
		this.mb = mb;
	}
	
	public void setActiveMenuBarTitle( String title) throws IOException {
		mb.setActiveTitle(title);
	}


	public String getHead() {
		String out = "";
		out += "<head>";
			out += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />";
			out += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + charset + "\" />";
			out += "<title>" + title + "</title>";
			out += "<meta name=\"format-detection\" content=\"telephone=no\" />";
			out += getStylesheets();
			out += getScripts();

			for( int i = 0 ; i < csscodes.size() ; i++ ){
				out += "<style type=\"text/css\">";
				out += csscodes.get(i);
				out += "</style>";
			}
		out += "</head>";
		return out;

	}
	
	public String getJSCode() {
		String out = "";
		out += "<script type=\"text/javascript\">";

			for( int i = 0 ; i < jscodes.size() ; i++ ){
				out += jscodes.get(i);
			}
		out += "</script>";
		return out;

	}
	public String getBody( int index ) {
		return content.get(index).getHTML();
	}
	
	public int writeHtml(String target) throws IOException {		
		File f = new File(target);
		BufferedWriter output;
        try {
			output = new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			return fileproblem;
		}
		if( titles.hasJS() ) jscodes.add(titles.getJS());
		
        output.write( "<!DOCTYPE html>");
        output.write( "<html>" );
        output.write( getHead() );
		output.write("<body>");
		

		output.write("<div class=\"container bs-docs-container\">");//container
		    output.write("<div class=\"row\">");//row
		        output.write("<div class=\"col-md-3\" id=\"navbar-div\">");
			        output.write(titles.getHTML());
		        output.write("</div>");
		        output.write("<div class=\"col-md-9\" data-spy=\"scroll\" data-target=\"#navbar-div\">");;
	
					for( int i = 0 ; i < ncontent ; i++ ){
						HTML4R elt_output = content.get(i);
						if( elt_output.hasHTML() ) 
							output.write(elt_output.getHTML());
					}
		        output.write("</div>");//col-md-9
	        output.write("</div>");//row
        output.write("</div>");//container
        
	    output.write(mb.getHTML());
	    
		output.write(getJSCode());
		
		
		
		output.write("</body>");
		
        output.write( "</html>" );

		output.close();
        return noproblem;
        
	}
}

