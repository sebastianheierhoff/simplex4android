package com.googlecode.simplex4android;

import java.util.ArrayList;

/**
 * Datenhaltungsklasse SimplexProblem zur Repräsentation des SimplexTableaus und der Zielfunktion.
 * 
 * Das Tableau wird durch eine ArrayList bestehend aus ArrayLists (Zeilen) gefüllt mit DoubleObjekten repräsentiert.
 * @author Simplex4Android
 */
public class SimplexProblem {
	private ArrayList<ArrayList<Double>> tableau; 
	private ArrayList<Integer> target; //Zielfunktion mit zusätzlicher 0, um den Zielwert berechnen zu können
	private ArrayList<Integer> pivots; //Basisspalten
	private ArrayList<Double> xByF; //
	private ArrayList<Double> deltaByF; //Zeile unter dem Tableau für den dualen Simplex
	private boolean optimal;
	
	//SETTINGS!!!
	//normaler Simplex oder Dualer Simplex
	//automatische/manuelle Wahl der Pivotspalten
	
	/**
	 * Standardkonstruktor für ein leeres SimplexProblem zum anschließenden Hinzufügen der Zielfunktion und Nebenbedingungen.
	 * Die Zeile der delta-Werte ist bereits enthalten.
	 */
	public SimplexProblem(){
		this.tableau = new ArrayList<ArrayList<Double>>();
		this.tableau.add(new ArrayList<Double>()); // Zeile der delta-Werte hinzufügen
		this.target = new ArrayList<Integer>();
		this.pivots = new ArrayList<Integer>();
		this.xByF = new ArrayList<Double>();
		this.deltaByF = new ArrayList<Double>();
		this.optimal = false;
	}
	
	/**
	 * Stellt ein SimplexTableau inklusive Zielfunktion zur Verfügung.
	 * @param tableau
	 * @param target
	 */
	public SimplexProblem(double[][] tableau, int[] target){ 
		this.tableau = this.convertTo2DArrayList(tableau);
		this.target = this.convertToIntArrayList(target);		
		this.optimal = false;

		SimplexLogic.findPivots(this);
	    SimplexLogic.calcDeltas(this);
	    SimplexLogic.calcXByF(this);
	}
	
	/**
	 * Fügt dem SimplexProblem eine neue Zeile beliebiger Länge an vorletzter Stelle hinzu (in der letzten Zeile befinden sich stehts die Zeile der delta-Werte.
	 * Je nach Länge werden in den bereits vorhandenen Zeilen Nullen ergänzt.
	 * @param r neu einzufügenden Zeile, der Faktor der Variablen xi steht an Stelle x(i-1) des Arrays, an letzter Stelle der Zielwert b
	 */
	public void addRow(double[] r){
		ArrayList<Double> column = this.convertToDblArrayList(r);
		
		int size = this.tableau.get(0).size();
		if(r.length<size){ // neue Zeile ist zu kurz
			for(int i=(r.length);i<size;i++){ // Einfügen der fehlenden Nullen an vorletzter Stelle
				column.add(column.size()-1, new Double(0));
			}
		}else if(r.length>size){ // neue Zeile ist zu lang
			int anzahl = r.length-size; // Anzahl neu hinzuzufügender Nullen in den bestehenden Zeilen
			for(int i=0;i<this.tableau.size();i++){
				for(int x=1;x<=anzahl;x++){
					this.tableau.get(i).add(size-1, new Double(0));
				}
			}
		}
		this.tableau.add(this.tableau.size()-1, column); // Hinzufügen der Zeile an vorletzter Stelle
		
	}
	
	/**
	 * Fügt eine weitere Pivospalte (z.B. als künstliche oder Schlupfvariable) an vorletzter Stelle des Tableaus ein. 
	 * Die Eins befindet sich in der Zeile mit Index c, die neue Variable wird mit Kosten Null in der Zielfunktion hinzugefügt.
	 * @param c Index der Zeile, für die Eins der neuen Pivotspalte
	 */
	public void addPivotColumn(int c){
		// Pivotspalte ergänzen
		for(int i=0;i<this.tableau.size();i++){
			if(i==c){
				this.tableau.get(i).add(this.tableau.size()-2,new Double(1));
			}else{
				this.tableau.get(i).add(this.tableau.size()-2,new Double(0));
			}
			
		}
		
		// Einfügen der neuen Variable in die Zielfunktion inkl. Verschiebung des Zielwerts
		this.target.add(c,new Integer(0));
		
		// Einfügen der neuen Pivotspalte in die Basis
		this.pivots.add(c,new Integer(this.target.size()-2));	
	}
	
	/**
	 * Überführt das übergebene zweidimensionale Array in ein ArrayList<ArrayList<Double>>.
	 * @param array zu überführendes zweidimensionales Array
	 * @return überführte ArrayList
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
	 * Überführt die übergebene ArrayList<Double> in ein double[].
	 * @param arrayList zu überführende ArrayList
	 * @return überführtes Array
	 */
	private double[] convertToDblArray(ArrayList<Double> arrayList){
		double[] array = new double[arrayList.size()];
		for(int i=0;i<array.length;i++){
			array[i] = arrayList.get(i).doubleValue();
		}
		return array;
	}
	
	/**
	 * Überführt das übergebene Array in eine ArrayList<Double>.
	 * @param array zu überführendes Array
	 * @return überführte ArrayList
	 */
	private ArrayList<Double> convertToDblArrayList(double[] array){
		ArrayList<Double> arrayList = new ArrayList<Double>();
		for(int i=0;i<array.length;i++){
			arrayList.add(i,new Double(array[i]));
		}
		return arrayList;
	}

	/**
	 * Überführt die übergebene ArrayList<Integer> in ein int[].
	 * @param arrayList zu überführende ArrayList
	 * @return überführtes Array
	 */
	private int[] convertToIntArray(ArrayList<Integer> arrayList){
		int[] array = new int[arrayList.size()];
		for(int i=0;i<array.length;i++){
			array[i] = arrayList.get(i).intValue();
		}
		return array;
	}

	/**
	 * Überführt das übergebene Array in eine ArrayList<Integer>.
	 * @param array zu überführendes Array
	 * @return überführte ArrayList
	 */
	private ArrayList<Integer> convertToIntArrayList(int[] array){
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
	 * Setzt die Pvotspaltentabelle.
	 * @return Pivotspalten
	 */
	public int[] getPivots() {
		return this.convertToIntArray(this.pivots);
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
	 * Gibt die Zielfunktion zurück.
	 * @return Zielfunktion
	 */
	public int[] getTarget() {
		return this.convertToIntArray(this.target);
	}

	/**
	 * Gibt ein Array mit den x/f-Werten für jede Zeile zurück.
	 * @return Array mit den x/f-Werten für jede Zeile
	 */
	public double[] getXByF() {
		return this.convertToDblArray(this.xByF);
	}
	
	/**
	 * Gibt ein Array mit den x/f-Werten für jede Zeile zurück.
	 * @return Array mit den x/f-Werten für jede Zeile
	 */
	public double[] getDeltaByF() {
		return this.convertToDblArray(this.deltaByF);
	}
		
	/**
	 * Setzt Spalte j.
	 * @param c übergebene Spalte
	 * @param j Index der zu verändernden Spalte
	 */
	public void setColumn(double[] c, int j){
		for(int i=0;i<c.length;i++){
			this.tableau.get(i).set(j, new Double(c[i]));
		}
	}
	
	/**
	 * Setzt den Inhalt in Feld (zeile, spalte) auf den übergebenen double-Wert.
	 * @param i Index der Zeile im SimplexTableau
	 * @param j Index der Spalte im SimplexTableau
	 * @param value übergebener Wert
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
	 * Gibt die Pivotspaltentabelle aus.
	 * @param pivots zu setzende Pivotspalten
	 */
	public void setPivots(int[] pivots) {
		this.pivots = this.convertToIntArrayList(pivots);
	}
	
	/**
	 * Setzt Zeile i.
	 * @param r übergebene Zeile
	 * @param i Index der zu ändernden Zeile
	 */
	public void setRow(double[] r, int i){
		for(int a=0;a<r.length;a++){
			this.tableau.get(i).set(a,new Double(r[a]));
		}
	}
	
	/**
	 * Setzt das SimplexTableau.
	 * @param tableau übergebenes SimplexTableau
	 */
	public void setTableau(double[][] tableau) {
		this.tableau = this.convertTo2DArrayList(tableau);
	}
	
	/**
	 * Setzt die Zielfunktion.
	 * @param target übergebene Zielfunktion
	 */
	public void setTarget(int[] target) {
		this.target = this.convertToIntArrayList(target);
	}
	
	/**
	 * Überschreibt die x/f-Werte.
	 * @param xByF neue x/f-Werte
	 */
	public void setXByF(double[] xByF) {
		this.xByF = this.convertToDblArrayList(xByF);
	}
	
	/**
	 * Überschreibt die delta/f-Werte.
	 * @param deltaByF neue delta/f-Werte
	 */
	public void setDeltaByF(double[] deltaByF) {
		this.deltaByF = this.convertToDblArrayList(deltaByF);
	}
	
	/**
	 * Gibt eine Stringdarstellung des SimplexTableaus zurück.
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
	 * Gibt eine Stringdarstellung der Zielfunktion zurück.
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
	/**erstellt HTML-Code als String für das primale Problem.
	 * 
	 * @return komplettes Tableau als String in Html 
	 */
	public String tableauToHtml(){
		String html = "\n<html>\n<body>\n<table border=1 CELLSPACING=0>\n";
		//1. Zeile: Zielfunktion
		html = html + "<tr>\n<td></td><td></td>";		// direkt inkl. zwei leeren Einträgen 
		for(int i=0;i<target.size()-1;i++){
			html = html + "<td>" + (Math.round(target.get(i)*100.)/100.) + "</td>";
		}
		html = html + "<td></td><td></td></tr>\n";
		//2. Zeile: zwei Zeilen frei Durchnummerierung der Spalten + x +x/f
		html = html + "<tr><td></td><td></td>";		// direkt inkl. zwei leeren Einträgen
		for(int i=0;i<target.size()-1;i++){
			html = html +"<td>"+ (i+1) +"</td>";
		}
		html = html + "<td>x</td><td>x/f</td>";
		//ab der 3. Zeile: das eigentliche Tableau, die ersten beiden Spalten auch wie im Tableau + x/f
		for(int i=0;i<tableau.size()-1;i++){			//so oft ausführen wie es Zeilen-1 im Tableau gibt
			html = html + "<tr><td>"+ target.get(pivots.get(i))+"</td><td>" +(pivots.get(i)+1) +"</td>";
			for(int j=0;j<tableau.get(0).size();j++){
				html = html + "<td>" + (Math.round((tableau.get(i).get(j))*100.)/100.)+"</td>";
			}
			//x/f noch hinten dran hängen
			if((xByF.get(i)<=0) || (xByF.get(i)== Double.POSITIVE_INFINITY)){
				html = html + "<td> &#8211; </td>";
			}
			else{
				html = html + "<td>"+ (Math.round(xByF.get(i)*100.)/100.)+"</td>";
			}
			html = html + "</tr>\n";
		}
		// Letzte Zeile: extra behandlung für delta-Wert
		html = html + "<tr><td></td><td></td>"; //inkl. zwei leerfelder
		for(int i=0;i<tableau.get(0).size();i++){
			html = html + "<td>" + (Math.round((tableau.get(tableau.size()-1).get(i))*100.)/100.) +"</td>";
		}
		html = html + "<td></td></tr>\n";
		html = html + "</table>\n</body>\n</html>";
		return html;
	}
	
	/**Methode die das komplette Simplex-Tableau für den dualen Simplex als HTML-String zurückgibt.
	 * 
	 * @return komplettes Tableau als Duales Problem als String in Html 
	 */
	public String tableauToHtmlDual(){
		String html = "\n<html>\n<body>\n<table border=1 CELLSPACING=0>\n";
		//1. Zeile: Zielfunktion
		html = html + "<tr>\n<td></td><td></td>";		// direkt inkl. zwei leeren Einträgen 
		for(int i=0;i<target.size()-1;i++){
			html = html + "<td>" + (Math.round(target.get(i)*100.)/100.) + "</td>";
		}
		html = html + "<td></td></tr>\n";
		//2. Zeile: zwei Zeilen frei Durchnummerierung der Spalten + x +x/f
		html = html + "<tr><td></td><td></td>";		// direkt inkl. zwei leeren Einträgen
		for(int i=0;i<target.size()-1;i++){
			html = html +"<td>"+ (i+1) +"</td>";
		}
		html = html + "<td>x</td>";
		//ab der 3. Zeile: das eigentliche Tableau, die ersten beiden Spalten auch wie im Tableau + x/f
		for(int i=0;i<tableau.size()-1;i++){			//so oft ausführen wie es Zeilen-1 im Tableau gibt
			html = html + "<tr><td>"+ target.get(pivots.get(i))+"</td><td>" +(pivots.get(i)+1) +"</td>";
			for(int j=0;j<tableau.get(0).size();j++){
				html = html + "<td>" + (Math.round((tableau.get(i).get(j))*100.)/100.)+"</td>";
			}
			html = html + "</tr>\n";
		}
		// Letzte Zeile: extra behandlung für delta-Wert
		html = html + "<tr><td></td><td></td>"; //inkl. zwei leerfelder
		for(int i=0;i<tableau.get(0).size();i++){
			html = html + "<td>" + (Math.round((tableau.get(tableau.size()-1).get(i))*100.)/100.) +"</td>";
		}
		html = html + "</tr>\n";
		// allerletzte Zeile mit den delta/f-Werten
		html = html + "<tr><td></td><td></td>"; //inkl. zwei leerfelder
		for(int i=0;i<deltaByF.size();i++){
			html = html + "<td>" + (Math.round((deltaByF.get(i)*100.)/100.)) +"</td>";
		}
		html = html + "</tr>\n";
		html = html + "</table>\n</body>\n</html>";
		return html;
	}
}
