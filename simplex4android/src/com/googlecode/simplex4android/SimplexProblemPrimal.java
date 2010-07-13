package com.googlecode.simplex4android;

import java.util.ArrayList;

public class SimplexProblemPrimal extends SimplexProblem {

	private ArrayList<Double> xByF; //
	
	/**
	 * Standardkonstruktor f�r ein leeres SimplexProblem zum anschlie�enden Hinzuf�gen der Zielfunktion und Nebenbedingungen.
	 * Die Zeile der delta-Werte ist bereits enthalten.
	 */
	public SimplexProblemPrimal(){
		super();
		this.xByF = new ArrayList<Double>();
	}
	
	/**
	 * Konstruktor, der eine ArrayList mit Input-Objekten �bergeben bekommt. 
	 * An erster Stellte muss dabei stehts die Zielfunktion vom Typ Target stehen.
	 * @param input ArrayList mit Input-Objekten (Index 0 muss ein Target-Objekt enthalten)
	 */
	public SimplexProblemPrimal(ArrayList<Input> input){
		super(input);
		this.xByF = new ArrayList<Double>();
	}
	
	/**
	 * Stellt ein SimplexTableau inklusive Zielfunktion zur Verf�gung.
	 * @param tableau
	 * @param target
	 */
	public SimplexProblemPrimal(double[][] tableau, double[] target){ 
		super(tableau, target);
		SimplexLogic.findPivots(this);
	}
	
	/**
	 * gibt ein Klon vom aktuellen Objekt zur�ck
	 */
	@Override
	public SimplexProblem clone() {
		SimplexProblemPrimal clone = new SimplexProblemPrimal();
		clone.setXByF(this.getXByF());
		clone.setTableau(this.getTableau());
		clone.setTarget(this.getTarget());
		clone.setPivots(this.getPivots());
		return clone;
	}
	
	/**
	 * Gibt ein Array mit den x/f-Werten f�r jede Zeile zur�ck.
	 * @return Array mit den x/f-Werten f�r jede Zeile
	 */
	public double[] getXByF() {
		return this.convertToDblArray(this.xByF);
	}
	
	/**
	 * set Optimal Methode, die zus�tzlich xByF mit nullen auff�lt. Dies f�hrt dazu, dass im Optimum nur - angezeigt werden.
	 */
	public void setOptimal(){
		super.setOptimal();
		if(xByF!=null)xByF.clear();
		else{
			xByF = new ArrayList<Double>();
		}
		for(int i=0;i<super.getNoColumns()-1;i++){
			xByF.add(0.0);
		}
	}
	/**
	 * �berschreibt die x/f-Werte.
	 * @param xByF neue x/f-Werte
	 */
	public void setXByF(double[] xByF) {
		this.xByF = this.convertToDblArrayList(xByF);
	}
	
	/**erstellt HTML-Code als String f�r das primale Problem.
	 * 
	 * @return komplettes Tableau als String in Html 
	 */
	@Override
	public String tableauToHtml(){
		int[] pivots = SimplexLogic.findPivotsSorted(this);
		String html = "\n<html>\n<body>\n<table border=1 CELLSPACING=0>\n";
		//1. Zeile: Zielfunktion
		html = html + "<tr>\n<td></td><td></td>";		// direkt inkl. zwei leeren Eintr�gen 
		for(int i=0;i<this.getTarget().length-1;i++){
			html = html + "<td>" + (Math.round(this.getTarget()[i]*100.)/100.) + "</td>";
		}
		html = html + "<td></td><td></td></tr>\n";
		//2. Zeile: zwei Zeilen frei Durchnummerierung der Spalten + x +x/f
		html = html + "<tr><td></td><td></td>";		// direkt inkl. zwei leeren Eintr�gen
		for(int i=0;i<this.getTarget().length-1;i++){
			html = html +"<td>"+ (i+1) +"</td>";
		}
		html = html + "<td>x</td><td>x/f</td>";
		//ab der 3. Zeile: das eigentliche Tableau, die ersten beiden Spalten auch wie im Tableau + x/f
		for(int i=0;i<this.getTableau().length-1;i++){			//so oft ausf�hren wie es Zeilen-1 im Tableau gibt
			html = html + "<tr><td>"+ this.getTarget()[pivots[i]]+"</td><td>" +(pivots[i]+1) +"</td>";
			for(int j=0;j<this.getTableau()[0].length;j++){
				html = html + "<td>" + (Math.round((this.getTableau()[i][j])*100.)/100.)+"</td>";
			}
			//x/f noch hinten dran h�ngen
			if((xByF.get(i)<=0) || (xByF.get(i)== Double.POSITIVE_INFINITY)){
				html = html + "<td> &#8211; </td>";
			}
			else{
				html = html + "<td>"+ (Math.round(xByF.get(i)*100.)/100.)+"</td>";
			}
			html = html + "</tr>\n";
		}
		// Letzte Zeile: extra behandlung f�r delta-Wert
		html = html + "<tr><td></td><td></td>"; //inkl. zwei leerfelder
		for(int i=0;i<this.getTableau()[0].length;i++){
			html = html + "<td>" + (Math.round((this.getTableau()[(this.getTableau().length-1)][i])*100.)/100.) +"</td>";
		}
		html = html + "<td></td></tr>\n";
		html = html + "</table>\n</body>\n</html>";
		return html;
	}
}
