package com.googlecode.simplex4android;

import java.io.IOException;

/**
 * Klasse SimplexLogic - bekommt ein SimplexProblem übergeben, bearbeitete dieses und gibt ein neues SimplexProbelm zurück.
 * @author Simplex4Android
 */
public abstract class SimplexLogic {
	
	/**
	 * Berechnet die delta-Werte in der letzten Zeile des SimplexTableaus
	 * @param problem zu bearbeitendes SimplexTableau
	 */
	public static void calcDeltas(SimplexProblem problem){
		for(int i = 0; i<problem.getNoColumns(); i++){ //durchläuft alle Spalten
			double delta = 0;
			for(int k = 0; k<problem.getNoRows()-1; k++){
				delta += problem.getField(k,i) * problem.getTarget()[problem.getPivots()[k]]; 
			}
			delta = delta - problem.getTarget()[i];
			problem.setField(problem.getNoRows()-1, i, delta);	
		}
	}

		
	/**
	 * Berechnet die x/f-Werte des SimplexProblems.
	 * @param problem SimplexProblem, in dem x/f-Werte berechnet werden sollen.
	 */
	public static void calcXByF(SimplexProblem problem){
		if(problem.getOptimal()!=true){
			int pivotColumn = choosePivotColumn(problem);
			double[] xByF = new double[problem.getNoRows()-1];
			double[] f = problem.getLastColumn();
			for(int i = 0; i<problem.getNoRows()-1; i++){
				xByF[i] = (f[i] / problem.getField(i, pivotColumn));
			}
			problem.setXByF(xByF);
		}
	}
	
	/**
	 * Überprüft anhand der Delta-Werte, ob das aktuelle Tableau des SimplexProblem optimal ist, und setzt dieses dann ggf. optimal.
	 * @param problem zu prüfendes SimplexProblem
	 * @return true, wenn optimal, sonst false
	 */
	public static boolean checkOptimal(SimplexProblem problem){
		double[] deltas = problem.getRow(problem.getNoRows()-1);
		for(int i=0;i<deltas.length-1;i++){
			if(deltas[i]>0){
				return false;
			}
		}
		problem.setOptimal();
		return true;
	}
	
	/**
	 * Wählt am weitesten links stehendes Element
	 * Findet die neue Pivotspalte und gib diese aus.
	 * @param problem SimplexProblem, in dem die neue Pivotspalte gefunden werden soll.
	 * @return neue Pivotspalte
	 */
	public static int choosePivotColumn(SimplexProblem problem){
		int column = -1;
		for(int i = 0; i<problem.getNoColumns()-1; i++){
			if(problem.getTableau()[problem.getNoRows()-1][i] >0){
				column = i;
			}
		}
		return column;
	}
	
	/**
	 * Findet die neue Pivotzeile und gib diese aus.
	 * @param problem SimplexProblem, in dem die neue Pivotzeile gefunden werden soll.
	 * @return neue Pivotzeile
	 */
	public static int choosePivotRow(SimplexProblem problem){
		int row = -1;
		double min = Double.MAX_VALUE;
		for(int i = 0; i<problem.getXByF().length; i++){
			if(problem.getXByF()[i]<min && problem.getXByF()[i] >= 0){
				min = problem.getXByF()[i];
				row = i;				
			}
		}
		return row;
	}
	
	/**
	 * Gibt ein Array mit den Pivotspalten aus.
	 * @param problem SimplexProblem, für das die Pivotspalten bestimmt werden sollen.
	 * @return Array mit den Pivotspalten
	 */
	public static void findPivots(SimplexProblem problem){
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
		
	/**
	 * Führt für ein gegebenes Pivotelement an der Stelle (zeile,spalte) im SimplexTableau den Gauß-Algorithmus durch.
	 * @param zeile Index der Zeile des Pivotelements
	 * @param spalte Index der Spalte des Pivotelements
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
				for(int j=0;j<problem.getNoColumns();j++){
					problem.setField(i, j, (problem.getField(i, j)-zeilenfaktor*problem.getField(zeile, j)));
				}				
			}						
		}
		return problem;
	}
	
	/**
	 * Führt für das übergebene SimplexProblem die 2. Phase des Simplex-Algorithmus durch.
	 * @return bearbeitetes SimplexProblem
	 */
	public static SimplexProblem simplex(SimplexProblem problem){	
		try {
			if(problem.getOptimal()!= true){				
				SimplexProblem sp = gauss(problem, choosePivotRow(problem), choosePivotColumn(problem));
				findPivots(sp);
				checkOptimal(sp);
				calcXByF(sp);
				return sp;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Wählt am weitesten oben stehendes Element dass kleiner null ist und
	 * in dessen Zeile mindestens ein f-Wert kleiner null ist
	 * Findet die neue Zeile, in der das Pivotelement verschoben wird
	 * @param problem SimplexProblem, in dem die neue Pivotspalte gefunden werden soll.
	 * @return Zeile in der nach neuer Pivotspalte gesucht wird
	 */
	public static int chooseRowDualSimplex(SimplexProblem problem){
		int row = -1;
		for(int i = 0; i<problem.getNoRows()-1; i++){
			if((problem.getTableau()[problem.getNoColumns()-1][i]>0)){
				boolean fSmallerNull = false;	//mindestens ein F-Wert ist kleiner null
				for(int j=0;j<problem.getRow(i).length;j++){
					if(problem.getRow(i)[j]<0){
						fSmallerNull = true;
						j = problem.getRow(i).length;
					}
				}
				if(fSmallerNull==true)row = i;
			}
		}
		return row;
	}
	
	/**überprüft ob Problem nach dem dualen Simplex optimal ist.
	 * 
	 * @param problem
	 * @return boolean ob optimal
	 */
	public static boolean checkDualOptimal(SimplexProblem problem){
		if(chooseRowDualSimplex(problem)==-1)return true;
		else return false;
	}
	/**
	 * Berechnet die delta/f-Werte des SimplexProblems.
	 * @param problem SimplexProblem, in dem delta/f-Werte berechnet werden sollen.
	 */
	public static void calcDeltaByF(SimplexProblem problem){
		if(problem.getOptimal()!=true){
			int row = chooseRowDualSimplex(problem);
			double[] deltaByF = new double[problem.getNoColumns()-1];
			double[] delta = problem.getLastRow();
			for(int i = 0; i<problem.getNoColumns()-1; i++){
				if(problem.getField(row, i)<0) deltaByF[i] = (delta[i] / problem.getField(row, i));
				else{
					deltaByF[i] = -1;
				}
			}
			problem.setDeltaByF(deltaByF);
		}
	}
	
	/**
	 * Findet die Spalte, die in die Basis geht und gibt diese aus.
	 * @param problem SimplexProblem, in dem die neue Pivotzeile gefunden werden soll.
	 * @return Spalte, die in die Basis geht
	 */
	public static int choosePivotColumnDualSimplex(SimplexProblem problem){
		int column = -1;
		double min = Double.MAX_VALUE;
		for(int i = 0; i<problem.getDeltaByF().length; i++){
			if(problem.getDeltaByF()[i]<min && problem.getDeltaByF()[i] > 0){
				min = problem.getXByF()[i];
				column = i;
			}
		}
		return column;
	}
	
	/**stellt fest ob Problem primal zulässig ist
	 * 
	 * @param SimplexProblem
	 * @return boolean ob zulässig oder nicht
	 */
	public static boolean primalValid(SimplexProblem problem){
		boolean valid = true;
		for(int i=0;i<problem.getLastColumn().length;i++){
			if(problem.getLastColumn()[i]<0)valid = false;
		}
		return valid;
	}
}
