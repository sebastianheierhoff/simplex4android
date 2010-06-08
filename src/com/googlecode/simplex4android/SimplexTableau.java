package com.googlecode.simplex4android;

public class SimplexTableau {

	private double[][]tableau;
	private int[] target;
	
	public SimplexTableau(double[][] tableau, int[] target){ //int[] für die Zielfunktion?
		this.tableau = tableau;
		this.target = target;
	}
	
	//getter um einzelnen Wert auszugeben. Evtl. sind noch spalte und Zeile vertauscht
	public double getField(int zeile, int spalte){
		return tableau[zeile][spalte];
	}
	
	//setter um einzelnen Wert zu setzen. Evtl. sind noch spalte und Zeile vertauscht
	public void setField(int zeile, int spalte, double value){
		tableau[zeile][spalte]=value;
	}

	//Hier nach nur noch standard getter und setter. Mal schauen welche wir davon überhaupt brauchen
	
	public double[][] getTableau() {
		return tableau;
	}

	public void setTableau(double[][] tableau) {
		this.tableau = tableau;
	}

	public int[] getTarget() {
		return target;
	}

	public void setTarget(int[] target) {
		this.target = target;
	}

	public int getNoColumns(){
		return this.tableau[0].length-1;
	}
	
	public int getNoRows(){
		return this.tableau.length-1;
	}
}
