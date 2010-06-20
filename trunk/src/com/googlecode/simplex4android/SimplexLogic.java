package com.googlecode.simplex4android;

import java.io.IOException;
import java.util.Arrays;

//Eingabe verarbeiten/Tableau vervollständigen - Max

//Simplex-Algorithmus 2. Phase
public class SimplexLogic {

	SimplexHistory history;
	SimplexProblem currentProblem;
	//SimplexSettings currentSettings;
	double[][] currentTableau;
	int[] currentTarget;
	int[] currentPivots;
	
	public SimplexLogic(double[][] tableau, int[] target) {
		this.history = new SimplexHistory(tableau, target);
		this.currentProblem = this.history.getLastElement();
		//this.currentSettings = this.history.getLastElement().getSettings();
		this.currentTableau = this.history.getLastElement().getTableau(); //Aktuelles Tableau
		this.currentTarget = this.history.getLastElement().getTarget();//Aktuelle Zielfunktion
		this.currentPivots = this.history.getLastElement().getPivots(); //Aktuelle Pivotspalten
	}
	
	//Implementiert die 2. Phase des Simplex-Algorithmus
	public static void main(String[] args){
		boolean debug = true;
		double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}}; //Beispiel-Tableau
		int[] target = {1,2,7,5,0,0,0}; //Beispiel-Zielfunktion - Zielfunktion muss um eine 0 verlängert werden, um Zielwert berechnen zu können!!!
		
		SimplexLogic simplex = new SimplexLogic(tableau, target);
	
		//Pivotspalten finden - Sebastian
		simplex.findPivots();
		simplex.currentPivots = simplex.history.getLastElement().getPivots(); //Aktuelle Pivotspalten
		
		
		if(debug == true){System.out.println("Tableau: " + java.util.Arrays.deepToString(simplex.currentTableau));} //Debug
		if(debug == true){System.out.println("Zielfunktion: " + java.util.Arrays.toString(simplex.currentTarget));} //Debug
		if(debug == true){System.out.println("Pivotspalten: " + java.util.Arrays.toString(simplex.currentPivots));} //Debug
		
		//Delta-Werte berechnen (1. Durchgang, muss durch Funktion geschehen)
		simplex.calcDeltas();
		
		if(debug == true){System.out.println("Tableau (Delta-Werte berechnet): " + java.util.Arrays.deepToString(simplex.currentTableau));} //Debug

		//Delta-Werte und Zielwert berechnen - mittels Gauß-Algorithmus
		
		//x/f errechnen, Minimum berechnen, neue Pivotspalte bestimmen
		simplex.calcXByF();
		System.out.println(Arrays.toString(simplex.currentProblem.getXByF()));
		System.out.println(simplex.choosePivotRow());
		
		//Basiswechsel durchführen (Gauß-Algorithmus) - Stefan
		
		try {
			simplex.gauss(simplex.choosePivotRow(), simplex.choosePivotColumn());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(debug == true){System.out.println("Tableau: " + java.util.Arrays.deepToString(simplex.history.getLastElement().getTableau()));} //Debug
		//Einheitsvektoren finden, Tableau auffüllen, künstliche Variablen
		
		//Zielwert ausgeben
	}

		
	/**
	 * Führt für ein gegebenes Pivotelement an der Stelle (zeile,spalte) im SimplexTableau den Gauß-Algorithmus durch.
	 * @param zeile Zeile des Pivotelements
	 * @param spalte Spalte des Pivotelements
	 * @return mit dem Gauß-Algorithmus bearbeitetes SimplexTableau
	 */
	public void gauss(int zeile, int spalte) throws IOException{
		SimplexProblem s = currentProblem;
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
		this.history.addElement(s);
	}
	
	public void calcDeltas(){
		for(int i = 0; i<currentProblem.getNoColumns(); i++){ //durchläuft alle Spalten
			double delta = 0;
			for(int k = 0; k<currentProblem.getNoRows()-1; k++){
				delta += currentProblem.getField(k,i) * currentTarget[currentPivots[k]]; 
			}
			delta = delta - currentTarget[i];
			currentProblem.setField(currentProblem.getNoRows()-1, i, delta);	
		}
	}
	
	public int choosePivotColumn(){
		for(int i = 0; i<currentProblem.getNoColumns(); i++){
			if(currentTableau[currentProblem.getNoRows()-1][i] >0){
				return i;
			}
		}
		return -1;
	}
	
	public void calcXByF(){
		int pivotColumn = this.choosePivotColumn();
		double[] xByF = new double[currentProblem.getNoRows()-1];
		double[] f = currentProblem.getLastColumn();
		for(int i = 0; i<currentProblem.getNoRows()-1; i++){
			xByF[i] = (f[i] / currentProblem.getField(i, pivotColumn));
		}
		currentProblem.setXByF(xByF);
	}
	
	public int choosePivotRow(){
		int row = -1;
		double min = Double.MAX_VALUE;
		for(int i = 0; i<currentProblem.getXByF().length; i++){
			if(currentProblem.getXByF()[i]<min && currentProblem.getXByF()[i] >= 0){
				min = currentProblem.getXByF()[i];
				row = i;
			}
		}
		return row;
	}
	
	/**
	 * Gibt ein Array mit den Pivotspalten aus.
	 * @return Array mit den Pivotspalten
	 */
	public void findPivots(){
		int[] pivots = new int[currentProblem.getNoRows()-1]; //int[] pivots: Länge entspricht der Anzahl Zeilen des Tableaus-1
		for(int i = 0; i<currentProblem.getNoColumns(); i++){ //For-Schleife, durchläuft alle Spalten
			int posOfOne = 0;// Speichert die Position der ersten gefundenen 1 in einer Spalte
			int noo = 0;//Anzahl Einsen
			for(int k = 0; k<currentProblem.getNoRows()-1; k++){ //For-Schleife, durchläuft alle Zeilen
				if(currentProblem.getField(k,i) != 0 && currentProblem.getField(k,i) != 1){
					break; //Abbruch des Durchlaufs, falls die Zahl an Stelle k != 0 bzw. != 1
				}
				else{
					if(currentProblem.getField(k,i) == 1){ 
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
		currentProblem.setPivots(pivots);
	}
}
