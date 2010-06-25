package com.googlecode.simplex4android;

import java.util.ArrayList;

/**
 * Klasse SimplexHistory - speichert SimplexProbleme.
 * @author
 */
public class SimplexHistory {

	/**
	 * History zur Verwaltung von SimplexPrioblemen.
	 */
	private ArrayList<SimplexProblem> history = new ArrayList<SimplexProblem>();
	
	/**
	 * Leerer Konstruktor zum Anlegen einer SimplexHistory.
	 */
	public SimplexHistory(){
		
	}
	
	/**
	 * Legt eine SimplexHistory zur Verwaltung von SimplexProblemen.
	 * @param tableau Start-SimplexProblem
	 * @param target Zielfunktion
	 */
	public SimplexHistory(double[][] tableau, int[] target){
		SimplexProblem firstProblem = new SimplexProblem(tableau, target);
		history.add(firstProblem);
	}
	
	/**
	 * Gibt das letzte Element der SimplexHistory zurück.
	 * @return letztes Element der SimplexHistory
	 */
	public SimplexProblem getLastElement(){
		return this.history.get(this.history.size()-1);
	}
	
	/**
	 * Gibt das erste Element der SimplexHistory zurück.
	 * @return erstes Element der SimplexHistory
	 */
	public SimplexProblem getFirstElement(){
		return this.history.get(0);
	}

	/**
	 * Gibt das Element mit dem Index index zurück.
	 * @param index Index des auszugebenen Elements
	 * @return Element mit Index index
	 * @throws java.lang.IndexOutOfBoundsException
	 */
	public SimplexProblem getElement(int index) throws java.lang.IndexOutOfBoundsException{
		return this.history.get(index);
	}
	
	/**
	 * Fügt der SimplexHistory ein neues Element hinzu.
	 * @param tableau einzufügendes SimplexProblem
	 */
	public void addElement(SimplexProblem tableau){
		this.history.add(tableau);
	}

}
