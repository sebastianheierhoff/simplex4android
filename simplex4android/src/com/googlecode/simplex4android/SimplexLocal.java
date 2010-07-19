package com.googlecode.simplex4android;

import java.io.IOException;
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
		//double[][] tableau = {{0,0,-4,-0.6,1,1,0.6,-3.8},{-1,1,3,0.4,0,-1,-0.4,3.2},{0,0,0,0,0,0,0,0}};
		//double[] target = {75,15,55,15,5,0,0,0};
		//primales Problem
		//double[] target = {1,2,7,5,0,0,0};
		//double[][] tableau = {{-1,2,1,0,1,0,-7},{0,1,0,1,0,-1,3},{1,0,2,2,0,0,-8},{0,0,0,0,0,0,0}};
		//Beispiel OSI 1.
		//double[][] tableau = {{2, 2, 1, 0, 0, 300},{1, 0, 0, 1, 0, 100},{1, 4, 0, 0, 1, 360},{0, 0, 0, 0, 0, 0}};
		//double[] target = {-400, -300, 0, 0, 0, 0};
		//Beispiel OSI 2.
		//double[]target = {5000, 8000, 0, 0, 0, 0, 0};
		//double[][]tableau={{-500,-500,1,0,0,0,-5000},{-50,-100,0,1,0,0,-800},{-1,0,0,0,1,0,-2},{0,-1,0,0,0,1,-2},{0,0,0,0,0,0,0}};
		//Beispiel OSI 3.
		//double[]target = {-300,-500,0,-50,0,0,0,0,0,0};
		//double[][]tableau={{1,1,1,0,1,0,0,0,0,170},{1,1,0,0,0,1,0,0,0,150},{0,2,1,1,0,0,1,0,0,180},{-1,-3,0,0,0,0,0,1,0,-150},{0,1,-1,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,0}};
		//double[][]tableau={{1,1,1,0,1,0,0,0,0,170},{1,1,0,0,0,1,0,0,0,150},{0,2,1,1,0,0,1,0,0,180},{1,3,0,0,0,0,0,-1,0,150},{0,1,-1,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,0}};
		
		//double[][] tab = {{-1.5,3,0,0,5,-1,6},{0,1,0,5,0,-1,3},{0.5,-1,5,0,0,1,1},{0,0,0,0,0,0,0}};
		//Beispiel-Zielfunktion - Zielfunktion muss um eine 0 verlängert werden, um Zielwert berechnen zu können!!!
		//double[] target = {1,2,7,5,0,0,0}; 

		//SimplexProblem erzeugen (aus Tableau, Target, SimplexSettings)
		
		

//		double[] target = {1,2,7,5,0,0,0};
//		double[][] tableau = {{-1,2,1,0,1,0,7},{0,1,0,1,0,-1,3},{1,0,2,2,0,0,8},{0,0,0,0,0,0,0}};
//		Target t1 = new Target();
//		t1.setValue(0,1);
//		t1.setValue(1,2);
//		t1.setValue(2,7);
//		t1.setValue(3,5);
//		t1.setMinOrMax(true);
//		
////		Target t2 = new Target();
////		t2.setValue(0,7);
////		t2.setValue(1,8);
////		t2.setValue(2,9);
////		t2.setMinOrMax(false);
//		


		
//		//{-1,2,1,0,1,0,7}
//		Constraint c1 = new Constraint();
//		c1.setValue(0, -1);
//		c1.setValue(1, 2);
//		c1.setValue(2, 1);
//		c1.setValue(3, 0);
//		c1.setSign(-1);
//		c1.setTargetValue(7);
//		
//		//{0,1,0,1,0,-1,3}
//		Constraint c2 = new Constraint();
//		c2.setValue(0, 0);
//		c2.setValue(1, 1);
//		c2.setValue(2, 0);
//		c2.setValue(3, 1);
//		c2.setSign(1);
//		c2.setTargetValue(3);
//		
//		//{1,0,2,2,0,0,8}
//		Constraint c3 = new Constraint();
//		c3.setValue(0, 1);
//		c3.setValue(1, 0);
//		c3.setValue(2, 2);
//		c3.setValue(3, 2);
//		c3.setSign(0);
//		c3.setTargetValue(8);
		
//		Constraint c3 = new Constraint();
//		c3.setValue(2, 1);
//		c3.setSign(1);
//		c3.setTargetValue(20);
//		Constraint c4 = new Constraint();
//		c4.setValue(3, 1);
//		c4.setSign(-1);
//		c4.setTargetValue(40);
		
		
		//double[]target = {-300,-500,0,-50,0,0,0,0,0,0};
		//double[][]tableau={{1,1,1,0,1,0,0,0,0,170},{1,1,0,0,0,1,0,0,0,150},{0,2,1,1,0,0,1,0,0,180},{-1,-3,0,0,0,0,0,1,0,-150},{0,1,-1,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,0}};
		//double[][]tableau={{1,1,1,0,1,0,0,0,0,170},{1,1,0,0,0,1,0,0,0,150},{0,2,1,1,0,0,1,0,0,180},{1,3,0,0,0,0,0,-1,0,150},{0,1,-1,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0,0,0}};
		
		Target t1 = new Target();
		t1.setValue(0, 45);
		t1.setValue(1, -100);
		t1.setValue(2, 90);
		t1.setValue(3, -200);
//		t1.setValue(4, 5);
		t1.setMinOrMax(true);

		
		//{{0,-1,-3,-3,-2,-1},
		Constraint c1 = new Constraint();
		c1.setValue(0, 200);
		c1.setValue(1, 300);
		c1.setValue(2, 400);
		c1.setValue(3, 500);
//		c1.setValue(4, -);
		c1.setSign(-1);
		c1.setTargetValue(6000);
		
		//{-1,-5,-5,5,5,-1},{0,0,0,0,0,0}};
		Constraint c2 = new Constraint();
		c2.setValue(0, 20);
		c2.setValue(1, 30);
		c2.setValue(2, 40);
		c2.setValue(3, 50);
//		c2.setValue(4, 5);
		c2.setSign(1);
		c2.setTargetValue(600);
		
		//{0,2,1,1,0,0,1,0,0,180},
		Constraint c3 = new Constraint();
		c3.setValue(0, 100);
		c3.setValue(1, 200);
		c3.setValue(2, 300);
		c3.setValue(3, 400);
//		c3.setValue(5, 1);
		c3.setSign(1);
		c3.setTargetValue(20000);
//		//{1,3,0,0,0,0,0,-1,0,150}
//		//{-1,-3,0,0,0,0,0,1,0,-150},
//		Constraint c4 = new Constraint();
//		c4.setValue(0, -1);
//		c4.setValue(1, -3);
//		c4.setValue(2, -0);
//		c4.setValue(5, -8);
//		c4.setSign(-1);
//		c4.setTargetValue(150);
//		//{0,1,-1,0,0,0,0,0,1,0}
//		Constraint c5 = new Constraint();
//		c5.setValue(0, 0);
//		c5.setValue(1, 1);
//		c5.setValue(2, -1);
//		c5.setValue(3, 0);
//		c5.setSign(-1);
//		c5.setTargetValue(0);
		
		ArrayList<Input> input1 = new ArrayList<Input>();
		input1.add(t1);
		input1.add(c1);
		input1.add(c2);
		input1.add(c3);
//		input1.add(c4);
//		input1.add(c5);
		
//		Target t1 = new Target();
//		t1.setValue(0, 33);
//		t1.setMinOrMax(true);
//
//		
//		//{1,1,1,0,1,0,0,0,0,170},
//		Constraint c1 = new Constraint();
//		c1.setValue(0, 13);
//		c1.setValue(1, 15);
//		c1.setSign(-1);
//		c1.setTargetValue(17);
//		
//		//{1,1,0,0,0,1,0,0,0,150},
//		Constraint c2 = new Constraint();
//		c2.setValue(0, -1);
//		c2.setValue(1, 19);
//		c2.setSign(1);
//		c2.setTargetValue(16);
//		
//		ArrayList<Input> input1 = new ArrayList<Input>();
//		input1.add(t1);
//		input1.add(c1);
//		input1.add(c2);
		
//		double[][] tableauDual = {{-1,-2,-1,-3},{-2,1,-3,-4},{0,0,0,0}};
//		double[] targetDual = {2,3,4,0};
//		SimplexProblemDual problem = new SimplexProblemDual(tableauDual, targetDual);
//		
//		double[][] tableau = {{-1,-2,-1,1,0,-3},{-2,1,-3,0,1,-4},{0,0,0,0,0,0}};
//		double[] target = {2,3,4,0,0,0};
		
//		double[][] tableauDual = {{0,-1,-3,-3,-2,-1},{-1,-5,-5,5,5,-1},{0,0,0,0,0,0}};
//		double[] targetDual = {10,55,75,15,5,0};
//		SimplexProblemDual problem = new SimplexProblemDual(tableauDual, targetDual);
//		SimplexHistory[] history = new SimplexHistory[2];
//		history = SimplexLogic.twoPhaseSimplex(problem);
//		
//		SimplexHistory[]tmp = history;
//
//		// Wurden die künstlichen Variablen korrekt hinzugefügt?
//		double[][] phaseOne0 = {{-1.0,-2.0,-1.0,1.0,0.0,-3.0},{-2.0,1.0,-3.0,0.0,1.0,-4.0},{-3.0,-1.0,-4.0,0.0,0.0,-7.0}};
//		
//		System.out.println(Arrays.toString(phaseOne0[0])+"    "+ Arrays.toString(history[0].getElement(1).getTableau()[0]));
//		System.out.println(Arrays.toString(phaseOne0[1])+"    "+  Arrays.toString(history[0].getElement(1).getTableau()[1]));
//		System.out.println(Arrays.toString(phaseOne0[2])+"    "+  Arrays.toString(history[0].getElement(1).getTableau()[2]));


		double[][] tableau = {{-1,-2,-1,1,0,-3},{-2,1,-3,0,1,-4},{0,0,0,0,0,0}};
		double[] target = {2,3,4,0,0,0};		
		//double[][] tableau = {{0,-1,-3,-3,-2,1,0,-1},{-1,-5,-5,5,5,0,1,-1},{0,0,0,0,0,0,0,0}};
		//double[] target = {10,55,75,15,5,0,0,0};
		
		//SimplexProblemPrimal firstProblem = new SimplexProblemPrimal(input1);
		SimplexProblemDual firstProblem = new SimplexProblemDual(tableau, target);
		//SimplexLogic.printPhases(tmp);
		System.out.println("pivot row dual:  "+SimplexLogic.choosePivotRowDual(firstProblem));
		SimplexHistory[] tmp = SimplexLogic.twoPhaseSimplex(firstProblem);
		
//		for(int i=0;i<tmp[1].getElement(1).getNoRows();i++){
//			for(int j=0;j<tmp[1].getElement(1).getNoColumns();i++){
//				System.out.println(tmp[1].getElement(1).getRow(j)[i]);
//			}
//		}
//
		
		if(tmp[0]!=null){
			for(int i=0;i<tmp[0].size();i++){

				System.out.println("Phase 1   + i:" +i);
				if(tmp[0].getElement(i)!=null)System.out.println(tmp[0].getElement(i).tableauToHtml());
			}
		}
		if(tmp[1]!=null){
			for(int i=0;i<tmp[1].size();i++){
				System.out.println("Phase 2  + i:" +i);
				if(tmp[1].getElement(i)!=null)System.out.println(tmp[1].getElement(i).tableauToHtml());
				else System.out.println("nicht lösbar");
			}
		}else System.out.println("nicht lösbar");
		
		
//		double[][] phaseTwo2Optimal = {{0.1,2./3.,1.0,-0.0,-1./6.,-1./6.,-0.1,4./15.},{-0.1,-1./3.,0.0,1.0,5./6.,-1./6.,0.1,1./15.},{-4.0,-10.0,0.0,0.0,-5.0,-15.0,-6.0,21.0}};
//		
//		for(int i=0;i<tmp[1].getElement(3).getNoRows();i++){
//			for(int j=0;j<tmp[1].getElement(3).getNoColumns();j++){
//				tmp[1].getElement(3).setField(i, j, (Math.round(tmp[1].getElement(3).getField(i, j))));
//				phaseTwo2Optimal[i][j] = (Math.round(phaseTwo2Optimal[i][j]));
//			}
//		}
//		System.out.println(Arrays.toString(phaseTwo2Optimal[0])+ "  "+Arrays.toString(tmp[1].getElement(3).getTableau()[0]));
//		System.out.println(Arrays.toString(phaseTwo2Optimal[1])+ "  "+Arrays.toString(tmp[1].getElement(3).getTableau()[1]));
//		System.out.println(Arrays.toString(phaseTwo2Optimal[2])+ "  "+Arrays.toString(tmp[1].getElement(3).getTableau()[2]));
		
//		double[][] phaseTwo2 = {{-0.6,-4.0,-6.0,0.0,1.0,1.0,0.6,-1.6},{0.4,3.0,5.0,1.0,0.0,-1.0,-0.4,1.4},{-7.0,-30.0,-30.0,0.0,0.0,-10.0,-3.0,13.0}};
//		System.out.println(Arrays.toString(phaseTwo2[0])+"  "+ Arrays.toString(tmp[1].getElement(2).getTableau()[0]));
		
			
//		System.out.println(tmp[0].getLastElement().getOptimal());
//		ArrayList<Input> input2 = new ArrayList<Input>();
//		input2.add(t2);
//		input2.add(c3);
//		input2.add(c4);

//		InputsDb save;
//		try{
//			//save = new InputsDb();
//			//System.out.println(save.getListOfInputs().isEmpty());
//			//save.addInput(input1);
//			//save.addInput(input2);
//			System.out.println("Speichern durchgeführt!");
//		}catch(Exception e) {
//			e.printStackTrace();
//			save = null;
//			System.out.println("Fehler beim Speichern!");
//		}
//		try{
//			System.out.println();
//			System.out.println("Erstes Problem:");
//			//System.out.println(save.getInput(0).get(0));
//			//System.out.println(save.getInput(1).get(0));
//		}catch(NullPointerException npe1){
//			npe1.printStackTrace();
//		}
//
//		InputsDb read;
//		try{
//			//read = new InputsDb();
//			System.out.println();	
//			System.out.println("Laden durchgeführt!");
//		}catch(Exception e){
//			e.printStackTrace();
//			read = null;
//			System.out.println("Fehler beim Laden!");
//		}
//		try{		
//			System.out.println();			
//			System.out.println("Erstes Problem:");
//			//System.out.println(read.getInput(0).get(0));
//			//System.out.println(read.getInput(1).get(0));
//		}catch(NullPointerException npe2){
//			npe2.printStackTrace();
//		}


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
