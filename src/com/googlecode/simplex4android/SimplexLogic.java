package com.googlecode.simplex4android;

//Eingabe verarbeiten/Tableau vervollst�ndigen - Max

//Simplex-Algorithmus 2. Phase
public class SimplexLogic {
	
	SimplexTableau problem;
	
	
	public SimplexLogic(double[][] tableau, int[] target) {
		this.problem = new SimplexTableau(tableau, target);
	}
	
	//Implementiert die 2. Phase des Simplex-Algorithmus
	public static void main(String[] args){
		double[][] tableau = {{-1.5,0,0.5,0},{3,1,-1,0},{0,0,1,0},{0,1,0,0},{1,0,0,0},{-1,-1,1,0},{-1,-1,1,0}};
		int[] target = {1,2,7,6,0,0}; //int[] oder double[]
		SimplexLogic simplex = new SimplexLogic(tableau, target);
		//Pivotspalten finden - Sebastian
		System.out.println(simplex.findPivot().toString());
		//Methode Delta-Werte berechnen

		//Zielwert berechnen
		
		//x/f errechnen, Minimum berechnen, neue Pivotspalte bestimmen
		
		//Basiswechsel durchf�hren (Gau�-Algorithmus) - Stefan
		
	

		
	}
	
	
	/**
	 * F�hrt f�r ein gegebenes Pivotelement an der Stelle (zeile,spalte) im SimplexTableau den Gau�-Algorithmus durch.
	 * @param simplex �bergebenes SimplexTableau
	 * @param zeile Zeile des Pivotelements
	 * @param spalte Spalte des Pivotelements
	 * @return mit dem Gau�-Algorithmus bearbeitetes SimplexTableau
	 */
	public SimplexTableau gauss(SimplexTableau simplex, int zeile, int spalte){
		zeile = zeile++;
	}
	
	//Pivotspalte finden
	public int[] findPivot(){
		int[] pivots = new int[this.problem.getNoRows()-1]; //int[] pivots: L�nge entspricht der Anzahl Zeilen des Tableaus-1
		int countPivots = 0; //Z�hler, wie viele Pivotspalten bereites gefunden wurden
		int posOfOne = 0;// Speichert die Position der ersten gefundenen 1 in einer Spalte
		for(int i = 0; i<this.problem.getNoColumns(); i++){ //For-Schleife, durchl�uft alle Spalten
			int noo = 0;//Anzahl Einsen
			for(int k = 0; k<this.problem.getNoRows(); k++){ //For-Schleife, durchl�uft alle Zeilen
				if(this.problem.getField(i,k) != 0 && this.problem.getField(i,k) != 1){
					break; //Abbruch des Durchlaufs, falls die Zahl an Stelle k != 0 bzw. != 1
				}
				else{
					if(this.problem.getField(i,k) == 1){ 
						posOfOne = k;
						noo++; //Anzahl Einsen um 1 erh�hen, falls Zelle[i][k] = 1
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
		return pivots;
	}
	
	//Einheitsvektoren finden, Tableau auff�llen, k�nstliche Variablen
	
	//Zielwert ausgeben
	
	//Pivot
	
}
