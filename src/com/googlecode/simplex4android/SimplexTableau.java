package com.googlecode.simplex4android;

/**
 * Datenhaltungsklasse SimplexTableau zur Repr�sentation des SimplexTableaus und der Zielfunktion.
 * @author Max Wortmann
 */
public class SimplexTableau {

	
	private double[][]tableau;
	private int[] target;
	
	/**
	 * Stellt ein SimplexTableau inklusive Zielfunktion zur Verf�gung.
	 * @param tableau
	 * @param target
	 */
	public SimplexTableau(double[][] tableau, int[] target){ //int[] f�r die Zielfunktion?
		this.tableau = tableau;
		this.target = target;
	}
	
	/**
	 * Gibt den Inhalt den Feldes in (zeile,spalte) aus.
	 * @param zeile Zeile im SimplexTableau
	 * @param spalte Spalte im SimplexTableau
	 * @return Inhalt des Feldes in (zeile,spalte)
	 */
	public double getField(int zeile, int spalte){
		return tableau[zeile][spalte];
	}
	
	/**
	 * Setzt den Inhalt in Feld (zeile, spalte) auf den �bergebenen double-Wert.
	 * @param zeile Zeile im SimplexTableau
	 * @param spalte Spalte im SimplexTableau
	 * @param value �bergebener Wert
	 */
	public void setField(int zeile, int spalte, double value){
		tableau[zeile][spalte]=value;
	}
	
	/**
	 * Gibt das SimplexTableau aus.
	 * @return SimplexTableau
	 */
	public double[][] getTableau() {
		return tableau;
	}
	
	/**
	 * Setzt das SimplexTableau.
	 * @param tableau �bergebenes SimplexTableau
	 */
	public void setTableau(double[][] tableau) {
		this.tableau = tableau;
	}

	/**
	 * Gibt die Zielfunktion zur�ck.
	 * @return Zielfunktion
	 */
	public int[] getTarget() {
		return target;
	}

	/**
	 * Setzt die Zielfunktion.
	 * @param target �bergebene Zielfunktion
	 */
	public void setTarget(int[] target) {
		this.target = target;
	}

	/**
	 * Gibt die Anzahl der Spalten aus.
	 * @return Anzahl der Spalten
	 */
	public int getNoColumns(){
		return this.tableau[0].length;
	}
	
	/**
	 * Gibt die Anzahl der Zeilen aus.
	 * @return Anzahl der Zeilen
	 */
	public int getNoRows(){
		return this.tableau.length;
	}
}
