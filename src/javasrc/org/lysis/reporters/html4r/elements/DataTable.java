/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.html4r.elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.lysis.rdata.RAtomicInterface;
import org.lysis.reporters.html4r.tools.Format;
import org.lysis.reporters.html4r.tools.utils;
import org.lysis.reporters.tables.TableBase;
import org.lysis.reporters.tables.TableLayoutHTML;



public class DataTable extends TableBase implements HTML4R{
	

	private TableLayoutHTML rSpecFormats;
	private String uid;
	public DataTable(TableLayoutHTML tf) throws FileNotFoundException, IOException {
		super();
		rSpecFormats = tf;
		uid = utils.generateUniqueId();

	}
	

	private String HeaderHTML( ) {
		//Grouped Headers
		String out = "";
		out += "<thead>";
		if( hasGroupColumns() ){
			out += "<tr>";
			for (int i = 0 ; i < groupColsSpecifications.size(); i++ ) {
				String groupColName = groupColsSpecifications.get(i).getValue();
				int colSpan = groupColsSpecifications.get(i).getColspan();
				out += "<th class=\"GroupedheaderCell\" colSpan=\"" + colSpan + "\">";
				out += "<p class=\"GroupedheaderPar\">";
				out += "<span class=\"GroupedheaderText\">";
				out += groupColName;
				out += "</span>";
				out += "</p>";
				out += "</th>";
			}
			out += "</tr>";
		} 
		//Headers
		out += "<tr>";
		for (Iterator<String> it2 = columnLabels.keySet().iterator(); it2.hasNext();) {
			String colName = it2.next();
			out += "<th class=\"HeaderCell\">";
			out += "<p class=\"HeaderPar\">";
			out += "<span class=\"HeaderText\">";
			out += columnLabels.get(colName);
			out += "</span>";
			out += "</p>";
			out += "</th>";
		}
		out += "</tr>";

		out += "</thead>";
		return out;
	}
	


	private String ContentHTML()  {
		String out = "";
		out += "<tbody>";		
		for( int i = 0 ; i < data.get(0).size() ; i++ ){
			out += "<tr>";		
			for( int j = 0 ; j < data.size() ; j++ ){
				RAtomicInterface robj = data.get(j);
				String cell = "<td class=\"" + robj.getTdCssClass() + "\"";		

				if( fillColors.containsKey(data.names(j))){
					cell += " style=\"background-color:" + fillColors.get(data.names(j))[i] + ";\"";
				} 

				if( !mergeInstructions.containsKey( data.names(j) ) ){
				} else if( mergeInstructions.get( data.names( j ) )[i] == 0 ){					
				} else if( mergeInstructions.get( data.names( j ) )[i] == 1 ){
				} else if( mergeInstructions.get( data.names( j ) )[i] > 1 ){
					cell += " rowspan=\"" + mergeInstructions.get( data.names( j ) )[i] + "\"";
				} else {
				} 
				cell += ">";
				
				if( mergeInstructions.containsKey( data.names(j) ) && mergeInstructions.get( data.names( j ) )[i] == 0 ){
					cell = "";
				} else {
					if( !fontColors.containsKey(data.names(j))){
						cell += robj.getHTML(i, rSpecFormats );
						cell += "</td>";
					} else {
						cell += robj.getHTML(i, fontColors.get(data.names(j))[i], rSpecFormats );
						cell += "</td>";
					}
				}
				out += cell;
			}
			out += "</tr>";		
		}
		out += "</tbody>";		
		return out;
	}


	@Override
	public String getHTML() {
		String out = "";
		out += "<table id=\"" + uid + "\">";
		out += HeaderHTML();
		out += ContentHTML();
		out += "</table>";
		
		return out;
	}

	@Override
	public String getCSS() {
		String out = "";
		out += "#" + uid + " .GroupedheaderCell{" + Format.getJSString(rSpecFormats.getGroupedheaderCell()) + "}";
		out += "#" + uid + " .HeaderCell{" + Format.getJSString(rSpecFormats.getHeaderCell()) + "}";
		out += "#" + uid + " .IntegerCell{" + Format.getJSString(rSpecFormats.getIntegerCell()) + "}";
		out += "#" + uid + " .DoubleCell{" + Format.getJSString(rSpecFormats.getDoubleCell()) + "}";
		out += "#" + uid + " .PercentCell{" + Format.getJSString(rSpecFormats.getPercentCell()) + "}";
		out += "#" + uid + " .CharacterCell{" + Format.getJSString(rSpecFormats.getCharacterCell()) + "}";
		out += "#" + uid + " .DateCell{" + Format.getJSString(rSpecFormats.getDateCell()) + "}";
		out += "#" + uid + " .LogicalCell{" + Format.getJSString(rSpecFormats.getLogicalCell()) + "}";

		out += "#" + uid + " .GroupedheaderPar{margin: 0px;" + Format.getJSString(rSpecFormats.getGroupedheaderPar()) + "}";
		out += "#" + uid + " .HeaderPar{margin: 0px;" + Format.getJSString(rSpecFormats.getHeaderPar()) + "}";
		out += "#" + uid + " .IntegerPar{margin: 0px;" + Format.getJSString(rSpecFormats.getIntegerPar()) + "}";
		out += "#" + uid + " .DoublePar{margin: 0px;" + Format.getJSString(rSpecFormats.getDoublePar()) + "}";
		out += "#" + uid + " .PercentPar{margin: 0px;" + Format.getJSString(rSpecFormats.getPercentPar()) + "}";
		out += "#" + uid + " .CharacterPar{margin: 0px;" + Format.getJSString(rSpecFormats.getCharacterPar()) + "}";
		out += "#" + uid + " .DatePar{margin: 0px;" + Format.getJSString(rSpecFormats.getDatePar()) + "}";
		out += "#" + uid + " .LogicalPar{margin: 0px;" + Format.getJSString(rSpecFormats.getLogicalPar()) + "}";

		out += "#" + uid + " .GroupedheaderText{" + Format.getJSString(rSpecFormats.getGroupedheaderText()) + "}";
		out += "#" + uid + " .HeaderText{" + Format.getJSString(rSpecFormats.getHeaderText()) + "}";
		out += "#" + uid + " .IntegerText{" + Format.getJSString(rSpecFormats.getIntegerText()) + "}";
		out += "#" + uid + " .DoubleText{" + Format.getJSString(rSpecFormats.getDoubleText()) + "}";
		out += "#" + uid + " .PercentText{" + Format.getJSString(rSpecFormats.getPercentText()) + "}";
		out += "#" + uid + " .CharacterText{" + Format.getJSString(rSpecFormats.getCharacterText()) + "}";
		out += "#" + uid + " .DateText{margin: 0px;" + Format.getJSString(rSpecFormats.getDateText()) + "}";
		out += "#" + uid + " .LogicalText{margin: 0px;" + Format.getJSString(rSpecFormats.getLogicalText()) + "}";

		return out;
	}

	@Override
	public String getJS() {
		return "";
	}

	@Override
	public boolean hasJS() {
		return false;
	}

	@Override
	public boolean hasHTML() {
		return true;
	}

	@Override
	public boolean hasCSS() {
		return true;
	}

}
