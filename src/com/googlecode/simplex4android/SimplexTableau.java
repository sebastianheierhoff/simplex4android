package com.googlecode.simplex4android;

/**
 * Datenhaltungsklasse SimplexTableau zur Repräsentation des SimplexTableaus und der Zielfunktion.
 * @author Max Wortmann
 */
public class SimplexTableau {

	
	private double[][]tableau;
	private int[] target;
	
	/**
	 * Stellt ein SimplexTableau inklusive Zielfunktion zur Verfügung.
	 * @param tableau
	 * @param target
	 */
	public SimplexTableau(double[][] tableau, int[] target){ //int[] für die Zielfunktion?
		this.tableau = tableau;
		this.target = target;
	}
	
	/**
	 * Erstellt ein Standardtableau
	 */
	public SimplexTableau() {
		this.tableau = new double[7][3];
		this.target = new int[3];
	}

	/**
	 * Gibt den Inhalt den Feldes in (zeile,spalte) aus.
	 * @param i Index der Zeile im SimplexTableau
	 * @param j Index der Spalte im SimplexTableau
	 * @return Inhalt des Feldes in (zeile,spalte)
	 */
	public double getField(int i, int j){
		return tableau[i][j];
	}
	
	/**
	 * Setzt den Inhalt in Feld (zeile, spalte) auf den übergebenen double-Wert.
	 * @param i Index der Zeile im SimplexTableau
	 * @param j Index der Spalte im SimplexTableau
	 * @param value übergebener Wert
	 */
	public void setField(int i, int j, double value){
		tableau[i][j]=value;
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
	 * @param tableau übergebenes SimplexTableau
	 */
	public void setTableau(double[][] tableau) {
		this.tableau = tableau;
	}

	/**
	 * Gibt die Zielfunktion zurück.
	 * @return Zielfunktion
	 */
	public int[] getTarget() {
		return target;
	}

	/**
	 * Setzt die Zielfunktion.
	 * @param target übergebene Zielfunktion
	 */
	public void setTarget(int[] target) {
		this.target = target;
	}
	
	/**
	 * Gibt Spalte j aus.
	 * @param j Index der auszugebenen Spalte
	 * @return Spalte j
	 */
	public double[] getColumn(int j){
		return this.tableau[j];
	}
	
	/**
	 * Setzt Spalte j.
	 * @param c übergebene Spalte
	 * @param j Index der zu verändernden Spalte
	 */
	public void setColumn(double[] c, int j){
		this.tableau[j]=c;
	}
	
	/**
	 * Gibt Zeile i aus.
	 * @param i Index der auszugebenen Zeile
	 * @return Zeile i
	 */
	public double[] getRow(int i){
		double[] r = new double[this.tableau[0].length];
		for(int a=0;a<this.tableau[0].length;a++){
			r[a]=this.tableau[i][a];
		}
		return r;
	}
	
	/**
	 * Setzt Zeile i.
	 * @param r übergebene Zeile
	 * @param i Index der zu überändernden Zeile
	 */
	public void setRow(double[] r, int i){
		for(int a=0;a<this.tableau[0].length;a++){
			this.tableau[i][a]=r[a];
		}
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
