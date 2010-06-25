package com.googlecode.simplex4android;

import java.util.Arrays;

/**
 * Klasse Simplex - legt eine History an, nimmt die Eingabe entgegen, verarbeitet diese, lässt das Problem je nach Settings 
 * von SimplexLogic lösen und sorgt für die Ausgabe.
 * @author Simplex4Android
 */
public class Simplex {
	
	SimplexHistory history;
	
	public Simplex(){
		this.history = new SimplexHistory();
	}
	
	public static void main(String[] args){
		boolean debug = true;
		Simplex simplex = new Simplex();
		
		//Settings lesen, SimplexSettings erzeugen
		
		//Eingabe lesen
		double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}}; //Beispiel-Tableau
		int[] target = {1,2,7,5,0,0,0}; //Beispiel-Zielfunktion - Zielfunktion muss um eine 0 verlängert werden, um Zielwert berechnen zu können!!!
		
		//SimplexProblem erzeugen (aus Tableau, Target, SimplexSettings)
		SimplexProblem firstProblem = new SimplexProblem(tableau, target);
			
		//SimplexProblem in History einfügen
		simplex.history.addElement(firstProblem);

		//SimplexLogic auf SimplexProblem(e) ausführen, bis optimale Lösung gefunden, dabei Ausgabe aller Zwischenschritte
		//while(simplex.history.getLastElement().getOptimal() != true){
			SimplexLogic current = new SimplexLogic(simplex.history.getLastElement());
			simplex.history.addElement(current.simplex());
			
			//Debug-Ausgabe
			if(debug == true){System.out.println("Tableau: \n" + current.problem.tableauToString());} 
			if(debug == true){System.out.println("Zielfunktion: " + current.problem.targetToString());} 
			if(debug == true){System.out.println("Tableau (Delta-Werte berechnet): " + Arrays.deepToString(current.problem.getTableau()));} //Debug
			if(debug == true){System.out.println("Basisspalten: " + Arrays.toString(current.problem.getPivots()));}
			if(debug == true){System.out.println("Aktuell gewählte Pivotspalte: "+current.choosePivotRow());}
			

		//}
	}

	
	
}
