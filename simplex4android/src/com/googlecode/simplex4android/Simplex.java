package com.googlecode.simplex4android;

import java.io.IOException;
import java.util.Arrays;

/**
 * Klasse Simplex - legt eine History an, nimmt die Eingabe entgegen, verarbeitet diese, lässt das Problem je nach Settings 
 * von SimplexLogic lösen und sorgt für die Ausgabe.
 * @author Simplex4Android
 */
public class Simplex {
	
	public Simplex(){
	}
	
	public static void main(String[] args){
		boolean debug = true;
		SimplexHistory sh = new SimplexHistory();
		
		//Settings lesen, SimplexSettings erzeugen
		
		//Eingabe lesen
		//Beispiel-Tableau
		double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}}; 
		//Beispiel-Zielfunktion - Zielfunktion muss um eine 0 verlängert werden, um Zielwert berechnen zu können!!!
		int[] target = {1,2,7,5,0,0,0}; 
		
		//SimplexProblem erzeugen (aus Tableau, Target, SimplexSettings)
		SimplexProblem firstProblem = new SimplexProblem(tableau, target);
		firstProblem.setPivots(SimplexLogic.findPivots(firstProblem));		
		firstProblem = SimplexLogic.calcDeltas(firstProblem);
		firstProblem = SimplexLogic.calcXByF(firstProblem);
			
		//SimplexProblem in History einfügen
		sh.addElement(firstProblem);
		
		
		if(debug == true){System.out.println("Tableau: \n" + firstProblem.tableauToString());} 
		if(debug == true){System.out.println("Zielfunktion: " + firstProblem.targetToString());}
		if(debug == true){System.out.println("HTML: "+firstProblem.tableauToHtml());}

		//SimplexLogic auf SimplexProblem(e) ausführen, bis optimale Lösung gefunden, dabei Ausgabe aller Zwischenschritte
		while(sh.getLastElement().getOptimal()!=true){
			SimplexProblem current = sh.getLastElement();
			current = SimplexLogic.simplex(current);
			
			//Debug-Ausgabe
			if(debug == true){System.out.println("Tableau: \n" + current.tableauToString());}
			if(debug == true){System.out.println("Basisspalten: " + Arrays.toString(current.getPivots()));}
			if(debug == true){System.out.println("Optimal: "+current.getOptimal());}
			if(debug == true){System.out.println("HTML: "+current.tableauToHtml());}
			
			sh.addElement(current);
		}
	}

	
	
}
