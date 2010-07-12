package com.googlecode.simplex4android;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasse SimplexLogic - bekommt ein SimplexProblem übergeben, bearbeitete dieses und gibt ein neues SimplexProbelm zurück.
 * @author Simplex4Android
 */
public abstract class SimplexLogic {
	
	/**
	 * Fügt dem SimplexProblem gemäß der ersten Phase der Zweiphasenmethode die benötigten künstlichen Variablen hinzu.
	 * @return SimplexProblem für die Zweiphasenmethode, null, wenn keine künstlichen Variablen eingefügt werden mussten
	 */
	public static SimplexProblem addArtificialVars(SimplexProblem problem){
		//debug Infos:
		//System.out.println(problem.getPivots().length);
		//System.out.println(problem.getNoRows()-1);
		if(problem.getPivots().length==problem.getNoRows()-1){ // Anzahl der Pivotspalten entspricht der der Zeilen
			return null; // Hinzufügen von künstlichen Variablen nicht nötig
		}
		int[] pivots = findPivotRows(problem);
				
		double[] target = problem.getTarget();
		for(int i=0;i<target.length;i++){ // alle Kosten auf 0 setzen
			target[i]=0;
		}
		problem.setTarget(target);
		
		for(int i=0;i<problem.getNoRows()-1;i++){
			boolean set = true;
			for(int j=0;j<pivots.length;j++){ // Suche, ob Pivotspalte bereits enthalten
				if(i==pivots[j]){
					set = false;
				}
			}			
			if(set){
				problem.addArtificialVar(i); // Hinzufügen der benötigten künstlichen Variablen
			}
		}
		SimplexLogic.calcDeltas(problem);
		return problem;
	}
	
	/**
	 * Berechnet die delta/f-Werte des SimplexProblems.
	 * @param problem SimplexProblem, in dem delta/f-Werte berechnet werden sollen.
	 */
	public static void calcDeltaByF(SimplexProblemDual problem){
		if(problem.getOptimal()!=true){
			int row = choosePivotRowDual(problem);
			double[] deltaByF = new double[problem.getNoColumns()-1];
			double[] delta = problem.getLastRow();
			for(int j = 0; j<problem.getNoColumns()-1; j++){
				if(problem.getField(row, j)<0){
					deltaByF[j] = (delta[j] / problem.getField(row, j));
				}
				else{
					deltaByF[j] = -1;
				}
			}
			problem.setDeltaByF(deltaByF);
		}
	}

		
	/**
	 * Berechnet die delta-Werte in der letzten Zeile des SimplexTableaus
	 * @param problem zu bearbeitendes SimplexTableau
	 */
	public static void calcDeltas(SimplexProblem problem){
		int[] pivots = findPivotRows(problem);
		for(int i = 0; i<problem.getNoColumns(); i++){ //alle Spalten
			double delta = 0;
			for(int k = 0; k<problem.getNoRows()-1; k++){ // alle Zeilen
				delta += problem.getField(k,i) * problem.getTarget()[problem.getPivots()[pivots[k]]]; 
			}
			delta = delta - problem.getTarget()[i];
			problem.setField(problem.getNoRows()-1, i, delta);	
		}
	}
	
	/**
	 * Berechnet die x/f-Werte des SimplexProblems.
	 * @param problem SimplexProblem, in dem x/f-Werte berechnet werden sollen.
	 */
	public static void calcXByF(SimplexProblemPrimal problem){
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
	 * Überprüft ob Problem nach dem dualen Simplex optimal ist.
	 * 
	 * @param problem
	 * @return boolean ob optimal
	 */
	public static boolean checkDualOptimal(SimplexProblem problem){
		 if(primalValid(problem) && checkOptimal(problem)){
			 return true;
		 }
		 return false;
	}
	
//	/**
//	 * Prüft den Eingabestring einer Zielfunktion auf seine Zulässigkeit
//	 * @param s Eingabestring einer Zielfunktion
//	 * @return true, wenn Eingabestring s gültig, sonst false
//	 */
//	public static boolean checkTarget(String s){
//		
//	}
	
	/**
	 * Prüft den Teileingabestring einer Nebenbedingung auf seine Zulässigkeit
	 * @param s Teileingabestring einer Nebenbedingung
	 * @return true, wenn Eingabestring s gültig, sonst false
	 */
	public static boolean checkInput(String s){
		if(s.startsWith("-")){ 	// Wenn "-" vorhanden, dann nur an erster Stelle?
			if((s.lastIndexOf("-")!=0)){
				return false;
			}			
		}
		if(s.indexOf(".")!=-1){ // "." überhaupt enthalten?
			if(s.indexOf(".")!=s.lastIndexOf(".")){ // Mehr als ein "."?
				return false;
			}else{
				int i=s.indexOf(".");
				if(s.length() > i+1){
					if((i-1)<0 || Integer.valueOf(s.substring(i-1,i)) >9 || Integer.valueOf(s.substring(i+1,i+2)) >9){ // "." an falscher Stelle oder eins der Zeichen davor oder dahinter nicht in [0,9]
						return false;
					}
				}
				else{
					if((i-1)<0 || Integer.valueOf(s.substring(i-1,i)) >9){ // "." an falscher Stelle oder eins der Zeichen davor oder dahinter nicht in [0,9]
						return false;
					}
				}
			}
		}
		if(s.indexOf("/")!=-1){ // "/" überhaupt enthalten?
			if(!(s.indexOf("/")==s.lastIndexOf("/"))){ // Mehr als ein "/"?
				return false;
			}else{
				int i=s.indexOf("/");
				try{
					if(Integer.valueOf(s.substring(i-1,i))>9 || Integer.valueOf(s.substring(i+1,i+2))>9 || Integer.valueOf(s.substring(i+1,i+2))==0){ // "/" an falscher Stelle oder Zeichen davor nicht in [0,9] oder Zeichen dahinter nicht in [1,9]
						return false;
					}
				}catch(Exception e){
					return false;
				}
				
			}
		}
		return true;
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
		return true;
	}
	
	/**
	 * Findet die neue Pivotspalte und gib diese aus (wählt am weitesten links stehendes Element).
	 * @param problem SimplexProblem, in dem die neue Pivotspalte gefunden werden soll.
	 * @return neue Pivotspalte, "-1", falls keine zulässige Spalte gefunden wurde.
	 */
	public static int choosePivotColumn(SimplexProblem problem){
		int column = -1;
		for(int i=problem.getNoColumns()-2; i>-1; i--){
			if(problem.getField(problem.getNoRows()-1,i)>0.0){
				column = i;
			}
		}
		return column;
	}	
		
	/**
	 * Findet die Spalte, die in die Basis geht und gibt diese aus (wählt am weitesten links stehendes Element).
	 * @param problem SimplexProblem, in dem die neue Pivotzeile gefunden werden soll.
	 * @return Spalte, die in die Basis geht, "-1", falls keine zulässige Spalte gefunden wurde.
	 */
	public static int choosePivotColumnDual(SimplexProblemDual problem){
		int column = -1;
		for(int i = problem.getNoColumns()-2; i>-1; i--){
			if(problem.getDeltaByF()[i]>0){
				column = i;
			}
		}
		return column;
	}
	
	/**
	 * Findet die neue Pivotzeile und gib diese aus.
	 * Bei mehreren Möglichkeiten wird stets die Zeile mit dem kleinsten Index gewählt.
	 * @param problem SimplexProblem, in dem die neue Pivotzeile gefunden werden soll.
	 * @return neue Pivotzeile
	 */
	public static int choosePivotRow(SimplexProblemPrimal problem){
		int row = -1;
		double min = Double.MAX_VALUE;
		for(int i=problem.getXByF().length-1; i>-1; i--){
			if(problem.getXByF()[i]<=min && problem.getXByF()[i] > 0){
				min = problem.getXByF()[i];
				row = i;				
			}
		}
		return row;
	}
	
	/**
	 * Wählt den kleinsten, negativen x-Wert aus und bestimmt dadurch die Pivotzeile.
	 * Bei mehreren Möglichkeiten wird stets die Zeile mit dem kleinsten Index gewählt.
	 * @param problem SimplexProblem, in dem die neue Pivotspalte gefunden werden soll.
	 * @return Zeile in der nach neuer Pivotspalte gesucht wird
	 */
	public static int choosePivotRowDual(SimplexProblem problem){
		int row = -1;
		double min = Double.MAX_VALUE;
		for(int i=problem.getNoRows()-2; i>-1; i--){
			if(problem.getTableau()[i][problem.getNoColumns()-1]<=min && problem.getTableau()[i][problem.getNoColumns()-1]<0){
				min = problem.getTableau()[i][problem.getNoColumns()-1];
				row = i;
			}
		}
		return row;
	}
	
	/**
	 * Stellt fest ob Problem dual zulässig ist.
	 * 
	 * @param SimplexProblem
	 * @return boolean ob zulässig oder nicht
	 */
	public static boolean dualValid(SimplexProblem problem){
		for(int i=0;i<problem.getLastRow().length;i++){
			if(problem.getLastRow()[i]<0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Findet die Einsen in den Pivotspalten und gibt deren Zeilenindizes zurück. 
	 * An Stelle i des zurückgegebenen Arrays befindet sich der Zeilenindex der Pivotspalte an Index i der Pivottabelle im SimplexProblem.
	 * @param problem SimpexProblem, für das die Zeilenindizes gefunden werden sollen.
	 * @return Zeilenindizes der Einsen der Pivotspalten
	 */
	public static int[] findPivotRows(SimplexProblem problem){
		int [] pivots = new int[problem.getPivots().length];
		for(int i=0;i<problem.getPivots().length;i++){ // Zeilenindizes der Einsen in den Pivotspalten finden und einspeichern
			double[] column = problem.getColumn(problem.getPivots()[i]);
				for(int j=0;j<column.length;j++){ // Durch die Spalten
					if(column[j]==1){ // Eins gefunden
						pivots[i] = j;
					}
				}
			}
		return pivots;
	}
	
	/**
	 * Gibt ein Array mit den Pivotspalten aus.
	 * @param problem SimplexProblem, für das die Pivotspalten bestimmt werden sollen.
	 * @return Array mit den Pivotspalten
	 */
	public static void findPivots(SimplexProblem problem){
		ArrayList<Integer> pivots = new ArrayList<Integer>();
		for(int i = 0; i<problem.getNoColumns(); i++){ //For-Schleife, durchläuft alle Spalten
			int posOfOne = 0;// Speichert die Position der ersten gefundenen 1 in einer Spalte
			int noo = 0;//Anzahl Einsen
			for(int k = 0; k<problem.getNoRows()-1; k++){ //For-Schleife, durchläuft alle Zeilen
				if(problem.getField(k,i) != 0.0 && problem.getField(k,i) != 1.0){
					noo = 0;
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
				pivots.add(i);
			}
		}
		problem.setPivots(pivots);
	}
	/**
	 * Führt für ein gegebenes Pivotelement an der Stelle (zeile,spalte) im SimplexTableau den Gauß-Algorithmus durch.
	 * @param zeile Index der Zeile des Pivotelements
	 * @param spalte Index der Spalte des Pivotelements
	 * @return mit dem Gauß-Algorithmus bearbeitetes SimplexTableau
	 * @throws IOException falls Element an (zeile,spalte) gleich Null oder POSITIVE_INFINITY bzw. NEGATIVE_INFINITY
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
	 * Stellt fest ob Problem primal zulässig ist, also alle x-Werte >=0 sind.
	 * 
	 * @param SimplexProblem
	 * @return boolean ob zulässig oder nicht
	 */
	public static boolean primalValid(SimplexProblem problem){
		double[] c = problem.getLastColumn();
		for(int i=0;i<c.length-1;i++){
			if(c[i]<0){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Führt für das übergebene SimplexProblem die 2. Phase des Simplex-Algorithmus durch.
	 * Vorher werden die delta und die deltaByF-Werte berechnet.
	 * @return bearbeitetes SimplexProblem
	 */
	public static SimplexProblemDual simplex(SimplexProblemDual problem){
		calcDeltas(problem);
		calcDeltaByF(problem);
		try {
			if(problem.getOptimal()!= true){				
				SimplexProblemDual spd = (SimplexProblemDual)gauss(problem, choosePivotRowDual(problem), choosePivotColumnDual(problem));
				findPivots(spd);
				if(checkDualOptimal(spd)){
					problem.setOptimal();
				}
				calcDeltaByF(spd);
				return spd;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Führt für das übergebene SimplexProblem die 2. Phase des Simplex-Algorithmus durch.
	 * Vorher werden die delta und die xByF-Werte berechnet.
	 * @return bearbeitetes SimplexProblem
	 */
	public static SimplexProblemPrimal simplex(SimplexProblemPrimal problem){
		calcDeltas(problem);
		calcXByF(problem);
		try {
			if(problem.getOptimal()!= true){				
				SimplexProblemPrimal spp = (SimplexProblemPrimal)gauss(problem, choosePivotRow(problem), choosePivotColumn(problem));
				findPivots(spp);
				if(checkOptimal(spp)){
					problem.setOptimal();
				}				
				calcXByF(spp);
				return spp;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * sh.add() muss noch ersetzt werden durch einen Konstruktor für neues Objekt
	 * damit nicht immer das gleiche Objekt drinliegt!!!!!!
	 * Führt ZweiphasenSimplex durch
	 * @param problem
	 * @return SimplexHistory mit dem kompletten Verlauf inkl. der beiden Phasen
	 */
	public static SimplexHistory twoPhaseSimplex(SimplexProblemDual problem){ 
		
		System.out.println(problem.tableauToString());
		
		SimplexHistory sh = new SimplexHistory();
		findPivots(problem);
		calcDeltas(problem);
		calcDeltaByF(problem);
		sh.addElement(problem.clone());
		if(addArtificialVars(problem)!=null){		//wenn künstliche Variablen hinzugefügt wurden
			SimplexProblemDual phaseOneProblem = (SimplexProblemDual)addArtificialVars(problem);
			
			System.out.println(phaseOneProblem.tableauToString());
			
			sh.addElement(phaseOneProblem.clone());
			findPivots(phaseOneProblem);
			calcDeltas(phaseOneProblem);
			calcDeltaByF(phaseOneProblem);
			sh.addElement(phaseOneProblem.clone());
			do{
				SimplexProblemDual current = (SimplexProblemDual) sh.getLastElement();
				current = SimplexLogic.simplex(current);
				sh.addElement(current.clone());
			}
			while(sh.getLastElement().getOptimal()!=true);

			//Problem zurückbauen: alte Zielfuntion wiederübernehmen, künstliche Variablen rausschmeißen
			SimplexProblem firstProblem = sh.getFirstElement();
			SimplexProblem problemEndFirstPhase = sh.getLastElement();
			double[] targetFirstPhase = problemEndFirstPhase.getTarget();
			double[][] tableau = firstProblem.getTableau();  //nur um garantiert die richtige Größe zu bekommen :)
			//solange nullen in der Zielfunktion stehen werden die Spalten kopiert 
			SimplexProblemDual phaseTwoProblem = new SimplexProblemDual(tableau, firstProblem.getTarget());
			for(int i=0;i<targetFirstPhase.length;i++){
				if(targetFirstPhase[i]==0){
					phaseTwoProblem.setColumn(problemEndFirstPhase.getColumn(i), i);
				}else{
					i = targetFirstPhase.length;
				}
			}
			sh.addElement(phaseTwoProblem.clone());
		}

		//hier gehts weiter falls die erste Phase nicht benötigt wurde
		SimplexProblemDual phaseTwoProblem = (SimplexProblemDual)sh.getLastElement();
		findPivots(phaseTwoProblem);
		calcDeltas(phaseTwoProblem);
		calcDeltaByF(phaseTwoProblem);
		
		SimplexProblemDual phaseTwoProblemDual = (SimplexProblemDual)phaseTwoProblem;
		calcDeltaByF(phaseTwoProblemDual);
		sh.addElement(phaseTwoProblemDual.clone());
		//Simplex durchführen bis optimal
		do{
			SimplexProblemDual current = (SimplexProblemDual) sh.getLastElement();
			current = SimplexLogic.simplex(current);
			sh.addElement(current);
		}
		while(sh.getLastElement().getOptimal()!=true);

		return sh;

	}

	public static SimplexHistory twoPhaseSimplex(SimplexProblemPrimal problem){ 
		SimplexHistory sh = new SimplexHistory();
		sh.addElement(problem.clone());
		boolean variant; //variant gibt an mit welche Methode das Problem gelöst werden soll. true für primal, false für dual
		String nameOfClass = problem.getClass().getName();
		if(nameOfClass=="SimplexProblemDual"){
			variant = false;
		}else{
			variant = true;
		}
		if(addArtificialVars(problem)!=null){		//wenn künstliche Variablen hinzugefügt wurden
			if(variant == true){
				SimplexProblemPrimal phaseOneProblem = (SimplexProblemPrimal)addArtificialVars(problem);
				sh.addElement(phaseOneProblem.clone());
				
				//debug
				//System.out.println(phaseOneProblem.tableauToString());
				
				findPivots(phaseOneProblem);
				calcDeltas(phaseOneProblem);
				calcXByF(phaseOneProblem);
				sh.addElement(phaseOneProblem.clone());
				do{
					SimplexProblemPrimal current = (SimplexProblemPrimal) sh.getLastElement();
					current = SimplexLogic.simplex(current);
					sh.addElement(current.clone());
				}
				while(sh.getLastElement().getOptimal()!=true);
			}else{
				SimplexProblemDual phaseOneProblem = (SimplexProblemDual)addArtificialVars(problem);
				sh.addElement(phaseOneProblem.clone());
				findPivots(phaseOneProblem);
				calcDeltas(phaseOneProblem);
				calcDeltaByF(phaseOneProblem);
				sh.addElement(phaseOneProblem.clone());
				do{
					SimplexProblemDual current = (SimplexProblemDual) sh.getLastElement();
					current = SimplexLogic.simplex(current);
					sh.addElement(current.clone());
				}
				while(sh.getLastElement().getOptimal()!=true);
			}
			//Problem zurückbauen: alte Zielfuntion wiederübernehmen, künstliche Variablen rausschmeißen
			SimplexProblem firstProblem = sh.getFirstElement();
			SimplexProblem problemEndFirstPhase = sh.getLastElement();
			double[] targetFirstPhase = problemEndFirstPhase.getTarget();
			double[][] tableau = firstProblem.getTableau();  //nur um garantiert die richtige Größe zu bekommen :)
			if(variant==true){	//solange nullen in der Zielfunktion stehen werden die Spalten kopiert 
				SimplexProblemPrimal phaseTwoProblem = new SimplexProblemPrimal(tableau, firstProblem.getTarget());
				for(int i=0;i<targetFirstPhase.length;i++){
					if(targetFirstPhase[i]==0){
						phaseTwoProblem.setColumn(problemEndFirstPhase.getColumn(i), i);
					}else{
						i = targetFirstPhase.length;
					}
				}
				sh.addElement(phaseTwoProblem.clone());
			}else{	//hier der Spaß nochmal für das duale Prolbem. Geht bestimmt auch in einem 
				SimplexProblemDual phaseTwoProblem = new SimplexProblemDual(tableau, firstProblem.getTarget());
				for(int i=0;i<targetFirstPhase.length;i++){
					if(targetFirstPhase[i]==0){
						phaseTwoProblem.setColumn(problemEndFirstPhase.getColumn(i), i);
					}else{
						i = targetFirstPhase.length;
					}
				}
				sh.addElement(phaseTwoProblem.clone());
			}
		} //hier gehts weiter falls die erste Phase nicht benötigt wurde
		SimplexProblem phaseTwoProblem = sh.getLastElement();
		findPivots(phaseTwoProblem);
		calcDeltas(phaseTwoProblem);
		//erneute Unterteilung in Verlauf für duales und primales Problem
		if(variant==true){
			SimplexProblemPrimal phaseTwoProblemPrimal = (SimplexProblemPrimal)phaseTwoProblem;
			calcXByF(phaseTwoProblemPrimal);
			sh.addElement(phaseTwoProblemPrimal.clone());
			//Simplex durchführen bis optimal
			do{
				SimplexProblemPrimal current = (SimplexProblemPrimal) sh.getLastElement();
				current = SimplexLogic.simplex(current);
				sh.addElement(current);
			}
			while(sh.getLastElement().getOptimal()!=true);

		}else{
			SimplexProblemDual phaseTwoProblemDual = (SimplexProblemDual)phaseTwoProblem;
			calcDeltaByF(phaseTwoProblemDual);
			sh.addElement(phaseTwoProblemDual.clone());
			//Simplex durchführen bis optimal
			do{
				SimplexProblemDual current = (SimplexProblemDual) sh.getLastElement();
				current = SimplexLogic.simplex(current);
				sh.addElement(current);
			}
			while(sh.getLastElement().getOptimal()!=true);
		}
		return sh;
		
	}

}
