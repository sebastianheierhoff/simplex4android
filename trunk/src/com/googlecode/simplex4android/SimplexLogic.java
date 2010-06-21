package com.googlecode.simplex4android;

import java.io.IOException;

/**
 * Klasse SimplexLogic - bekommt ein SimplexProblem übergeben, bearbeitete dieses und gibt ein neues SimplexProbelm zurück.
 * @author
 */
public class SimplexLogic {
	SimplexProblem problem;
	
	public SimplexLogic(SimplexProblem currentProblem) {
		this.problem = currentProblem;
	}
	
	//Implementiert die 2. Phase des Simplex-Algorithmus
	public SimplexProblem simplex(){
		this.findPivots(); 	//Pivotspalten finden		
		this.calcDeltas(); //Delta-Werte berechnen (1. Durchgang, muss durch Funktion geschehen)
		this.calcXByF(); //x/f errechnen, 
		//Minimum berechnen, 

		//Basiswechsel durchführen (Gauß-Algorithmus) - Stefan
		try {
			if(this.problem.getOptimal()!= true){
				return this.gauss(this.choosePivotRow(), this.choosePivotColumn()); //neues Pivotelement (Zeile/Spalte) bestimmen
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

		
	/**
	 * Führt für ein gegebenes Pivotelement an der Stelle (zeile,spalte) im SimplexTableau den Gauß-Algorithmus durch.
	 * @param zeile Zeile des Pivotelements
	 * @param spalte Spalte des Pivotelements
	 * @return mit dem Gauß-Algorithmus bearbeitetes SimplexTableau
	 */
	public SimplexProblem gauss(int zeile, int spalte) throws IOException{
		SimplexProblem s = this.problem;
		double pivotElement = s.getField(zeile, spalte);
		
		//Normalisierung der neuen Pivotzeile
		if(pivotElement==0 || pivotElement==Double.POSITIVE_INFINITY || pivotElement==Double.NEGATIVE_INFINITY){
			throw new IOException("Pivotelement ist gleich Null oder Unendlich.");
		}
		double pivotfaktor = 1/s.getField(zeile, spalte);
		double[] pivotZeile = s.getRow(zeile);
		for(int i=0;i<s.getNoColumns();i++){
			pivotZeile[i] = pivotZeile[i]*pivotfaktor;
		}
		s.setRow(pivotZeile, zeile);
		
		//Erzeugen der Nullen in der Pivotspalte
		for(int i=0;i<s.getNoRows();i++){
			if(i!=zeile && s.getField(i, spalte)!=0){
				double zeilenfaktor = s.getField(i, spalte)/pivotElement;
				for(int j=0;j<s.getNoRows();j++){
					s.setField(i, j, (s.getField(i, j)-zeilenfaktor*pivotElement));
				}				
			}						
		}
		return s;
	}
	
	public void calcDeltas(){
		for(int i = 0; i<problem.getNoColumns(); i++){ //durchläuft alle Spalten
			double delta = 0;
			for(int k = 0; k<problem.getNoRows()-1; k++){
				delta += problem.getField(k,i) * this.problem.getTarget()[this.problem.getPivots()[k]]; 
			}
			delta = delta - this.problem.getTarget()[i];
			problem.setField(problem.getNoRows()-1, i, delta);	
		}
	}
	
	public int choosePivotColumn(){
		for(int i = 0; i<problem.getNoColumns()-1; i++){
			if(this.problem.getTableau()[problem.getNoRows()-1][i] >0){
				return i;
			}
		}
		this.problem.setOptimal();
		return -1;
	}
	
	public void calcXByF(){
		if(!this.problem.getOptimal()){
			int pivotColumn = this.choosePivotColumn();
			double[] xByF = new double[problem.getNoRows()-1];
			double[] f = problem.getLastColumn();
			for(int i = 0; i<problem.getNoRows()-1; i++){
				xByF[i] = (f[i] / problem.getField(i, pivotColumn));
			}
			problem.setXByF(xByF);
		}
	}
	
	public int choosePivotRow(){
		int row = -1;
		if(!this.problem.getOptimal()){
			double min = Double.MAX_VALUE;
			for(int i = 0; i<problem.getXByF().length; i++){
				if(problem.getXByF()[i]<min && problem.getXByF()[i] >= 0){
					min = problem.getXByF()[i];
					row = i;
				}
			}
		}
		return row;
	}
	
	/**
	 * Gibt ein Array mit den Pivotspalten aus.
	 * @return Array mit den Pivotspalten
	 */
	public void findPivots(){
		int[] pivots = new int[problem.getNoRows()-1]; //int[] pivots: Länge entspricht der Anzahl Zeilen des Tableaus-1
		for(int i = 0; i<problem.getNoColumns(); i++){ //For-Schleife, durchläuft alle Spalten
			int posOfOne = 0;// Speichert die Position der ersten gefundenen 1 in einer Spalte
			int noo = 0;//Anzahl Einsen
			for(int k = 0; k<problem.getNoRows()-1; k++){ //For-Schleife, durchläuft alle Zeilen
				if(problem.getField(k,i) != 0 && problem.getField(k,i) != 1){
					break; //Abbruch des Durchlaufs, falls die Zahl an Stelle k != 0 bzw. != 1
				}
				else{
					if(problem.getField(k,i) == 1){ 
						posOfOne = k;
						noo++; //Anzahl Einsen um 1 erhöhen, falls Zelle[k][i] = 1
					}
					if(noo > 1){
						break; //Abbruch, falls mehr als eine 1 in einer Spalte gefunden wird
					}
				}
			}
			if(noo == 1){
				pivots[posOfOne] = i;
			}
		}
		problem.setPivots(pivots);
	}
}
