/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.docx4r.tools;

import java.util.List;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.model.structure.HeaderFooterPolicy;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.P;


public class DocExplorer {
	
    private static BookmarkObject traversePartForBookmarkObject(Object parent, String bookmark) {
    	BookmarkObject p = new BookmarkObject();
        bookmark = bookmark.toLowerCase();
        
        List<Object> children = TraversalUtil.getChildrenImpl(parent);
        if (children != null) {
            for (Object o : children) {
                o = XmlUtils.unwrap(o);
                if (o instanceof CTBookmark) {
                	CTBookmark bm = ((CTBookmark) o);
                    if (bm.getName().toLowerCase().equals(bookmark)) {
                    	p = new BookmarkObject((P) parent, bm);
                        return p;// If bookmark found, we return the surrounding P
                    }
                }
                p = traversePartForBookmarkObject(o, bookmark);
                if (p.exists()) {
                    break;
                }
            }
        }
        return p;
    }

	public static BookmarkObject getBookmarkObject( String bookmark, WordprocessingMLPackage wmlp ){
		BookmarkObject p = traversePartForBookmarkObject(wmlp.getMainDocumentPart(), bookmark);
		if( !p.exists() ){
			List<SectionWrapper> sectionWrappers = wmlp.getDocumentModel().getSections();
			for (SectionWrapper sw : sectionWrappers) {
				if( p.exists() ) break;
				HeaderFooterPolicy hfp = sw.getHeaderFooterPolicy();
				if (!p.exists() && hfp.getFirstHeader() != null)
					p = traversePartForBookmarkObject(hfp.getFirstHeader(), bookmark);
				if (!p.exists() && hfp.getDefaultHeader() != null)
					p = traversePartForBookmarkObject(hfp.getDefaultHeader(), bookmark);
				if (!p.exists() && hfp.getEvenHeader() != null)
					p = traversePartForBookmarkObject(hfp.getEvenHeader(), bookmark);
				if (!p.exists() && hfp.getFirstFooter() != null)
					p = traversePartForBookmarkObject(hfp.getFirstFooter(), bookmark);
				if (!p.exists() && hfp.getDefaultFooter() != null)
					p = traversePartForBookmarkObject(hfp.getDefaultFooter(), bookmark);
				if (!p.exists() && hfp.getEvenFooter() != null)
					p = traversePartForBookmarkObject(hfp.getEvenFooter(), bookmark);
			}
		}

		return p;
	}
	
}
