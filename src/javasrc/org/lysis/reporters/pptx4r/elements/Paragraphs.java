/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.pptx4r.elements;

import org.docx4j.XmlUtils;
import org.docx4j.dml.CTTextBody;
import org.lysis.reporters.texts.ParagraphsSection;
import org.pptx4j.jaxb.Context;
import org.pptx4j.pml.Shape;



public class Paragraphs {
	private static String SAMPLE_SHAPE_START =                         
            "<p:sp xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">"
            + "<p:nvSpPr>"
	            + "<p:cNvPr id=\"${id_shape}\" name=\"Title ${title_shape}\" />"
		            + "<p:cNvSpPr>"
		                    + "<a:spLocks noGrp=\"${noGrp}\" />"
		            + "</p:cNvSpPr>"
	            + "<p:nvPr>"
                    + "<p:ph idx=\"${idx}\"/>"//+ "<p:ph idx=\"${idx}\" ${size}/>"
	            + "</p:nvPr>"
		    + "</p:nvSpPr>"
		    + "<p:spPr>"
		      + "<a:xfrm>"
		        + "<a:off x=\"0\" y=\"0\"/>"
		        + "<a:ext cx=\"0\" cy=\"0\"/>"
		      + "</a:xfrm>"
		    + "</p:spPr>"
		    + "<p:txBody>"
	            + "<a:bodyPr />";
	private static String SAMPLE_SHAPE_END = "</p:txBody>" + "</p:sp>";
	
	private CTTextBody tbody;
	public Paragraphs(){
		this.tbody = null;
	}

	public void setTextBody ( ParagraphsSection tbody ) throws Exception{
		this.tbody = tbody.getCTTextBody();
	}

	public Shape getShape(long shape_id, long idx) throws Exception{

		java.util.HashMap<String, String>mappings = new java.util.HashMap<String, String>();
        mappings.put("id_shape", shape_id + "" );
        mappings.put("title_shape", "Texts" + shape_id);
        mappings.put("idx", idx+"" );
        mappings.put("noGrp", "1" );

        Shape o = (Shape) XmlUtils.unmarshallFromTemplate(SAMPLE_SHAPE_START + "<a:p/>" + SAMPLE_SHAPE_END, mappings,Context.jcPML, Shape.class);
        if( tbody != null )
        	o.setTxBody(tbody);

		return o;
	}
}
