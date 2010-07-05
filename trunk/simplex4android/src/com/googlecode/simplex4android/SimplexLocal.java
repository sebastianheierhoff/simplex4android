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
		double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}}; 
		//Beispiel-Zielfunktion - Zielfunktion muss um eine 0 verlängert werden, um Zielwert berechnen zu können!!!
		int[] target = {1,2,7,5,0,0,0}; 
		
		//SimplexProblem erzeugen (aus Tableau, Target, SimplexSettings)
		SimplexProblemPrimal firstProblem = new SimplexProblemPrimal(tableau, target);
		if(debug == true){System.out.println("Tableau: \n" + firstProblem.tableauToString());} 
		if(debug == true){System.out.println("Zielfunktion: " + firstProblem.targetToString());}
		//if(debug == true){System.out.println("HTML: "+ firstProblem.tableauToHtml());}

		//SimplexProblem in History einfügen
		sh.addElement(firstProblem);
		
		double[] add = {5,10,0,0,0,0,-1,20};
		double[] e = {5,10,0,0,0,0,1,20};
		ArrayList<Double> row = new ArrayList<Double>();
		for(int i=0;i<add.length;i++){
			row.add(new Double(add[i]));
		}
		firstProblem.addRow(row);
		boolean bool = true;
		double[] inserted = firstProblem.getRow(firstProblem.getTableau().length-2);
		for(int i=0;i<inserted.length;i++){
			if(inserted[i]!=e[i]){
				bool = false;
			}
		}		
		System.out.println(bool);
	
		
		if(debug == true){System.out.println("Tableau: \n" + firstProblem.tableauToString());} 
		if(debug == true){System.out.println("Zielfunktion: " + firstProblem.targetToString());}
		
		
		//if(debug == true){System.out.println("HTML: "+ firstProblem.tableauToHtml());}

		//SimplexLogic auf SimplexProblem(e) ausführen, bis optimale Lösung gefunden, dabei Ausgabe aller Zwischenschritte
		do{
			SimplexProblemPrimal current = (SimplexProblemPrimal) sh.getLastElement();
			current = SimplexLogic.simplex(current);

			//Debug-Ausgabe
			if(debug == true){System.out.println("Tableau: \n" + current.tableauToString());}
			if(debug == true){System.out.println("Basisspalten: " + Arrays.toString(current.getPivots()));}
			if(debug == true){System.out.println("Optimal: "+current.getOptimal());}
			if(debug == true){System.out.println("HTML: "+current.tableauToHtml());}
			
			sh.addElement(current);
		}
		while(sh.getLastElement().getOptimal()!=true);
	}	
}
