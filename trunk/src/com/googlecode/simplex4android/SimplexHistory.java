package com.googlecode.simplex4android;

import java.util.ArrayList;

/**
 * @author Sebastian Hanschke
 *
 */
public class SimplexHistory {

	private ArrayList<SimplexTableau> history;
	
	public SimplexHistory(){
		this.history = new ArrayList<SimplexTableau>();
	}
	
	/**
	 * @return
	 */
	public SimplexTableau getLastElement(){
		return this.history.get(this.history.size()-1);
	}
	
	/**
	 * @return
	 */
	public SimplexTableau getFirstElement(){
		return this.history.get(0);
	}

	/**
	 * @return
	 */
	public SimplexTableau getElement(int index) throws java.lang.IndexOutOfBoundsException{
		return this.history.get(index);
	}
	
		/**
	 * @param tableau
	 */
	public void addElement(SimplexTableau tableau){
		this.history.add(tableau);
	}

}
