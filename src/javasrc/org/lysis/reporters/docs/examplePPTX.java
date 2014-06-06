/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.docs;

import org.lysis.reporters.pptx4r.elements.DataTable;
import org.lysis.reporters.pptx4r.tools.Format;
import org.lysis.reporters.tables.TableLayoutPPTX;


public class examplePPTX {
	private static DataTable getTableExample() throws Exception{
		TableLayoutPPTX tf = new TableLayoutPPTX("%", 3, 2, "YYYY-mm-dd", "HH:MM", "YYYY-mm-dd HH:MM", "en", "US");
		tf.setHeaderText("red", 12, true, false, false, "Arial", "baseline");
		tf.setGroupedheaderText("red", 12, true, false, false, "Arial", "baseline");
		tf.setDoubleText("black", 10, true, false, false, "Arial", "baseline");
		tf.setIntegerText("black", 10, true, false, false, "Arial", "baseline");
		tf.setPercentText("black", 10, true, false, false, "Arial", "baseline");
		tf.setCharacterText("black", 10, true, true, false, "Arial", "baseline");
		tf.setDateText("black", 10, true, false, false, "Arial", "baseline");
		tf.setDatetimeText("black", 10, true, false, false, "Arial", "baseline");
		tf.setHeaderPar("center", 1, 1, 1, 1);
		tf.setGroupedheaderPar("center", 1, 1, 1, 1);
		tf.setDoublePar("right", 1, 1, 1, 1);
		tf.setIntegerPar("right", 1, 1, 1, 1);
		tf.setPercentPar("right", 1, 1, 1, 1);
		tf.setCharacterPar("left", 10, 10, 12, 12);
		tf.setDatePar("center", 1, 1, 1, 1);
		tf.setDatetimePar("center", 1, 1, 1, 1);
		
		tf.setHeaderCell("black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "middle", 3, 3, 3, 3, "gray");
		tf.setGroupedheaderCell("black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "middle", 3, 3, 3, 3, "orange");
		tf.setDoubleCell("black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "middle", 2, 2, 2, 2, "#FFFFFF");
		tf.setIntegerCell("black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "middle", 2, 2, 2, 2, "#FFFFFF");
		tf.setPercentCell("black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "middle", 2, 2, 2, 2, "#FFFFFF");
		tf.setCharacterCell("black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "middle", 2, 2, 2, 2, "#FFFFFF");
		tf.setDatetimeCell("black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "black", "solid", 1, "middle", 2, 2, 2, 2, "#FFFFFF");
		
		DataTable dt = new DataTable(tf);
		double[] SepalLength = { 5.1,4.9,4.7,4.6,5 };
		dt.setData( "Sepal.Length", "Sepal Length", SepalLength );
		double[] SepalWidth = { 5.1,4.9,4.7,4.6,5 };
		dt.setData( "Sepal.Width", "Sepal Width", SepalWidth );
		double[] PetalLength = { 1.4,1.4,1.3,1.5,1.4 };
		dt.setData( "Petal.Length", "Petal Length", PetalLength );
		double[] PetalWidth = { 0.2,0.2,0.2,0.2,0.2 };
		dt.setData( "Petal.Width", "Petal Width", PetalWidth );
		String[] Species = { "setosa","coco","coco","setosa","setosa" };
		dt.setData( "Species", "Species", Species );
		
		dt.setGroupedCols("Measures", 4);
		dt.setGroupedCols("Types", 1);
		int[] colMergeInst = {1,2,0,1,1};
		dt.setMergeInstructions("Species", colMergeInst);
		
		String[] cols = { "yellow","red","blue","#ee006a","gray" };
		dt.setFillColors("Sepal.Length", cols);
		return dt;
	}
	public static void generateReport() throws Exception{
		pptx4R toto = new pptx4R();
		toto.setBaseDocument("D:/WebDocReports/Presentation1.pptx");
		SlideContent sc;
		
		sc = new SlideContent( "Diapositive de titre", toto );
		sc.addTitle("Mon Titre");
		sc.addSubTitle("Mon Sous-Titre");
		sc.addDate("Mon premier titre");
		sc.addSlideNumber("1");
		sc.addFooter("footer example");
		
		sc = new SlideContent( "Titre et contenu", toto );
		sc.addTitle("Mon premier titre");
		sc.addPicture( "C:/temp/gas.png" );
		sc.addDate("Mon premier titre");
		sc.addSlideNumber();
		sc.addFooter("footer example");
		sc.addSubTitle("Mon Sous-Titre");//retourn noroomleft

		sc = new SlideContent( "Deux contenus", toto );
		sc.addTitle("Mon Tableau");
		sc.add(getTableExample() );

//		String[] texts = { "[animal] eat [food].","[animal] are food for [superpredator].","Hello world."};
//		Paragraph par = new Paragraph(texts);
//		par.addReplacement("[animal]", "Tigers", 12, true, true, false, "red", "Arial");
//		par.addReplacement("[food]", "dears", 16, true, true, false, "blue", "Arial");
//		par.addReplacement("[superpredator]", "humans", 16, true, true, false, "orange", "Arial");
//		sc.add( par );
		sc.addDate("2012-01-85") ;

		sc = new SlideContent( "Titre et contenu", toto );
		sc.addTitle("Paragraph simple");
//		String[] words = { "hello","world","!" };
//		par = new Paragraph(words);
//		sc.add( par );
//		sc.getXfrmMeta(3);
		toto.writePptxToStream("D:/WebDocReports/test.pptx");
	}
	
	public static void replacementTest() throws Exception{
		pptx4R toto = new pptx4R();
		toto.setBaseDocument("D:/WebDocReports/presentation.pptx");
		System.out.println(toto.getStyleNames()[1]);

		SlideContent sc;
		sc = new SlideContent( "Titre et contenu", toto );
		sc.addTitle("Mon é < Titre &");
		

		toto.writePptxToStream("D:/WebDocReports/test2.pptx");
	}
	public static void main( String[] args ) throws Exception{
//		generateReport();
		//replacementTest();
		Format.getBorder("000000", "solid", 0, "B");


	}
}
