package com.googlecode.simplex4android;

import java.util.ArrayList;

/**
 * Datenhaltungsklasse SimplexProblem zur Repr�sentation des SimplexTableaus und der Zielfunktion.
 * 
 * Das Tableau wird durch eine ArrayList bestehend aus ArrayLists (Zeilen) gef�llt mit DoubleObjekten repr�sentiert.
 * @author Simplex4Android
 */
public class SimplexProblem {
	private ArrayList<ArrayList<Double>> tableau; 
	private ArrayList<Integer> target; //Zielfunktion mit zus�tzlicher 0, um den Zielwert berechnen zu k�nnen
	private ArrayList<Integer> pivots; //Basisspalten
	private ArrayList<Double> xByF; //
	private boolean optimal;
	
	//SETTINGS!!!
	//normaler Simplex oder Dualer Simplex
	//automatische/manuelle Wahl der Pivotspalten
	
	
	/**
	 * Stellt ein SimplexTableau inklusive Zielfunktion zur Verf�gung.
	 * @param tableau
	 * @param target
	 */
	public SimplexProblem(double[][] tableau, int[] target){ 
		this.tableau = this.convertTo2DArrayList(tableau);
		this.target = this.convertToIntArrayList(target);		
		this.optimal = false;
	}
	
	/**
	 * Gibt den Inhalt den Feldes in (zeile,spalte) aus.
	 * @param i Index der Zeile im SimplexTableau
	 * @param j Index der Spalte im SimplexTableau
	 * @return Inhalt des Feldes in (zeile,spalte)
	 */
	public double getField(int i, int j){
		return this.tableau.get(i).get(j).doubleValue();
	}
	
	/**
	 * Setzt den Inhalt in Feld (zeile, spalte) auf den �bergebenen double-Wert.
	 * @param i Index der Zeile im SimplexTableau
	 * @param j Index der Spalte im SimplexTableau
	 * @param value �bergebener Wert
	 */
	public void setField(int i, int j, double value){
		this.tableau.get(i).set(j, new Double(value));
	}
	
	/**
	 * Gibt das SimplexTableau aus.
	 * @return SimplexTableau
	 */
	public double[][] getTableau() {
		double[][] tableau = new double[this.tableau.size()][this.tableau.get(0).size()];
		for(int i=0;i<tableau.length;i++){
			for(int j=0;j<tableau.length;j++){
				tableau[i][j] = this.getField(i, j);
			}
		}
		return tableau;
	}
	
	/**
	 * Setzt das SimplexTableau.
	 * @param tableau �bergebenes SimplexTableau
	 */
	public void setTableau(double[][] tableau) {
		this.tableau = this.convertTo2DArrayList(tableau);
	}

	/**
	 * Gibt die Zielfunktion zur�ck.
	 * @return Zielfunktion
	 */
	public int[] getTarget() {
		return this.convertToIntArray(this.target);
	}

	/**
	 * Setzt die Zielfunktion.
	 * @param target �bergebene Zielfunktion
	 */
	public void setTarget(int[] target) {
		this.target = this.convertToIntArrayList(target);
	}
	
	/**
	 * Gibt Spalte j aus.
	 * @param j Index der auszugebenen Spalte
	 * @return Spalte j
	 */
	public double[] getColumn(int j){
		double[] r = new double[this.tableau.size()];
		for(int i=0;i<r.length;i++){
			r[i] = this.tableau.get(i).get(j).doubleValue();
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
	 * @param c �bergebene Spalte
	 * @param j Index der zu ver�ndernden Spalte
	 */
	public void setColumn(double[] c, int j){
		for(int i=0;i<c.length;i++){
			this.tableau.get(i).set(j, new Double(c[i]));
		}
	}
	
	/**
	 * Gibt Zeile i aus.
	 * @param i Index der auszugebenen Zeile
	 * @return Zeile i
	 */
	public double[] getRow(int i){
		return this.convertToDblArray(this.tableau.get(i));
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
	 * @param r �bergebene Zeile
	 * @param i Index der zu �ndernden Zeile
	 */
	public void setRow(double[] r, int i){
		for(int a=0;a<r.length;a++){
			this.tableau.get(i).set(a,new Double(r[a]));
		}
	}
	
	/**
	 * Gibt die Anzahl der Spalten aus.
	 * @return Anzahl der Spalten
	 */
	public int getNoColumns(){
		return this.tableau.get(0).size();
	}
	
	/**
	 * Gibt die Anzahl der Zeilen aus.
	 * @return Anzahl der Zeilen
	 */
	public int getNoRows(){
		return this.tableau.size();
	}

	/**
	 * Setzt die Pvotspaltentabelle.
	 * @return Pivotspalten
	 */
	public int[] getPivots() {
		return this.convertToIntArray(this.pivots);
	}

	/**
	 * Gibt die Pivotspaltentabelle aus.
	 * @param pivots zu setzende Pivotspalten
	 */
	public void setPivots(int[] pivots) {
		this.pivots = this.convertToIntArrayList(pivots);
	}

	/**
	 * Gibt ein Array mit den x/f-Werten f�r jede Zeile zur�ck.
	 * @return Array mit den x/f-Werten f�r jede Zeile
	 */
	public double[] getXByF() {
		return this.convertToDblArray(this.xByF);
	}

	/**
	 * �berschreibt die x/f-Werte.
	 * @param xByF neue x/f-Werte
	 */
	public void setXByF(double[] xByF) {
		this.xByF = this.convertToDblArrayList(xByF);
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
	 * Gibt eine Stringdarstellung der Zielfunktion zur�ck.
	 * @return Stringdarstellung der Zielfunktion.
	 */
	public String targetToString(){
		String re = "";
		re += this.target.get(0).intValue() +"x1";
		for(int i=1;i<this.target.size()-2;i++){
			if(this.target.get(i)<0){
				re += " " +this.target.get(i).intValue() +"x" +(i+1);
			}else{
				re += " + " +this.target.get(i).intValue() +"x" +(i+1);
			}			
		}
		re += " = min \n";
		return re;
	}
	
	/**
	 * Gibt eine Stringdarstellung des SimplexTableaus zur�ck.
	 * @return Stringdarstellung des SimplexTableaus
	 */
	public String tableauToString(){
		String re ="";
		for(int i=0;i<this.tableau.size();i++){
			for(int j=0;j<this.tableau.get(0).size()-1;j++){
				re += " " +this.tableau.get(i).get(j).doubleValue();
			}
			re += " | " +this.tableau.get(i).get(this.tableau.get(0).size()-1) +"\n";
		}		
		return re;
	}
	
	/**
	 * F�gt eine weitere Pivospalte (z.B. als k�nstliche oder Schlupfvariable) an vorletzter Stelle des Tableaus ein. 
	 * Die Eins befindet sich in der Zeile mit Index c, die neue Variable wird mit Kosten Null in der Zielfunktion hinzugef�gt.
	 * @param c Index der Zeile, f�r die Eins der neuen Pivotspalte
	 */
	public void addPivotColumn(int c){
		// Pivotspalte erg�nzen
		for(int i=0;i<this.tableau.size();i++){
			if(i==c){
				this.tableau.get(i).add(this.tableau.size()-2,new Double(1));
			}else{
				this.tableau.get(i).add(this.tableau.size()-2,new Double(0));
			}
			
		}
		
		// Einf�gen der neuen Variable in die Zielfunktion inkl. Verschiebung des Zielwerts
		this.target.add(c,new Integer(0));
		
		// Einf�gen der neuen Pivotspalte in die Basis
		this.pivots.add(c,new Integer(this.target.size()-2));	
	}
	
	/**
	 * �berf�hrt das �bergebene zweidimensionale Array in ein ArrayList<ArrayList<Double>>.
	 * @param array zu �berf�hrendes zweidimensionales Array
	 * @return �berf�hrte ArrayList
	 */
	private ArrayList<ArrayList<Double>> convertTo2DArrayList(double[][] array){
		ArrayList<ArrayList<Double>> tableau = new ArrayList<ArrayList<Double>>();
		for(int i=0;i<array.length;i++){
			tableau.add(i,new ArrayList<Double>());
			for(int j=0;j<array[0].length;j++){
				tableau.get(i).add(j,new Double(array[i][j]));
			}
		}
		return tableau;
	}
	
	/**
	 * �berf�hrt das �bergebene Array in eine ArrayList<Integer>.
	 * @param array zu �berf�hrendes Array
	 * @return �berf�hrte ArrayList
	 */
	private ArrayList<Integer> convertToIntArrayList(int[] array){
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for(int i=0;i<array.length;i++){
			arrayList.add(i,new Integer(array[i]));
		}
		return arrayList;
	}
	
	/**
	 * �berf�hrt das �bergebene Array in eine ArrayList<Double>.
	 * @param array zu �berf�hrendes Array
	 * @return �berf�hrte ArrayList
	 */
	private ArrayList<Double> convertToDblArrayList(double[] array){
		ArrayList<Double> arrayList = new ArrayList<Double>();
		for(int i=0;i<array.length;i++){
			arrayList.add(i,new Double(array[i]));
		}
		return arrayList;
	}
	
	/**
	 * �berf�hrt die �bergebene ArrayList<Integer> in ein int[].
	 * @param arrayList zu �berf�hrende ArrayList
	 * @return �berf�hrtes Array
	 */
	private int[] convertToIntArray(ArrayList<Integer> arrayList){
		int[] array = new int[arrayList.size()];
		for(int i=0;i<array.length;i++){
			array[i] = arrayList.get(i).intValue();
		}
		return array;
	}
	
	/**
	 * �berf�hrt die �bergebene ArrayList<Double> in ein double[].
	 * @param arrayList zu �berf�hrende ArrayList
	 * @return �berf�hrtes Array
	 */
	private double[] convertToDblArray(ArrayList<Double> arrayList){
		double[] array = new double[arrayList.size()];
		for(int i=0;i<array.length;i++){
			array[i] = arrayList.get(i).doubleValue();
		}
		return array;
	}
}
