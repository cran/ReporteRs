/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.rdata;

import java.util.Vector;

public class RList implements RObject {

	private Vector<RAtomicInterface> columnsList;
	private Vector<String> theNames;
	private int size;
	
	public RList(){
		columnsList = new Vector<RAtomicInterface>();
		theNames = new Vector<String>();
		size = 0;
	}
	
	public void append(RAtomicInterface itsSObject, String name){
		columnsList.add(itsSObject);
		theNames.add(name);
		size++;
	}
	public String names(int i){
		return (theNames.get(i));
	}

	public RAtomicInterface get(int i){
		return (RAtomicInterface)columnsList.elementAt(i);
	}
	public RAtomicInterface get(String name){
		int i = 0;
		int winner = -1;
		while(i < theNames.size()){
			if(names(i).equals(name)){
				winner = i;
			}
			i++;
		}
		if(winner!=-1){
			return(get(winner));
		}
		return null;
	}
	
	public int size(){
		return size;
	}

	public void print(){		
		for(int i = 0 ; i < size ; i ++){
			System.out.println( "{" + names(i) + "}" );
			get(i).print();
		}
	}

}
