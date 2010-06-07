package com.googlecode.simplex4android;

//Eingabe verarbeiten/Tableau vervollständigen - Max


//Simplex-Algorithmus 2. Phase
public class SimplexLogic {
	
	double[][] tableau = {{-1.5,0,0.5,0},{3,1,-1,0},{0,0,1,0},{0,1,0,0},{1,0,0,0},{-1,-1,1,0},{-1,-1,1,0}};
	double[] target = {1,2,7,6,0,0};

	public SimplexLogic() {
		//Pivotspalten finden - Sebastian
		
		//Methode Delta-Werte berechnen
		
		//Zielwert berechnen
		
		//x/f errechnen, Minimum berechnen, neue Pivotspalte bestimmen
		
		//Basiswechsel durchführen (Gauß-Algorithmus) - Stefan
		
	
	}
	
	//Pivotspalte finden
	public int[] findPivot(){
		int[] pivots = new int[this.tableau[0].length-1];
		for(int i = 0; i<tableau.length; i++){
			int noo = 0;//Anzahl Einsen
			for(int k = 0; k<tableau[0].length; k++){
				if(tableau[i][k] != 0 && tableau[i][k] != 1){
					break;
				}
				else{
					if(tableau[i][k] = 1){
						noo++;
					}
				}
			}
			
		}
		return pivots;
	}
	
	
	//Einheitsvektoren finden, Tableau auffüllen, künstliche Variablen
	
	//Zielwert ausgeben
	
	//Pivot
	
}
