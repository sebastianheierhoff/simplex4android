package com.googlecode.simplex4android;

import java.util.ArrayList;

/**
 * @author Sebastian Hanschke
 *
 */
public class SimplexHistory {

	private ArrayList<SimplexProblem> history = new ArrayList<SimplexProblem>();
	
	public SimplexHistory(){
		
	}
	
	public SimplexHistory(double[][] tableau, int[] target){
		SimplexProblem firstProblem = new SimplexProblem(tableau, target);
		history.add(firstProblem);
	}
	
	/**
	 * @return
	 */
	public SimplexProblem getLastElement(){
		return this.history.get(this.history.size()-1);
	}
	
	/**
	 * @return
	 */
	public SimplexProblem getFirstElement(){
		return this.history.get(0);
	}

	/**
	 * @return
	 */
	public SimplexProblem getElement(int index) throws java.lang.IndexOutOfBoundsException{
		return this.history.get(index);
	}
	
		/**
	 * @param tableau
	 */
	public void addElement(SimplexProblem tableau){
		this.history.add(tableau);
	}

}
