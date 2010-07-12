package com.googlecode.simplex4android;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Klasse Simplex - legt eine History an, nimmt die Eingabe entgegen, verarbeitet diese, lässt das Problem je nach Settings 
 * von SimplexLogic lösen und sorgt für die Ausgabe.
 * @author Simplex4Android
 */
public class SimplexLocal {
	
	public SimplexLocal(){
	}
	
	public static void main(String[] args){
		boolean debug = true;
		SimplexHistory sh = new SimplexHistory();
		
		//Settings lesen, SimplexSettings erzeugen
		
		//Eingabe lesen
		//Beispiel-Tableau
		//double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}};
		//duales Beispiel Folie 2-143
		//double[][] tableau = {{-1,-2,-1,1,0,-3},{-2,1,-3,0,1,-4},{0,0,0,0,0,0}};
		//double[] target = {2,3,4,0,0,0};
		//duales Beispiel Übung 7c
		double[][] tableau = {{0,0,-4,-0.6,1,1,0.6,-3.8},{-1,1,3,0.4,0,-1,-0.4,3.2},{0,0,0,0,0,0,0,0}};
		double[] target = {75,15,55,15,5,0,0,0};
		//double[][] tab = {{-1.5,3,0,0,5,-1,6},{0,1,0,5,0,-1,3},{0.5,-1,5,0,0,1,1},{0,0,0,0,0,0,0}};
		//Beispiel-Zielfunktion - Zielfunktion muss um eine 0 verlängert werden, um Zielwert berechnen zu können!!!
		//double[] target = {1,2,7,5,0,0,0}; 
		
		//SimplexProblem erzeugen (aus Tableau, Target, SimplexSettings)
		SimplexProblemDual firstProblem = new SimplexProblemDual(tableau, target);
		sh = SimplexLogic.twoPhaseSimplex(firstProblem);
		System.out.println(sh.getLastElement().tableauToHtml());
		//SimplexLogic.findPivots(firstProblem);
		//SimplexLogic.calcDeltas(firstProblem);
		//double[] deltas = {2.5,-4,0,0,0,2,22};
//		System.out.println(Arrays.toString(firstProblem.getPivots()));
//		System.out.println(firstProblem.targetToString()); // KOMISCHE TARGET,PRÜFEN!!!
//		System.out.println(Arrays.toString(firstProblem.getRow(firstProblem.getNoRows()-1))); // MACHT GRÜTZE, PRÜFEN!!!
		//sh.addElement(firstProblem);
		//sh.toString();
//		if(debug == true){System.out.println("Tableau: \n" + firstProblem.tableauToString());} 
//		if(debug == true){System.out.println("Zielfunktion: " + firstProblem.targetToString());}
//		if(debug == true){System.out.println("Basisspalten: " + Arrays.toString(firstProblem.getPivots()));}
//		//if(debug == true){System.out.println("HTML: "+ firstProblem.tableauToHtml());}
//
//		//SimplexProblem in History einfügen
//		firstProblem.setField(0, 4, 5.0);
//		firstProblem.setField(1, 3, 5.0);
//		firstProblem.setField(2, 2, 5.0);
//		System.out.println(firstProblem.getField(0, 4));
//		System.out.println(firstProblem.getField(1, 3));
//		System.out.println(firstProblem.getField(2, 2));
//		firstProblem = (SimplexProblemPrimal) SimplexLogic.addArtificialVars(firstProblem);
//		System.out.println(firstProblem.getField(0, 6));
//		System.out.println(firstProblem.getField(1, 7));
//		System.out.println(firstProblem.getField(2, 8));
//		sh.addElement(firstProblem);
//				
		//if(debug == true){System.out.println("Tableau: \n" + firstProblem.tableauToString());} 
		//if(debug == true){System.out.println("Zielfunktion: " + firstProblem.targetToString());}
		//if(debug == true){System.out.println("Basisspalten: " + Arrays.toString(firstProblem.getPivots()));}
//		
//		
//		//if(debug == true){System.out.println("HTML: "+ firstProblem.tableauToHtml());}
//
		//SimplexLogic auf SimplexProblem(e) ausführen, bis optimale Lösung gefunden, dabei Ausgabe aller Zwischenschritte
//		do{
//			SimplexProblemDual current = (SimplexProblemDual) sh.getLastElement();
//			current = SimplexLogic.simplex(current);
//
//			//Debug-Ausgabe
//			if(debug == true){System.out.println("Tableau: \n" + current.tableauToString());}
//			if(debug == true){System.out.println("Basisspalten: " + Arrays.toString(current.getPivots()));}
//			if(debug == true){System.out.println("Optimal: "+current.getOptimal());}
//			if(debug == true){System.out.println("HTML: "+current.tableauToHtml());}
//			
//			sh.addElement(current);
//		}
//		while(sh.getLastElement().getOptimal()!=true);
	}	
	
}
