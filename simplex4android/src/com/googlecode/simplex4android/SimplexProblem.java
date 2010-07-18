package com.googlecode.simplex4android;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Datenhaltungsklasse SimplexProblem zur Repr�sentation von SimplexProblemen, Inhalt: SimplexTableau und Zielfunktion.
 * Inhalt: SimplexTableau, Zielfunktion, Pivotspalten und ein boolean, ob es optimal ist.
 * @author Simplex4Android
 */
@SuppressWarnings("serial")
public abstract class SimplexProblem implements Serializable{
	private ArrayList<ArrayList<Double>> tableau; 
	private ArrayList<Double> target; //Zielfunktion mit zus�tzlicher 0, um den Zielwert im Tableau berechnen zu k�nnen
	private int[] pivots;//Basisspalten
	private boolean optimal;

	//SETTINGS!!!
	//normaler Simplex oder Dualer Simplex
	//automatische/manuelle Wahl der Pivotspalten

	/**
	 * Der leere Konstruktor sollte nur zum klonen eins Objektes benutzt werden!!!
	 * Standardkonstruktor f�r ein leeres SimplexProblem zum anschlie�enden Hinzuf�gen der Zielfunktion und Nebenbedingungen.
	 * Die Zeile der delta-Werte ist bereits enthalten.
	 */
	public SimplexProblem(){
		this.tableau = new ArrayList<ArrayList<Double>>();
		this.tableau.add(new ArrayList<Double>()); // Zeile der delta-Werte hinzuf�gen
		this.target = new ArrayList<Double>();
		this.optimal = false;
		pivots = new int[this.getNoRows()-1];
		//		this.initializePivotsWithMinusOne();

	}

	/**
	 * Konstruktor, der eine ArrayList mit Input-Objekten �bergeben bekommt. 
	 * An erster Stellte muss dabei stehts die Zielfunktion vom Typ Target stehen.
	 * @param input ArrayList mit Input-Objekten (Index 0 muss ein Target-Objekt enthalten)
	 */
	public SimplexProblem(ArrayList<Input> input){
		// Zielfunktion auslesen und anlegen
		Target t = (Target) input.get(0);
		this.target = t.getClonedValues();
		this.target.add(new Double(0));
		if(!t.getMinOrMax()){
			for(int i=0;i<this.target.size()-1;i++){
				this.target.set(i, new Double(this.target.get(i).doubleValue()*(-1)));
			}
		}	
		// Tableau anlegen und mit Zeilen f�llen
		this.tableau = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> deltas = new ArrayList<Double>();
		
		//Herausfinden ob die Zielfunktion oder eine Nebenbedingung l�nger ist
		int longestRow = -1; //-1: Target ist mit am l�ngsten, 0-n Nr der l�ngsten Row (row i ist l�nger als Target)
		int lengthLR = target.size(); //L�nger der l�ngsten Zeile
		for(int i=0;i<input.size();i++){
			if(input.get(i).getValues().size()+1>lengthLR){
				longestRow = i;
				lengthLR = input.get(i).getValues().size()+1;
			}
		}
		if(longestRow!=-1){
			for(int i=target.size()-1;0<lengthLR-target.size();){
				target.add(i,new Double(0)); // target
			}	
		}
		for(int i=0;i<lengthLR;i++){  
			deltas.add(new Double(0)); // delta-Zeile anlegen
		}
		this.tableau.add(deltas);
		for(int i=1; i<input.size();i++){
			Constraint c = (Constraint) input.get(i); // Nebenbedingungen anlegen
			ArrayList<Double> row = new ArrayList<Double>();
			row = c.getClonedValues();
			row.add(new Double(c.getSign()));
			row.add(new Double(c.getTargetValue()));
			this.addRow(row);
		}
		this.optimal = false;
		pivots = new int[this.getNoRows()-1];
	}

	/**
	 * Stellt ein SimplexTableau inklusive Zielfunktion zur Verf�gung.
	 * @param tableau
	 * @param target
	 */
	public SimplexProblem(double[][] tableau, double[] target){ 
		this.tableau = this.convertTo2DArrayList(tableau);
		this.target = this.convertToDblArrayList(target);		
		this.optimal = false;
		pivots = new int[this.getNoRows()-1];
	}

	/**
	 * F�gt eine weitere Pivospalte (z.B. als k�nstliche oder Schlupfvariable) an vorletzter Stelle des Tableaus ein. 
	 * Die Eins befindet sich in der Zeile mit Index c, die neue Variable wird mit Kosten 1 in der Zielfunktion hinzugef�gt.
	 * @param row Index der Zeile, f�r die Eins der neuen Pivotspalte
	 */
	public void addArtificialVar(int row){
		//		System.out.println(row);
		// Pivotspalte erg�nzen
		for(int i=0;i<this.tableau.size();i++){
			if(i==row){
				this.tableau.get(row).add(this.tableau.get(row).size()-1,new Double(1));
			}else{
				this.tableau.get(i).add(this.tableau.get(i).size()-1,new Double(0));
			}

		}
		// Einf�gen der neuen Variable in die Zielfunktion inkl. Verschiebung des Zielwerts
		this.target.add(this.target.size()-1,new Double(1));
		// Einf�gen der neuen Pivotspalte in die Basis
		this.pivots[row] = this.target.size()-2;
		//this.pivots.add(row,new Integer(this.target.size()-2));	
	}

	/**
	 * F�gt dem SimplexProblem eine neue Zeile beliebiger L�nge an vorletzter Stelle hinzu (in der letzten Zeile befinden sich stehts die delta-Werte.
	 * Je nach L�nge werden in den bereits vorhandenen Zeilen Nullen erg�nzt und wenn n�tig Schlupfvariablen erg�nzt.
	 * @param r neu einzuf�genden Zeile, der Faktor der Variablen xi steht an Stelle x(i-1) des Arrays, an vorletzter Stelle der Vergleichsoperator ("-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">=")) und an letzter Stelle der Zielwert
	 */
	public void addRow(ArrayList<Double> row){
		ArrayList<Double> newRow = new ArrayList<Double>();
		// Alle Variablen vor dem Vergleichsoperator einf�gen
		for(int i=0;i<row.size()-2;i++){
			newRow.add(i, new Double(row.get(i)).doubleValue());
		}
		// Auff�llen der restlichen Variablen (fehlend zur Zielfunktion) mit 0
		for(int i=0;i<(this.getRow(0).length-(row.size()-1));i++){
			newRow.add(new Double(0));
		}
		// evtl. Einf�gen der Schlupfvariable inkl. Auff�llen von Nullen in anderen Zeilen
		int sign = row.get(row.size()-2).intValue();
		if(sign!=0){
			if(sign==1){
				newRow.add(new Double(-1));
			}else{
				newRow.add(new Double(1));
			}
			this.target.add(this.target.size()-1, new Double(0)); // Einf�gen in Zielfunktion
			for(int i=0;i<this.getNoRows();i++){ // Einf�gen der Nullen in anderen Zeilen
				this.tableau.get(i).add(this.tableau.get(i).size()-1, new Double(0));
			}
		}
		// Zielwert anf�gen
		newRow.add(row.get(row.size()-1));
		// Einf�gen der Zeile an vorletzter Stelle ins Tableau (deltaWerte immer unten)
		this.tableau.add(this.tableau.size()-1, newRow);
	}

	/**
	 * Abstrakte Methode, um ein SimplexProblem-Objekt zu klonen und somit eine History abbilden zu k�nnen.
	 * @return geklontes SimplexProblem
	 */
	public abstract SimplexProblem clone();

	public int[] clonePivots(){
		int[]tmp = new int[pivots.length];
		for (int i=0;i<pivots.length;i++){
			tmp[i] = pivots[i];
		}
		return tmp;
	}

	/**
	 * �berf�hrt das �bergebene zweidimensionale Array in ein ArrayList<ArrayList<Double>>.
	 * @param array zu �berf�hrendes zweidimensionales Array
	 * @return �berf�hrte ArrayList
	 */
	protected ArrayList<ArrayList<Double>> convertTo2DArrayList(double[][] array){
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
	 * �berf�hrt die �bergebene ArrayList<Double> in ein double[].
	 * @param arrayList zu �berf�hrende ArrayList
	 * @return �berf�hrtes Array
	 */
	protected double[] convertToDblArray(ArrayList<Double> arrayList){
		double[] array = new double[arrayList.size()];
		for(int i=0;i<array.length;i++){
			array[i] = arrayList.get(i).doubleValue();
		}
		return array;
	}

	/**
	 * �berf�hrt das �bergebene Array in eine ArrayList<Double>.
	 * @param array zu �berf�hrendes Array
	 * @return �berf�hrte ArrayList
	 */
	protected ArrayList<Double> convertToDblArrayList(double[] array){
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
	protected int[] convertToIntArray(ArrayList<Integer> arrayList){
		int[] array = new int[arrayList.size()];
		for(int i=0;i<array.length;i++){
			array[i] = arrayList.get(i).intValue();
		}
		return array;
	}

	/**
	 * �berf�hrt das �bergebene Array in eine ArrayList<Integer>.
	 * @param array zu �berf�hrendes Array
	 * @return �berf�hrte ArrayList
	 */
	protected ArrayList<Integer> convertToIntArrayList(int[] array){
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for(int i=0;i<array.length;i++){
			arrayList.add(i,new Integer(array[i]));
		}
		return arrayList;
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
	 * Gibt den Inhalt den Feldes in (zeile,spalte) aus.
	 * @param i Index der Zeile im SimplexTableau
	 * @param j Index der Spalte im SimplexTableau
	 * @return Inhalt des Feldes in (zeile,spalte)
	 */
	public double getField(int i, int j){
		return this.tableau.get(i).get(j).doubleValue();
	}

	/**
	 * Gibt die letzte Spalte des Simplex-Tableaus aus.
	 * @return letzte Spalte des Simplex-Tableaus
	 */
	public double[] getLastColumn(){
		return getColumn(this.getNoColumns()-1);
	}

	/**
	 * Gibt die letzte Zeile (delta-Werte) aus.
	 * @return letzte Zeile (delta-Werte)
	 */
	public double[] getLastRow(){
		return getRow(this.getNoRows()-1);
	}

	/**
	 * Gibt die Anzahl der Spalten aus.
	 * @return Anzahl der Spalten
	 */
	public int getNoColumns(){
		return this.tableau.get(0).size();
	}

	/**
	 * Gibt die Anzahl der gespeicherten Pivotspalten zur�ck (Es kann mehr geben,
	 * es werden aber nur so viele gefunden wie ben�tigt werden)
	 * @return Anzahl Pivotspalten
	 */
	public int getNoPivots(){
		int noPivots = 0;
		for(int i=0;i<this.pivots.length;i++){
			if(this.pivots[i] != -1)noPivots++;
		}
		return noPivots;
	}

	/**
	 * Gibt die Anzahl der Zeilen aus.
	 * @return Anzahl der Zeilen
	 */
	public int getNoRows(){
		return this.tableau.size();
	}

	/**
	 * Gibt true, wenn Optimaltableau gefunden, sonst false.
	 * @return true, wenn Optimaltableau gefunden, sonst false.
	 */
	public boolean getOptimal(){
		return optimal;
	}

	/**
	 * Gibt die Pivotspaltentabelle (als Indizes) zur�ck.
	 * @return Indizes der Pivotspalten
	 */
	public int[] getPivots() {
		return this.pivots;
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
	 * Gibt die L�sung des SimlexProblems zur�ck, sofern dieses bereits optimal ist.
	 * @return L�sungsstring, Leerstring falls nicht optimal.
	 */
	public String getSolution(){
		String solution = "";
		if(this.getOptimal()){
			double[] xSolutions = new double[this.getNoColumns()-1];
			int[] pivots = this.getPivots();
			// L�sungen einspeichern
			for(int i=0; i<pivots.length;i++){
				xSolutions[pivots[i]] = this.getField(i, this.getNoColumns()-1);
			}
			// Ausgabestring erstellen
			for(int i=0; i<xSolutions.length;i++){
				if(!(xSolutions[i]==0)){
					if(solution.equals("")){
						solution += "x" +(i+1) + " = " + String.valueOf(Math.round(xSolutions[i]*10000.)/10000.);
					}
					else{
						solution += ", x" +(i+1) + " = " + String.valueOf(Math.round(xSolutions[i]*10000.)/10000.);	
					}		
				}
			}
		}
		return solution;
	}

	/**
	 * Gibt das SimplexTableau aus.
	 * @return SimplexTableau
	 */
	public double[][] getTableau() {
		double[][] tableau = new double[this.tableau.size()][this.tableau.get(0).size()];
		for(int i=0;i<tableau.length;i++){
			for(int j=0;j<tableau[0].length;j++){
				tableau[i][j] = this.getField(i, j);
			}
		}
		return tableau;
	}

	/**
	 * Gibt die Zielfunktion zur�ck.
	 * @return Zielfunktion
	 */
	public double[] getTarget() {
		return this.convertToDblArray(this.target);
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
	 * Setzt den Inhalt in Feld (zeile, spalte) auf den �bergebenen double-Wert.
	 * @param i Index der Zeile im SimplexTableau
	 * @param j Index der Spalte im SimplexTableau
	 * @param value �bergebener Wert
	 */
	public void setField(int i, int j, double value){
		this.tableau.get(i).set(j, new Double(value));
	}

	/**
	 * Setzt das Tableau als Optimaltableau.
	 */
	public void setOptimal(){
		this.optimal = true;
	}

	/**
	 * Speichert die Pivotspaltentabelle.
	 * @param pivots Indizes der zu setzendenden Pivotspalten
	 */
	public void setPivots(int[] pivots) {
		this.pivots = pivots;
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
	 * setzt an Stelle row in pivots den Wert column.
	 * Achtung: die Indizes fangen bei null an! 
	 * @param row Zeile des Tableaus in der sich die Pivotspalte �ndert
	 * @param column Spalte der neuen Pivotspalte
	 */
	public void setSinglePivot(int row, int column) {
		this.pivots[row] = column;
	}

	/**
	 * Setzt das SimplexTableau und berechnet anschlie�end die Pivotwerte neu.
	 * @param tableau �bergebenes SimplexTableau
	 */
	public void setTableau(ArrayList<ArrayList<Double>> tableau) {
		this.tableau = tableau;
		SimplexLogic.findPivots(this);
	}

	/**
	 * Setzt das SimplexTableau und berechnet anschlie�end die Pivotwerte neu.
	 * @param tableau �bergebenes SimplexTableau
	 */
	public void setTableau(double[][] tableau) {
		this.tableau = this.convertTo2DArrayList(tableau);
		SimplexLogic.findPivots(this);
	}

	/**
	 * Setzt die Zielfunktion.
	 * @param target �bergebene Zielfunktion
	 */
	public void setTarget(ArrayList<Double> target) {
		this.target = target;
	}

	/**
	 * Setzt die Zielfunktion.
	 * @param target �bergebene Zielfunktion
	 */
	public void setTarget(double[] target) {
		this.target = this.convertToDblArrayList(target);
	}

	/**
	 * Abstrakte Methode, um das Tableau in HTML darzustellen.
	 * @return String mit HTML-Code als Inhalt f�r eine Tabelle
	 */
	public abstract String tableauToHtml();	

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
	 * Gibt eine Stringdarstellung der Zielfunktion zur�ck.
	 * @return Stringdarstellung der Zielfunktion.
	 */
	public String targetToString(){
		String re = "";
		re += this.target.get(0).intValue() +"x1";
		for(int i=1;i<this.target.size()-1;i++){
			if(this.target.get(i)<0){
				re += " " +this.target.get(i).intValue() +"x" +(i+1);
			}else{
				re += " + " +this.target.get(i).intValue() +"x" +(i+1);
			}			
		}
		re += " = min \n";
		return re;
	}
}
