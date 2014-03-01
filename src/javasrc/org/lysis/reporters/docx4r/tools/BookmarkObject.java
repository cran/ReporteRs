/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.docx4r.tools;

import java.math.BigInteger;

import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.P;

public class BookmarkObject {
	private P p;
	private BigInteger bookmarkID;
	
	public BookmarkObject( ){
		bookmarkID = null;
		this.p = null;
	}
	
	public BookmarkObject( P p, CTBookmark bm){
		bookmarkID = bm.getId();
		this.p = p;
	}

	public boolean exists(){
		return p!=null;
	}
	
	public P getP() {
		return p;
	}

	public BigInteger getBookmarkID() {
		return bookmarkID;
	}
	
}
