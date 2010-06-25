package com.googlecode.simplex4android;

/**
 * Datenhaltungsklasse SimplexProblem zur Repräsentation des SimplexTableaus und der Zielfunktion.
 * @author Simplex4Android
 */
public class SimplexProblem {
	private double[][]tableau; //Simplex-Tableau inkl. Delta-Werte. Delta-Werte werden mit 0 initialisiert und dann ersetzt
	private int[] target; //Zielfunktion mit zusätzlicher 0, um den Zielwert berechnen zu können
	private int[] pivots; //Basisspalten
	private double[] xByF; //
	private boolean optimal;
	
	//SETTINGS!!!
	//normaler Simplex oder Dualer Simplex
	//automatische/manuelle Wahl der Pivotspalten
	
	
	/**
	 * Stellt ein SimplexTableau inklusive Zielfunktion zur Verfügung.
	 * @param tableau
	 * @param target
	 */
	public SimplexProblem(double[][] tableau, int[] target){ //int[] für die Zielfunktion?
		this.tableau = tableau;
		this.target = target;
		this.optimal = false;
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
		double[] r = new double[this.tableau.length];
		for(int a=0;a<this.tableau.length;a++){
			r[a]=this.tableau[a][j];
		}
		return r;
	}

	/**
	 * Gibt die letzte Spalte des Simplex-Tableaus aus.
	 * @return letzte Spalte des Simplex-Tableaus
	 */
	public double[] getLastColumn(){
		return getColumn(this.getNoColumns()-1);
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
	 * Gibt die letzte Zeile (delta-Werte) aus.
	 * @return letzte Zeile (delta-Werte)
	 */
	public double[] getLastRow(){
		return getRow(this.getNoRows()-1);
	}
	
	/**
	 * Setzt Zeile i.
	 * @param r übergebene Zeile
	 * @param i Index der zu ändernden Zeile
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

	/**
	 * Setzt die Pvotspaltentabelle.
	 * @return Pivotspalten
	 */
	public int[] getPivots() {
		return pivots;
	}

	/**
	 * Gibt die Pivotspaltentabelle aus.
	 * @param pivots zu setzende Pivotspalten
	 */
	public void setPivots(int[] pivots) {
		this.pivots = pivots;
	}

	/**
	 * Gibt ein Array mit den x/f-Werten für jede Zeile zurück.
	 * @return Array mit den x/f-Werten für jede Zeile
	 */
	public double[] getXByF() {
		return xByF;
	}

	/**
	 * Überschreibt die x/f-Werte.
	 * @param xByF neue x/f-Werte
	 */
	public void setXByF(double[] xByF) {
		this.xByF = xByF;
	}
	
	/**
	 * Gibt true, wenn Optimaltableau gefunden, sonst false.
	 * @return true, wenn Optimaltableau gefunden, sonst false.
	 */
	public boolean getOptimal(){
		return optimal;
	}
	
	/**
	 * Setzt das Tableau als Optimaltableau.
	 */
	public void setOptimal(){
		this.optimal = true;
	}
	
	/**
	 * Gibt eine Stringdarstellung der Zielfunktion zurück.
	 * @return Stringdarstellung der Zielfunktion.
	 */
	public String targetToString(){
		String re = "";
		re += this.target[0]+"x1";
		for(int i=1;i<this.target.length-2;i++){
			if(this.target[i]<0){
				re += " " +target[i] +"x" +(i+1);
			}else{
				re += " + " +target[i] +"x" +(i+1);
			}			
		}
		re += " = min \n";
		return re;
	}
	
	/**
	 * Gibt eine Stringdarstellung des SimplexTableaus zurück.
	 * @return Stringdarstellung des SimplexTableaus
	 */
	public String tableauToString(){
		String re ="";
		for(int i=0;i<this.tableau.length;i++){
			for(int j=0;j<this.tableau[0].length-1;j++){
				re += " " +this.tableau[i][j];
			}
			re += " | " +this.tableau[i][this.tableau[0].length-1] +"\n";
		}		
		return re;
	}
}
