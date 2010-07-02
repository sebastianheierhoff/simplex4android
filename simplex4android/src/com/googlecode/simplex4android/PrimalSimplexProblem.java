package com.googlecode.simplex4android;

import java.util.ArrayList;

public class PrimalSimplexProblem extends SimplexProblem {

	private ArrayList<Double> xByF; //
	
	/**
	 * Standardkonstruktor für ein leeres SimplexProblem zum anschließenden Hinzufügen der Zielfunktion und Nebenbedingungen.
	 * Die Zeile der delta-Werte ist bereits enthalten.
	 */
	public PrimalSimplexProblem(){
		super();
		this.xByF = new ArrayList<Double>();
	}
	
	/**
	 * Stellt ein SimplexTableau inklusive Zielfunktion zur Verfügung.
	 * @param tableau
	 * @param target
	 */
	public PrimalSimplexProblem(double[][] tableau, int[] target){ 
		super(tableau, target);
		SimplexLogic.findPivots(this);
	    SimplexLogic.calcDeltas(this);
	    SimplexLogic.calcXByF(this);
	}
	
	/**
	 * Gibt ein Array mit den x/f-Werten für jede Zeile zurück.
	 * @return Array mit den x/f-Werten für jede Zeile
	 */
	public double[] getXByF() {
		return this.convertToDblArray(this.xByF);
	}
	
	/**
	 * Überschreibt die x/f-Werte.
	 * @param xByF neue x/f-Werte
	 */
	public void setXByF(double[] xByF) {
		this.xByF = this.convertToDblArrayList(xByF);
	}
	
	/**erstellt HTML-Code als String für das primale Problem.
	 * 
	 * @return komplettes Tableau als String in Html 
	 */
	public String tableauToHtml(){
		String html = "\n<html>\n<body>\n<table border=1 CELLSPACING=0>\n";
		//1. Zeile: Zielfunktion
		html = html + "<tr>\n<td></td><td></td>";		// direkt inkl. zwei leeren Einträgen 
		for(int i=0;i<target.size()-1;i++){
			html = html + "<td>" + (Math.round(target.get(i)*100.)/100.) + "</td>";
		}
		html = html + "<td></td><td></td></tr>\n";
		//2. Zeile: zwei Zeilen frei Durchnummerierung der Spalten + x +x/f
		html = html + "<tr><td></td><td></td>";		// direkt inkl. zwei leeren Einträgen
		for(int i=0;i<target.size()-1;i++){
			html = html +"<td>"+ (i+1) +"</td>";
		}
		html = html + "<td>x</td><td>x/f</td>";
		//ab der 3. Zeile: das eigentliche Tableau, die ersten beiden Spalten auch wie im Tableau + x/f
		for(int i=0;i<tableau.size()-1;i++){			//so oft ausführen wie es Zeilen-1 im Tableau gibt
			html = html + "<tr><td>"+ target.get(pivots.get(i))+"</td><td>" +(pivots.get(i)+1) +"</td>";
			for(int j=0;j<tableau.get(0).size();j++){
				html = html + "<td>" + (Math.round((tableau.get(i).get(j))*100.)/100.)+"</td>";
			}
			//x/f noch hinten dran hängen
			if((xByF.get(i)<=0) || (xByF.get(i)== Double.POSITIVE_INFINITY)){
				html = html + "<td> &#8211; </td>";
			}
			else{
				html = html + "<td>"+ (Math.round(xByF.get(i)*100.)/100.)+"</td>";
			}
			html = html + "</tr>\n";
		}
		// Letzte Zeile: extra behandlung für delta-Wert
		html = html + "<tr><td></td><td></td>"; //inkl. zwei leerfelder
		for(int i=0;i<tableau.get(0).size();i++){
			html = html + "<td>" + (Math.round((tableau.get(tableau.size()-1).get(i))*100.)/100.) +"</td>";
		}
		html = html + "<td></td></tr>\n";
		html = html + "</table>\n</body>\n</html>";
		return html;
	}

}
