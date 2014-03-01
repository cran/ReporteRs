/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.docx4r.elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import javax.xml.bind.JAXBElement;

import org.docx4j.dml.CTPositiveSize2D;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.CTMarkupRange;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;

public class Image {

//	public static Inline getImageInline(String filename, WordprocessingMLPackage wordMLPackage, int docPrId, int cNvPrId ) throws Exception {
//		File file = new File(filename);
//		byte[] bytes = convertImageToByteArray(file);
//		
//		BinaryPartAbstractImage imagePart =BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
//		Inline inline = imagePart.createImageInline("Filename hint", "Alternative text", docPrId, cNvPrId, false);
//
//		return inline;
//	}
	
	private byte[] image_bytes;
	
	private boolean hasBookmark;
	private JAXBElement<CTBookmark> bmStart;
	private JAXBElement<CTMarkupRange> bmEnd;

	public Image(String filename){
		File file = new File(filename);
		try {
			image_bytes = convertImageToByteArray(file);
		} catch (Exception e) {
		}
		hasBookmark = false;

	}
	
	public void setBookmark(String bookmark, BigInteger bookmarkID){
		hasBookmark = true;
		
		org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
		CTBookmark bm = new CTBookmark();
		bm.setId(bookmarkID);
		bm.setName(bookmark);		
		bmStart =  factory.createBodyBookmarkStart(bm);

		CTMarkupRange mr = factory.createCTMarkupRange();
		mr.setId(bookmarkID);
		bmEnd = factory.createBodyBookmarkEnd(mr);
	}
	
	public P addImageToPackage( WordprocessingMLPackage wordMLPackage, int docPrId, int cNvPrId, int width, int height, PPr ppr) throws Exception {
		
		BinaryPartAbstractImage imagePart =BinaryPartAbstractImage.createImagePart(wordMLPackage, image_bytes);
		Inline inline = imagePart.createImageInline("Filename hint", "Alternative text", docPrId, cNvPrId, false);
		CTPositiveSize2D size = new CTPositiveSize2D();
		size.setCx(width);
		size.setCy(height);
		inline.setExtent(size);
		return addInlineImageToParagraph(inline, ppr);
	}
	

	public static byte[] convertImageToByteArray(File file) throws FileNotFoundException, IOException {
		InputStream is = new FileInputStream(file );
		long length = file.length();
		// You cannot create an array using a long, it needs to be an int.
		if (length > Integer.MAX_VALUE) {
			System.out.println("File too large!!");
		}
		byte[] bytes = new byte[(int)length];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			offset += numRead;
		}
		// Ensure all the bytes have been read
		if (offset < bytes.length) {
			System.out.println("Could not completely read file " + file.getName());
		}
		is.close();
		return bytes;
	}
	
	
	private P addInlineImageToParagraph(Inline inline, PPr ppr) { 
		// Now add the in-line image to a paragraph 	
		
		ObjectFactory factory = new ObjectFactory(); 
		P paragraph = factory.createP(); 
		paragraph.setPPr(ppr);
		if( hasBookmark ) paragraph.getContent().add(bmStart);
		R run = factory.createR(); 
		paragraph.getContent().add(run); 
		Drawing drawing = factory.createDrawing(); 
		run.getContent().add(drawing); 
		drawing.getAnchorOrInline().add(inline); 
		if( hasBookmark ) paragraph.getContent().add(bmEnd);

		return paragraph; 
	} 
	
	public static Drawing addInlineImageToDrawing(String filename, WordprocessingMLPackage wordMLPackage, int docPrId, int cNvPrId) throws Exception { 
		File file = new File(filename);
		byte[] bytes = convertImageToByteArray(file);
		
		BinaryPartAbstractImage imagePart =BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
		Inline inline = imagePart.createImageInline("Filename hint", "Alternative text", docPrId, cNvPrId, false);
		// Now add the in-line image to a paragraph 
		ObjectFactory factory = new ObjectFactory(); 
		Drawing drawing = factory.createDrawing();
		drawing.getAnchorOrInline().add(inline); 
		return drawing; 
	} 
}
