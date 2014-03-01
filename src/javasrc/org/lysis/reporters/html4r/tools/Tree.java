/*
 * This file is part of ReporteRs
 * Copyright (c) 2014, David Gohel All rights reserved.
 * This program is licensed under the GNU GENERAL PUBLIC LICENSE V3.
 * You may obtain a copy of the License at :
 * http://www.gnu.org/licenses/gpl.html
 */

package org.lysis.reporters.html4r.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.lysis.reporters.html4r.elements.Title;

public class Tree<T> {

  private T head;

  private ArrayList<Tree<T>> feuilles = new ArrayList<Tree<T>>();

  private Tree<T> parent = null;

  private HashMap<T, Tree<T>> locate = new HashMap<T, Tree<T>>();

  public Tree(T head) {
    this.head = head;
    locate.put(head, this);
  }

  public void addLeaf(T root, T leaf) {
    if (locate.containsKey(root)) {
      locate.get(root).addLeaf(leaf);
    } else {
      addLeaf(root).addLeaf(leaf);
    }
  }

  public Tree<T> addLeaf(T leaf) {
    Tree<T> t = new Tree<T>(leaf);
    feuilles.add(t);
    t.parent = this;
    t.locate = this.locate;
    locate.put(leaf, t);
    return t;
  }

  public Tree<T> setAsParent(T parentRoot) {
    Tree<T> t = new Tree<T>(parentRoot);
    t.feuilles.add(this);
    this.parent = t;
    t.locate = this.locate;
    t.locate.put(head, this);
    t.locate.put(parentRoot, t);
    return t;
  }

  public T getHead() {
    return head;
  }

  public Tree<T> getTree(T element) {
    return locate.get(element);
  }

  public Tree<T> getParent() {
    return parent;
  }

  public Collection<T> getSuccessors(T root) {
    Collection<T> successors = new ArrayList<T>();
    Tree<T> tree = getTree(root);
    if (null != tree) {
      for (Tree<T> leaf : tree.feuilles) {
        successors.add(leaf.head);
      }
    }
    return successors;
  }

  public Collection<Tree<T>> getLeafs() {
    return feuilles;
  }

  public static <T> Collection<T> getSuccessors(T of, Collection<Tree<T>> in) {
    for (Tree<T> tree : in) {
      if (tree.locate.containsKey(of)) {
        return tree.getSuccessors(of);
      }
    }
    return new ArrayList<T>();
  }

  @Override
  public String toString() {
    return printTree(0);
  }

  private static final int indent = 2;

  private String printTree(int increment) {
    String s = "";
    String inc = "";
    for (int i = 0; i < increment; ++i) {
      inc = inc + " ";
    }
    s = inc + head;
    for (Tree<T> child : feuilles) {
      s += "\n" + child.printTree(increment + indent);
    }
    return s;
  }


	public String Tree2HTML() {
		String out = "<ul class=\"nav nav-list\">";
		int lev = 0;
		if (feuilles.size() > 0) {
			for (Tree<T> child : feuilles) {
				out += child.render2HTML(lev);
			}
		}
		out += "</ul>";
		return out;
	}

	public String render2HTML(int level) {
		String out = "";
		
		Title temp = (Title) head;
		level++;

		if (feuilles.size() < 1){
			out += "<li>";
			out += "<a href=\"#" + temp.getUID() + "\">";
			out += temp.getValue();
			out += "</a>";
			out += "</li>";
		} else {
			out += "<li>";
			out += "<a href=\"#" + temp.getUID() + "\">";
			out += temp.getValue();
			out += "</a>";
			out += "<ul class=\"nav nav-list\">";
			for (Tree<T> child : feuilles) {
				out += child.render2HTML(level );
			}
			out += "</ul>";
			out += "</li>";
		}
		return out;
	}

}