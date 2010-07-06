package com.googlecode.simplex4android;

import java.util.ArrayList;

public class Target{
	private ArrayList<Double> values;
	private boolean minOrMax; //true bedeutet Minimierung, false bedeutet Maximierung
	
	/**
	 * Legt für ein übergebenes double-Array und ein Vergleichssymbol eine Nebenbedingung an.
	 * @param values double-Array mit der Zielfunktion
	 * @param minOrMax false steht für Maximierung, true für Minimierung
	 */
	public Target(double[] values, boolean minOrMax) {
		this.values = convertToDblArrayList(values);
		this.minOrMax = minOrMax;
	}
	
	/**
	 * get-Methode für die Zielfunktion
	 * @return Zielfunktion in einer ArrayList mit Doublen
	 */
	public ArrayList<Double> getValues() {
		return values;
	}
	
	/**
	 * set-Methode für die Zielfunktion
	 * @param values Zielfunktion als Arraylist mit Doublen
	 */
	public void setValues(ArrayList<Double> values) {
		this.values = values;
	}
	
	/**
	 * boolean der angibt ob Minimierung oder Maximierung vorliegt
	 * @return true steht für Minimierung, false für Maximierung
	 */
	public boolean isMinOrMax() {
		return minOrMax;
	}
	
	/**
	 * boolean der angibt ob Minimierung oder Maximierung vorliegt
	 * @param minOrMax true steht für Minimierung, false für Maximierung
	 */
	public void setMinOrMax(boolean minOrMax) {
		this.minOrMax = minOrMax;
	}
	
	/**
	 * Überführt das übergebene Array in eine ArrayList<Double>.
	 * @param array zu überführendes Array
	 * @return überführte ArrayList
	 */
	private ArrayList<Double> convertToDblArrayList(double[] array){
		ArrayList<Double> arrayList = new ArrayList<Double>();
		for(int i=0;i<array.length;i++){
			arrayList.add(i,new Double(array[i]));
		}
		return arrayList;
	}
}
