package com.googlecode.simplex4android;

import java.io.IOException;

/**
 * Klasse SimplexLogic - bekommt ein SimplexProblem übergeben, bearbeitete dieses und gibt ein neues SimplexProbelm zurück.
 * @author Simplex4Android
 */
public abstract class SimplexLogic {
	
	/**
	 * Führt für das übergebene SimplexProblem die 2. Phase des Simplex-Algorithmus durch.
	 * @return bearbeitetes SimplexProblem
	 */
	public static SimplexProblem doSimplex(SimplexProblem problem){
		problem.setPivots(findPivots(problem)); 	//Pivotspalten finden		
		problem = calcDeltas(problem); //Delta-Werte berechnen (1. Durchgang, muss durch Funktion geschehen)
		problem = calcXByF(problem); //x/f errechnen, 
		//Minimum berechnen, 

		//Basiswechsel durchführen (Gauß-Algorithmus) - Stefan
		try {
			if(problem.getOptimal()!= true){
				return gauss(problem, choosePivotRow(problem), choosePivotColumn(problem)); //neues Pivotelement (Zeile/Spalte) bestimmen
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
	public static SimplexProblem gauss(SimplexProblem problem, int zeile, int spalte) throws IOException{
		double pivotElement = problem.getField(zeile, spalte);
		
		//Normalisierung der neuen Pivotzeile
		if(pivotElement==0 || pivotElement==Double.POSITIVE_INFINITY || pivotElement==Double.NEGATIVE_INFINITY){
			throw new IOException("Pivotelement ist gleich Null oder Unendlich.");
		}
		double pivotfaktor = 1/pivotElement;
		double[] pivotZeile = problem.getRow(zeile);
		for(int i=0;i<problem.getNoColumns();i++){
			pivotZeile[i] = pivotZeile[i]*pivotfaktor;
		}
		problem.setRow(pivotZeile, zeile);
		
		//Erzeugen der Nullen in der Pivotspalte
		for(int i=0;i<problem.getNoRows();i++){
			if(i!=zeile && problem.getField(i, spalte)!=0){
				double zeilenfaktor = problem.getField(i, spalte);
				for(int j=0;j<problem.getNoRows();j++){
					problem.setField(i, j, (problem.getField(i, j)-zeilenfaktor*problem.getField(zeile, j)));
				}				
			}						
		}
		return problem;
	}
	
	/**
	 * Berechnet die delta-Werte in der letzten Zeile des SimplexTableaus
	 * @param problem zu bearbeitendes SimplexTableau
	 * @return bearbeitetes SimplexTableau
	 */
	public static SimplexProblem calcDeltas(SimplexProblem problem){
		for(int i = 0; i<problem.getNoColumns(); i++){ //durchläuft alle Spalten
			double delta = 0;
			for(int k = 0; k<problem.getNoRows()-1; k++){
				delta += problem.getField(k,i) * problem.getTarget()[problem.getPivots()[k]]; 
			}
			delta = delta - problem.getTarget()[i];
			problem.setField(problem.getNoRows()-1, i, delta);	
		}
		return problem;
	}
	
	/**
	 * Berechnet die x/f-Werte des SimplexProblems.
	 * @param problem SimplexProblem, in dem x/f-Werte berechnet werden sollen.
	 * @return bearbeitetes SimplexProblem
	 */
	public static SimplexProblem calcXByF(SimplexProblem problem){
		if(!problem.getOptimal()){
			int pivotColumn = choosePivotColumn(problem);
			double[] xByF = new double[problem.getNoRows()-1];
			double[] f = problem.getLastColumn();
			for(int i = 0; i<problem.getNoRows()-1; i++){
				xByF[i] = (f[i] / problem.getField(i, pivotColumn));
			}
			problem.setXByF(xByF);
		}
		return problem;
	}
	
	/**
	 * Findet die neue Pivotspalte und gib diese aus.
	 * @param problem SimplexProblem, in dem die neue Pivotspalte gefunden werden soll.
	 * @return neue Pivotspalte
	 */
	public static int choosePivotColumn(SimplexProblem problem){
		for(int i = 0; i<problem.getNoColumns()-1; i++){
			if(problem.getTableau()[problem.getNoRows()-1][i] >0){
				return i;
			}
		}
		problem.setOptimal();
		return -1;
	}
	
	/**
	 * Findet die neue Pivotzeile und gib diese aus.
	 * @param problem SimplexProblem, in dem die neue Pivotzeile gefunden werden soll.
	 * @return neue Pivotzeile
	 */
	public static int choosePivotRow(SimplexProblem problem){
		int row = -1;
		if(!problem.getOptimal()){
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
	 * @param problem SimplexProblem, für das die Pivotspalten bestimmt werden sollen.
	 * @return Array mit den Pivotspalten
	 */
	public static int[] findPivots(SimplexProblem problem){
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
		return pivots;
	}
}
