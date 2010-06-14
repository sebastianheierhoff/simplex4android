package com.googlecode.simplex4android;

import java.io.IOException;

//Eingabe verarbeiten/Tableau vervollständigen - Max

//Simplex-Algorithmus 2. Phase
public class SimplexLogic {

	SimplexHistory history;
	SimplexProblem problem;
	
	public SimplexLogic(double[][] tableau, int[] target) {

		this.problem = new SimplexProblem(tableau, target);
	}
	
	//Implementiert die 2. Phase des Simplex-Algorithmus
	public static void main(String[] args){
		double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}};
		int[] target = {1,2,7,6,0,0}; //int[] oder double[]
		SimplexLogic simplex = new SimplexLogic(tableau, target);
		//Pivotspalten finden - Sebastian
		System.out.println(java.util.Arrays.deepToString(simplex.problem.getTableau()));
		System.out.println(java.util.Arrays.toString(simplex.findPivot()));

		//Methode Delta-Werte berechnen
		
		//Zielwert berechnen
		
		//x/f errechnen, Minimum berechnen, neue Pivotspalte bestimmen
		
		//Basiswechsel durchführen (Gauß-Algorithmus) - Stefan
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
	
	/**
	 * Gibt ein Array mit den Pivotspalten aus.
	 * @return Array mit den Pivotspalten
	 */
	public int[] findPivot(){
		int[] pivots = new int[this.problem.getNoRows()-1]; //int[] pivots: Länge entspricht der Anzahl Zeilen des Tableaus-1
		int countPivots = 0; //Zähler, wie viele Pivotspalten bereites gefunden wurden
		for(int i = 0; i<this.problem.getNoColumns(); i++){ //For-Schleife, durchläuft alle Spalten
			int posOfOne = 0;// Speichert die Position der ersten gefundenen 1 in einer Spalte
			int noo = 0;//Anzahl Einsen
			for(int k = 0; k<this.problem.getNoRows(); k++){ //For-Schleife, durchläuft alle Zeilen
				if(this.problem.getField(k,i) != 0 && this.problem.getField(k,i) != 1){
					break; //Abbruch des Durchlaufs, falls die Zahl an Stelle k != 0 bzw. != 1
				}
				else{
					if(this.problem.getField(k,i) == 1){ 
						posOfOne = i;
						noo++; //Anzahl Einsen um 1 erhöhen, falls Zelle[k][i] = 1
					}
					if(noo > 1){
						break; //Abbruch, falls mehr als eine 1 in einer Spalte gefunden wird
					}
				}
			}
			if(noo == 1){
				pivots[countPivots] = posOfOne;
				countPivots++;
			}
		}
		this.problem.setPivots(pivots);
	}
	
	public void calcDeltas(){		
	}
	
	//Einheitsvektoren finden, Tableau auffüllen, künstliche Variablen
	
	//Zielwert ausgeben
	
	//Pivot
	
}
