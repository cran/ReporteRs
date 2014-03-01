/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.rdata;

import java.util.LinkedHashMap;

import org.docx4j.dml.CTTableCellProperties;
import org.docx4j.dml.CTTextBody;
import org.docx4j.wml.P;
import org.docx4j.wml.TcPr;

public interface RAtomicInterface {
	public void print();
	public int size();
	public P getP(int i, org.lysis.reporters.tables.TableLayoutDOCX tf);
	public P getP(int i, org.lysis.reporters.tables.TableLayoutDOCX tf, String fontColor);
	public CTTextBody getP(int i, org.lysis.reporters.tables.TableLayoutPPTX tf) throws Exception;
	public CTTextBody getP(int i, org.lysis.reporters.tables.TableLayoutPPTX tf, String fontColor) throws Exception;
	public TcPr getCellProperties( org.lysis.reporters.tables.TableLayoutDOCX tf );
	public CTTableCellProperties getCellProperties( org.lysis.reporters.tables.TableLayoutPPTX tf );
	public LinkedHashMap<String, String> getCellProperties( org.lysis.reporters.tables.TableLayoutHTML tf );
	public Object get(int i);
	public String getTdCssClass();
	public String getHTML(int i, org.lysis.reporters.tables.TableLayoutHTML tf) ;
	public String getHTML(int i, String fontColor, org.lysis.reporters.tables.TableLayoutHTML tf) ;
}
