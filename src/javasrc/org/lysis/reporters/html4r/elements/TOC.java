/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.html4r.elements;

import java.util.LinkedHashMap;

import org.lysis.reporters.html4r.tools.Tree;


public class TOC implements HTML4R{
	private LinkedHashMap<Integer, Title> titles;
	private int titleIndex;
	private Tree<Title> tree;
	private Title currentContainer;
	private int currentLevel;
	
	
	public TOC(String headStr){
		titles = new LinkedHashMap<Integer, Title>();
		titleIndex=-1;
		currentLevel = 0;
		Title root = new Title(headStr, 0);
		tree = new Tree<Title>(root);
		currentContainer = root;
	}
	
	public void addTitle(Title title){
		titleIndex++;
		titles.put(titleIndex, title);
		
		if( title.getLevel() > currentLevel ){
			tree.addLeaf(currentContainer, title);
		} else if( title.getLevel() == currentLevel ){
			tree.getTree(currentContainer).getParent().addLeaf(title);
		}else if( title.getLevel() < currentLevel ){
			tree.getTree(currentContainer).getParent().getParent().addLeaf(title);
		} else {
		}
		currentContainer = title; 
		currentLevel = title.getLevel();
	}
  
	@Override
	public String getHTML() {
		String out = "";
		out += "<div class=\"navbar-fixed-topleft nav nav-list bs-sidebar\" role=\"navigation\">";
		out += tree.Tree2HTML();
		out += "</div>";
		return out;
	}

	@Override
	public String getCSS() {
		return "";
	}

	@Override
	public String getJS() {
		String str = ""
				+ "var $window = $(window)\n"
				+ "var $body   = $(document.body)\n"

				+ "var navHeight = $('.navbar').outerHeight(true) + 10\n"

				+ "$body.scrollspy({\n"
				+ "	target: '.bs-sidebar',\n"
				+ "	offset: navHeight\n"
				+ "})\n"

				+ "$window.on('load', function () {\n"
				+ "	$body.scrollspy('refresh')\n"
				+ "})\n"

				+ "$('.bs-docs-container [href=#]').click(function (e) {\n"
				+ "	e.preventDefault()\n"
				+ "})\n"

				+ "setTimeout(function () {\n"
				+ "	var $sideBar = $('.bs-sidebar')\n"

				+ "$sideBar.affix({\n"
				+ "	offset: {\n"
				+ "	top: function () {\n"
				+ "		var offsetTop	  = $sideBar.offset().top\n"
				+ "		var sideBarMargin  = parseInt($sideBar.children(0).css('margin-top'), 10)\n"
				+ "		var navOuterHeight = $('.bs-docs-nav').height()\n"
				+ "		return (this.top = offsetTop - navOuterHeight - sideBarMargin)\n"
				+ "		}"
				+ "	, bottom: function () {\n"
				+ "		return (this.bottom = $('.bs-footer').outerHeight(true))\n"
				+ "		}\n"
				+ "	}\n"
				+ "	})\n"
				+ "}, 100)\n"

				+ "setTimeout(function () {\n"
				+ "	$('.bs-top').affix()\n"
				+ "}, 100)\n";
		return str;//"$('body').scrollspy({ target: '#navbar-div' });";
	}
	@Override
	public boolean hasJS() {
		return true;
	}

	@Override
	public boolean hasCSS() {
		return false;
	}

	@Override
	public boolean hasHTML() {
		return true;
	}


}
