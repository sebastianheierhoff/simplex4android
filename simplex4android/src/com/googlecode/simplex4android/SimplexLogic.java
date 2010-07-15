package com.googlecode.simplex4android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

/**
 * Klasse SimplexLogic - bekommt ein SimplexProblem �bergeben, bearbeitete dieses und gibt ein neues SimplexProbelm zur�ck.
 * @author Simplex4Android
 */
public abstract class SimplexLogic {

	/**
	 * F�gt dem SimplexProblem gem�� der ersten Phase der Zweiphasenmethode die ben�tigten k�nstlichen Variablen hinzu.
	 * @return SimplexProblem f�r die Zweiphasenmethode, null, wenn keine k�nstlichen Variablen eingef�gt werden mussten
	 */
	public static SimplexProblemPrimal addArtificialVars(SimplexProblemPrimal problem){
		if(problem.getPivots().length==problem.getNoRows()-1){ // Anzahl der Pivotspalten entspricht der der Zeilen
			return null; // Hinzuf�gen von k�nstlichen Variablen nicht n�tig
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
				problem.addArtificialVar(i); // Hinzuf�gen der ben�tigten k�nstlichen Variablen
			}
		}
		calcDeltas(problem);
		findPivots(problem);
		calcXByF(problem);
		SimplexProblemPrimal sp = problem;
		return sp;
	}

	/**
	 * F�gt dem SimplexProblem gem�� der ersten Phase der Zweiphasenmethode die ben�tigten k�nstlichen Variablen hinzu.
	 * @return SimplexProblem f�r die Zweiphasenmethode, null, wenn keine k�nstlichen Variablen eingef�gt werden mussten
	 */
	public static SimplexProblemDual addArtificialVars(SimplexProblemDual problem){
		if(problem.getPivots().length==problem.getNoRows()-1){ // Anzahl der Pivotspalten entspricht der der Zeilen
			return null; // Hinzuf�gen von k�nstlichen Variablen nicht n�tig
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
				problem.addArtificialVar(i); // Hinzuf�gen der ben�tigten k�nstlichen Variablen
			}
		}
		calcDeltas(problem);
		findPivots(problem);
		calcDeltaByF(problem);
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
		int[] pivots = findPivotsSorted(problem);
		for(int i = 0; i<problem.getNoColumns(); i++){ //alle Spalten
			double delta = 0;
			for(int k = 0; k<problem.getNoRows()-1; k++){ // alle Zeilen
				delta += problem.getField(k,i) * problem.getTarget()[pivots[k]]; 
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
			double[] x = problem.getLastColumn();
			for(int i = 0; i<problem.getNoRows()-1; i++){
				xByF[i] = (x[i] / problem.getField(i, pivotColumn));
			}
			problem.setXByF(xByF);
		}
	}

	/**
	 * �berpr�ft ob Problem nach dem dualen Simplex optimal ist.
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
	//	 * Pr�ft den Eingabestring einer Zielfunktion auf seine Zul�ssigkeit
	//	 * @param s Eingabestring einer Zielfunktion
	//	 * @return true, wenn Eingabestring s g�ltig, sonst false
	//	 */
	//	public static boolean checkTarget(String s){
	//		
	//	}

	/**
	 * Pr�ft den Teileingabestring einer Nebenbedingung auf seine Zul�ssigkeit
	 * @param s Teileingabestring einer Nebenbedingung
	 * @return true, wenn Eingabestring s g�ltig, sonst false
	 */
	public static boolean checkInput(String s){
		if(s.startsWith("-")){ 	// Wenn "-" vorhanden, dann nur an erster Stelle und mit mind. einer Ziffer dahinter?
			if((s.lastIndexOf("-")!=0) || s.length()<2){
				return false;
			}			
		}
		if(s.indexOf(".")!=-1){ // "." �berhaupt enthalten?
			if(s.indexOf(".")!=s.lastIndexOf(".") || s.indexOf("/") != -1){ // Mehr als ein "." oder gleichzeitig ein "/" und "." enthalten?
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
		if(s.indexOf("/")!=-1){ // "/" �berhaupt enthalten?
			if(!(s.indexOf("/")==s.lastIndexOf("/")) || s.indexOf(".") != -1){ // Mehr als ein "/" oder gleichzeitig ein "/" und "." enthalten?
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
	 * �berpr�ft anhand der Delta-Werte, ob das aktuelle Tableau des SimplexProblem optimal ist, und setzt dieses dann ggf. optimal.
	 * @param problem zu pr�fendes SimplexProblem
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
	 * Findet die neue Pivotspalte und gib diese aus (w�hlt am weitesten links stehendes Element).
	 * @param problem SimplexProblem, in dem die neue Pivotspalte gefunden werden soll.
	 * @return neue Pivotspalte, "-1", falls keine zul�ssige Spalte gefunden wurde.
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
	 * Findet die Spalte, die in die Basis geht und gibt diese aus (w�hlt am weitesten links stehendes Element).
	 * @param problem SimplexProblem, in dem die neue Pivotzeile gefunden werden soll.
	 * @return Spalte, die in die Basis geht, "-1", falls keine zul�ssige Spalte gefunden wurde.
	 */
	public static int choosePivotColumnDual(SimplexProblemDual problem){
		int column = -1;
		double min = Double.MAX_VALUE;
		for(int i = problem.getNoColumns()-2; i>-1; i--){
			if(problem.getDeltaByF()[i]>0&&problem.getDeltaByF()[i]<min){
				column = i;
				min = problem.getDeltaByF()[i];
			}
		}
		return column;
	}

	/**
	 * Findet die neue Pivotzeile und gib diese aus.
	 * Bei mehreren M�glichkeiten wird stets die Zeile mit dem kleinsten Index gew�hlt.
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
	 * W�hlt den kleinsten, negativen x-Wert aus und bestimmt dadurch die Pivotzeile.
	 * Bei mehreren M�glichkeiten wird stets die Zeile mit dem kleinsten Index gew�hlt.
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
	 * Stellt fest ob Problem dual zul�ssig ist.
	 * 
	 * @param SimplexProblem
	 * @return boolean ob zul�ssig oder nicht
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
	 * Findet die Einsen in den Pivotspalten und gibt deren Zeilenindizes zur�ck. 
	 * An Stelle i des zur�ckgegebenen Arrays befindet sich der Zeilenindex der Pivotspalte an Index i der Pivottabelle im SimplexProblem.
	 * @param problem SimpexProblem, f�r das die Zeilenindizes gefunden werden sollen.
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
	 * @param problem SimplexProblem, f�r das die Pivotspalten bestimmt werden sollen.
	 * @return Array mit den Pivotspalten
	 */
	public static void findPivots(SimplexProblem problem){
		ArrayList<Integer> pivots = new ArrayList<Integer>();
		for(int i = 0; i<problem.getNoColumns(); i++){ //For-Schleife, durchl�uft alle Spalten
			int posOfOne = 0;// Speichert die Position der ersten gefundenen 1 in einer Spalte
			int noo = 0;//Anzahl Einsen
			for(int k = 0; k<problem.getNoRows()-1; k++){ //For-Schleife, durchl�uft alle Zeilen
				if(problem.getField(k,i) != 0.0 && problem.getField(k,i) != 1.0){
					noo = 0;
					break; //Abbruch des Durchlaufs, falls die Zahl an Stelle k != 0 bzw. != 1
				}
				else{
					if(problem.getField(k,i) == 1){ 
						posOfOne = k;
						noo++; //Anzahl Einsen um 1 erh�hen, falls Zelle[k][i] = 1
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
	 * Gibt ein int-Array mit den Pivotspalten aus. 
	 * An Stelle i steht, in welcher Spalte eine 1 der Pivotspalte steht.
	 * quasi die methode oben dr�ber nur in richtig
	 * @param problem SimplexProblem, f�r das die Pivotspalten bestimmt werden sollen.
	 * @return Array mit den Pivotspalten
	 */
	public static int[] findPivotsSorted(SimplexProblem problem){
		int[] pivots = new int[problem.getNoRows()-1];
		for(int i = 0; i<problem.getNoColumns(); i++){ //For-Schleife, durchl�uft alle Spalten
			int posOfOne = 0;// Speichert die Position der ersten gefundenen 1 in einer Spalte
			int noo = 0;//Anzahl Einsen
			for(int k = 0; k<problem.getNoRows()-1; k++){ //For-Schleife, durchl�uft alle Zeilen
				if(problem.getField(k,i) != 0.0 && problem.getField(k,i) != 1.0){
					noo = 0;
					break; //Abbruch des Durchlaufs, falls die Zahl an Stelle k != 0 bzw. != 1
				}
				else{
					if(problem.getField(k,i) == 1){ 
						posOfOne = k;
						noo++; //Anzahl Einsen um 1 erh�hen, falls Zelle[k][i] = 1
					}
					if(noo > 1){
						break; //Abbruch, falls mehr als eine 1 in einer Spalte gefunden wird
					}
				}
			}
			if(noo == 1){
				pivots[posOfOne]= i;
			}
		}
		return pivots;
	}
	/**
	 * F�hrt f�r ein gegebenes Pivotelement an der Stelle (zeile,spalte) im SimplexTableau den Gau�-Algorithmus durch.
	 * @param zeile Index der Zeile des Pivotelements
	 * @param spalte Index der Spalte des Pivotelements
	 * @return mit dem Gau�-Algorithmus bearbeitetes SimplexTableau
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
	 * Stellt fest ob Problem primal zul�ssig ist, also alle x-Werte >=0 sind.
	 * 
	 * @param SimplexProblem
	 * @return boolean ob zul�ssig oder nicht
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
	 * F�hrt f�r das �bergebene SimplexProblem die 2. Phase des Simplex-Algorithmus durch.
	 * Vorher werden die delta und die deltaByF-Werte berechnet.
	 * @return bearbeitetes SimplexProblem
	 * @throws DataFormatException Problem kann primal weiterbearbeitet werden
	 * @throws IOException	Problem nicht l�sbar
	 */
	public static SimplexProblemDual simplex(SimplexProblemDual problem)throws IOException, DataFormatException{
		if(checkDualOptimal(problem)){
			problem.setOptimal();
		}else{
			if(!solveableDual(problem)){
				if(!solveablePrimal(problem)){
					throw new IOException ("Das Problem ist nicht l�sbar!");
				}else{
					throw new DataFormatException ("Typ des Problems muss gewechselt werden");
				}
			}
		}
		try {
			if(problem.getOptimal()!= true){				
				SimplexProblemDual spp = (SimplexProblemDual)gauss(problem, choosePivotRowDual(problem), choosePivotColumnDual(problem));
				findPivots(spp);
				if(checkDualOptimal(spp)){
					problem.setOptimal();
				}				
				calcDeltaByF(spp);
				return spp;
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * F�hrt f�r das �bergebene SimplexProblem die 2. Phase des Simplex-Algorithmus durch.
	 * Vorher werden die delta und die xByF-Werte berechnet.
	 * @return bearbeitetes SimplexProblem
	 * @throws IOException wenn das Problem nicht l�sbar ist.
	 * @throws DataFormatException Problem kann in dualer Form weiterbearbeitet werden
	 */
	public static SimplexProblemPrimal simplex(SimplexProblemPrimal problem)throws IOException, DataFormatException{
		if(checkOptimal(problem)){
			problem.setOptimal();
		}else{
			if(!solveablePrimal(problem)){
				if(!solveableDual(problem)){
					throw new IOException ("Das Problem ist nicht l�sbar!");
				}else{
					throw new DataFormatException ("Typ des Problems muss gewechselt werden");
				}

			}
		}
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
	 * Stellt fest, ob ein primales Problem im aktuellen Zustand l�sbar ist
	 * @param problem Primales Simplexproblem
	 * @return boolean, der angibt, ob das Problem l�sbar ist
	 */
	public static boolean solveablePrimal(SimplexProblem problem){
		double[] deltas = problem.getLastRow();
		for(int i=0;i<deltas.length;i++){
			if(deltas[i]>0){
				for(int j=0;j<problem.getColumn(i).length;j++){
					if(problem.getColumn(i)[j]>0)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Stellt fest, ob ein duales Problem im aktuellen Zustand l�sbar ist
	 * @param problem Primales Simplexproblem
	 * @return boolean, der angibt, ob das Problem l�sbar ist
	 */
	public static boolean solveableDual(SimplexProblem problem){
		double[] x = problem.getLastColumn();
		for(int i=0;i<x.length;i++){
			if(x[i]<0){
				for(int j=0;j<problem.getRow(i).length;j++){
					if(problem.getRow(i)[j]<0)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * F�hrt ZweiphasenSimplex durch
	 * @param problem in dualer Form
	 * @return SimplexHistory[] der L�nge zwei mit dem kompletten Verlauf inkl. der beiden Phasen an den Stellen 0 und 1. Wenn keine erste Phase ben�tigt wurde ist Index 0==null
	 */
	public static SimplexHistory[] twoPhaseSimplex(SimplexProblemDual problem){ 
		SimplexHistory[] phases = new SimplexHistory[2];
		phases[0] = new SimplexHistory();
		phases[1] = new SimplexHistory();
		findPivots(problem);
		problem.setDeltaByF(initializDeltaByFwithNull(problem));
		phases[0].addElement(problem.clone());
		SimplexProblemDual tmp = addArtificialVars(problem); 
		if(tmp!=null){		//wenn k�nstliche Variablen hinzugef�gt wurden
			calcDeltas(tmp);
			calcDeltaByF(tmp);
			phases[0].addElement(tmp.clone());
			phases = firstPhaseDual(phases);
		}else{
			phases[0] = null;
			phases[1].addElement(problem.clone());
			phases = secondPhaseDual(phases);
		}
		return phases;
	}

	/**
	 * F�hrt ZweiphasenSimplex durch
	 * @param problem in primaler Form
	 * @return SimplexHistory[] der L�nge zwei mit dem kompletten Verlauf inkl. der beiden Phasen an den Stellen 0 und 1. Wenn keine erste Phase ben�tigt wurde ist Index 0==null
	 * @throws IOException falls das Problem nicht l�sbar ist.
	 */
	public static SimplexHistory[] twoPhaseSimplex(SimplexProblemPrimal problem){ 
		SimplexHistory[] phases = new SimplexHistory[2];
		phases[0] = new SimplexHistory();
		phases[1] = new SimplexHistory();
		findPivots(problem);
		problem.setXByF(initializXByFwithNull(problem));
		phases[0].addElement(problem.clone());
		SimplexProblemPrimal tmp = addArtificialVars(problem); 
		if(tmp!=null){		//wenn k�nstliche Variablen hinzugef�gt wurden
			calcDeltas(tmp);
			calcXByF(tmp);
			phases[0].addElement(tmp.clone());
			// ab hier eigene Methode(firstPhasePrimal), damit man hier wieder eintsteigen kann nach Transformation
			phases = firstPhasePrimal(phases);
		}else{
			phases[0] = null;
			phases[1].addElement(problem.clone());
			phases = secondPhasePrimal(phases);
		}
		//		for(int i=0;i<phases[0].size();i++){
		//			System.out.println("Phase 1");
		//			System.out.println(phases[0].getElement(i).tableauToHtml());
		//			//System.out.println(i +" i");
		//		}
		//		for(int i=0;i<phases[1].size();i++){
		//			System.out.println("Phase 2");
		//			System.out.println(phases[1].getElement(i).tableauToHtml());
		//		}
		return phases;
	}


	/**
	 * �berf�hrt ein duales Problem aus der 1. Phase in das Problem f�r die zweite Phase:
	 * Konkret: es entfernt die k�nstlichen Variablen und holt die alte zielfunktion
	 * @param firstProblem	Ursprungsproblem ohne k�nstliche Variablen
	 * @param problemEndFirstPhase Problem mit k�nstlichen Variablen nach Ende der 1.Phase
	 * @return Problem f�r den Start der 2. Phase
	 */
	public static SimplexProblemDual transitionPhasesDual(SimplexProblem firstProblem, SimplexProblem problemEndFirstPhase){
		double[] targetFirstPhase = problemEndFirstPhase.getTarget();
		double[][] tableau = firstProblem.getTableau();  //nur um garantiert die richtige Gr��e zu bekommen :)
		//solange nullen in der Zielfunktion stehen werden die Spalten kopiert 
		SimplexProblemDual phaseTwoProblem = new SimplexProblemDual(tableau, firstProblem.getTarget());
		for(int i=0;i<targetFirstPhase.length;i++){
			if(targetFirstPhase[i]==0){
				phaseTwoProblem.setColumn(problemEndFirstPhase.getColumn(i), i);
			}else{
				i = targetFirstPhase.length;
			}
		}
		phaseTwoProblem.setColumn(problemEndFirstPhase.getLastColumn(), firstProblem.getTarget().length-1);
		calcDeltas(phaseTwoProblem);
		findPivots(phaseTwoProblem);
		if(checkOptimal(phaseTwoProblem)){
			phaseTwoProblem.setOptimal();
			calcDeltaByF(phaseTwoProblem);
		}		
		return phaseTwoProblem;
	}

	/**
	 * �berf�hrt ein primales Problem aus der 1. Phase in das Problem f�r die zweite Phase.
	 * Konkret: es entfernt die k�nstlichen Variablen und f�gt die alte Zielfunktion wieder ein
	 * @param firstProblem	Ursprungsproblem ohne k�nstliche Variablen
	 * @param problemEndFirstPhase Problem mit k�nstlichen Variablen nach Ende der 1.Phase
	 * @return Problem f�r den Start der 2. Phase
	 */
	public static SimplexProblemPrimal transitionPhasesPrimal(SimplexProblem firstProblem, SimplexProblem problemEndFirstPhase){
		double[] targetFirstPhase = problemEndFirstPhase.getTarget();
		double[][] tableau = firstProblem.getTableau();  //nur um garantiert die richtige Gr��e zu bekommen :)
		//solange nullen in der Zielfunktion stehen werden die Spalten kopiert 
		SimplexProblemPrimal phaseTwoProblem = new SimplexProblemPrimal(tableau, firstProblem.getTarget());
		for(int i=0;i<targetFirstPhase.length;i++){
			if(targetFirstPhase[i]==0){
				phaseTwoProblem.setColumn(problemEndFirstPhase.getColumn(i), i);
			}else{
				i = targetFirstPhase.length;
			}
		}
		phaseTwoProblem.setColumn(problemEndFirstPhase.getLastColumn(), firstProblem.getTarget().length-1);
		calcDeltas(phaseTwoProblem);
		findPivots(phaseTwoProblem);
		if(checkOptimal(phaseTwoProblem)){
			phaseTwoProblem.setOptimal();
			calcXByF(phaseTwoProblem);
		}		
		return phaseTwoProblem;
	}

	/**
	 * F�llt xByF mit Nullen
	 * @return xByF
	 */
	public static double[] initializXByFwithNull(SimplexProblemPrimal problem){
		double[] tmpXByF = new double[problem.getNoRows()];
		for(int i=0;i<tmpXByF.length;i++){
			tmpXByF[i]=0;
		}
		return tmpXByF;
	}

//	/**
//	 * erstellt ein int[] komplett mit nullen in der gr��er von pivots
//	 * @param problem
//	 * @return int[] in Gr��e von pivots mit nullen
//	 */	
//	public static int[] initializePivotsWithNull(SimplexProblem problem){
//		int[] tmpPivots = new int[problem.getNoRows()-1];
//		for(int i=0;i<tmpPivots.length;i++){
//			tmpPivots[i]=0;
//		}
//		return tmpPivots;
//	}
	
	/**
	 * erstellt ein double [] komplett mit nullen in der gr��er von deltaByF
	 * @param problem
	 * @return double[] in Gr��e von  deltaByF mit nullen
	 */
	public static double[] initializDeltaByFwithNull(SimplexProblemDual problem){
		double[] tmpDeltaByF = new double[problem.getNoColumns()];
		for(int i=0;i<tmpDeltaByF.length;i++){
			tmpDeltaByF[i]=0;
		}
		return tmpDeltaByF;
	}

	/**
	 * gibt zur�ck ob eine Zahl einem Array vorhanden ist
	 * @return bolean ob Zahl enthalten
	 */
	public static boolean contains(int[] array, int search){
		for(int i=0;i<array.length;i++){
			if(array[i]==search)return true;
		}
		return false;
	}

	/**
	 * vergleicht zwei int[] mit den Pivots drin und gibt zur�ck ob sie gleich sind.
	 * @return boolean
	 */
	public static boolean compareArray(int[] a, int[]b){
		if(a.length!=b.length)return false;
		for(int i=0;i<a.length;i++){
			if(a[i]!=b[i])return false;
		}
		return true;
	}

	/**
	 * transformiert ein primales Problem in ein duales Problem
	 */
	public static SimplexProblemDual transformProblem(SimplexProblemPrimal problem){
		SimplexProblemDual dualProblem = new SimplexProblemDual(problem.getTableau(),problem.getTarget());
		calcDeltas(dualProblem);
		calcDeltaByF(dualProblem);
		return dualProblem;
	}

	/**
	 * transformiert ein duales Problem in ein primales Problem
	 */
	public static SimplexProblemPrimal transformProblem(SimplexProblemDual problem){
		SimplexProblemPrimal primalProblem = new SimplexProblemPrimal(problem.getTableau(),problem.getTarget());
		calcDeltas(primalProblem);
		calcXByF(primalProblem);
		return primalProblem;
	}

	/**
	 * 1.Phase des Zweiphasen-Simplex f�r primale Probleme
	 * @return SimplexHistory[] der Probleme
	 */
	public static SimplexHistory[] firstPhasePrimal(SimplexHistory[] phases){
		phases[0].addElement(phases[0].getLastElement().clone());
		try{
			do{
				SimplexProblemPrimal current = (SimplexProblemPrimal) phases[0].getLastElement();
				current = SimplexLogic.simplex(current);
				if(current!=null)phases[0].addElement(current.clone());
			}
			while(phases[0].getLastElement().getOptimal()!=true);
		}catch(IOException e){// Problem gar nicht l�sbar
			phases[0].addElement(null);
			return phases;
		}catch(DataFormatException e){ //Problem in dualer Form weiterbearbeitbar
			phases[0].addElement(transformProblem((SimplexProblemPrimal)phases[0].getLastElement()));
			return firstPhaseDual(phases);
		}
		//vorletztes Problem aus History entfernen, falls die letzten beiden gleich sind
		if(compareArray(phases[0].getLastElement().getPivots(), phases[0].getElement(phases[0].size()-2).getPivots())){
			phases[0].deleteElement(phases[0].size()-2);
		}
		//Problem zur�ckbauen: alte Zielfuntion wieder�bernehmen, k�nstliche Variablen rausschmei�en
		phases[1].addElement(transitionPhasesPrimal(phases[0].getFirstElement(), phases[0].getLastElement()));
		return secondPhasePrimal(phases);
	}

	/**
	 * 1.Phase des Zweiphasen-Simplex f�r duale Probleme
	 * @return SimplexHistory[] der Probleme
	 */
	public static SimplexHistory[] firstPhaseDual(SimplexHistory[] phases){
		phases[0].addElement(phases[0].getLastElement().clone());
		try{
			do{
				SimplexProblemDual current = (SimplexProblemDual) phases[0].getLastElement();
				current = SimplexLogic.simplex(current);
				if(current!=null)phases[0].addElement(current.clone());
			}
			while(phases[0].getLastElement().getOptimal()!=true);
		}catch(IOException e){// Problem gar nicht l�sbar
			phases[0].addElement(null);
			return phases;
		}catch(DataFormatException e){ //weiterbearbeitung des Problems in primaler Form
			phases[0].addElement(transformProblem((SimplexProblemDual)phases[0].getLastElement()));
			return firstPhasePrimal(phases);
		}
		//vorletztes Problem aus History entfernen, falls die letzten beiden gleich sind
		if(compareArray(phases[0].getLastElement().getPivots(), phases[0].getElement(phases[0].size()-2).getPivots())){
			phases[0].deleteElement(phases[0].size()-2);
		}
		//Problem zur�ckbauen: alte Zielfuntion wieder�bernehmen, k�nstliche Variablen rausschmei�en
		phases[1].addElement(transitionPhasesDual(phases[0].getFirstElement(), phases[0].getLastElement()));
		return secondPhaseDual(phases);
	}

	/**
	 * 2.Phase des Zweiphasen-Simplex f�r primale Probleme
	 * @return SimplexHistory[] der Probleme
	 */
	public static SimplexHistory[] secondPhasePrimal(SimplexHistory[] phases){
		calcDeltas(phases[1].getLastElement());
		calcXByF((SimplexProblemPrimal)phases[1].getLastElement());
		phases[1].addElement(phases[1].getLastElement().clone());
		try{
			do{
				SimplexProblemPrimal current = (SimplexProblemPrimal) phases[1].getLastElement();
				current = SimplexLogic.simplex(current);
				if(current!=null)phases[1].addElement(current.clone());
			}
			while(phases[1].getLastElement().getOptimal()!=true);
		}catch(IOException e){// Problem gar nicht l�sbar
			phases[1].addElement(null);
			return phases;
		}catch(DataFormatException e){ //Problem wird in dualer Form weiterbearbeitet
			phases[1].addElement(transformProblem((SimplexProblemPrimal)phases[0].getLastElement()));
			return secondPhaseDual(phases);
		}
		//vorletztes Problem aus History entfernen, falls die letzten beiden gleich sind
		if(compareArray(phases[1].getLastElement().getPivots(), phases[1].getElement(phases[1].size()-2).getPivots())){
			phases[1].deleteElement(phases[1].size()-2);
		}
		return phases;
	}

	/**
	 * 2.Phase des Zweiphasen-Simplex f�r duale Probleme
	 * @return SimplexHistory[] der Probleme
	 */
	public static SimplexHistory[] secondPhaseDual(SimplexHistory[] phases){
		calcDeltas(phases[1].getLastElement());
		calcDeltaByF((SimplexProblemDual)phases[1].getLastElement());
		phases[1].addElement(phases[1].getLastElement().clone());
		try{
			do{
				SimplexProblemDual current = (SimplexProblemDual) phases[1].getLastElement();
				current = SimplexLogic.simplex(current);
				if(current!=null)phases[1].addElement(current.clone());
			}
			while(phases[1].getLastElement().getOptimal()!=true);
		}catch(IOException e){// Problem gar nicht l�sbar
			phases[1].addElement(null);
			return phases;
		}catch(DataFormatException e){ // Problem wird in primaler Form weiterbearbeitet
			phases[1].addElement(transformProblem((SimplexProblemDual)phases[0].getLastElement()));
			return secondPhasePrimal(phases);
		}
		//vorletztes Problem aus History entfernen, falls die letzten beiden gleich sind
		if(compareArray(phases[1].getLastElement().getPivots(), phases[1].getElement(phases[1].size()-2).getPivots())){
			phases[1].deleteElement(phases[1].size()-2);
		}
		return phases;
	}
}
