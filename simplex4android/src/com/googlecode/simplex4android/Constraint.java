package com.googlecode.simplex4android;

import java.util.ArrayList;

/**
 * Datenhaltungklasse zur Repräsentation von Nebenbedingungen.
 * Nebenbedingungen können in Gleichheits- oder Ungleichheitsform ("<=" und ">=") eingegeben werden. Direkt beim Erstellen werde sie in Gleichheitsform gebracht und mit den nötigen Schlupfvariablen versehen.
 * @author Simplex4Android
 *
 */
public class Constraint {
	private ArrayList<Double> values;
	private double targetValue;
	private int sign;
	
	/**
	 * Legt für ein übergebenes int-Array und ein Vergleichssymbol eine Nebenbedingung an.
	 * @param c int-Array mit den a-Werten der Nebenbedingung, an letzter Stelle steht b
	 * @param s Vergleichssymbol ("-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">=")
	 */
	public Constraint(double[] c, int sign){
		this.values = convertToDblArrayList(c);
		this.sign = sign;
	}
	
	public Constraint() {
		values = new ArrayList<Double>();
	}

	/**
	 * Fügt der Nebenbedingung an letzter Stelle ein neues Element hinzu.
	 * @param value hinzuzufügendes Element
	 */
	public void add(Double value){
		this.values.add(value);
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
		
	/**
	 * Gibt den Zielfunktionswert zurück.
	 * @return Zielfunktionswert
	 */
	public double getTargetValue(){
		return this.targetValue;
	}
	
	/**
	 * Gibt den Wert der Nebenbedingung an Index j zurück.
	 * @return Wert der Nebenbedingung an Index j
	 * @throws IndexOutOfBoundsException falls (i<0 || i>(size()-1))
	 */
	public double getValue(int j) throws IndexOutOfBoundsException{
		return this.values.get(j);
	}
	
	/**
	 * Setzt das Vergleichssymbol und daraufhin die ggf. benötigte Schlupfvariable, um die Nebenbedingung in Standardform zu bringen.
	 * @param s "-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">="
	 */
	public void setSign(int s){
		this.sign = s;
	}
	
	/**
	 * Setzt den Zielfunktionswert.
	 * @param value zu setzender Zielfunktionswert
	 */
	public void setTargetValue(double value){
		this.targetValue = value;
	}
	
	/**
	 * Setzt den Wert an Index j auf den übergebenen double-Wert. Ist der Index nicht vorhanden, wir passend aufgefüllt.
	 * @param j Index des zu setzenden Elements
	 * @param value Wert, auf den das Element gestetzt werden soll.
	 */
	public void setValue(int i, double value){
		if(i>=this.values.size()){
			for(int j=this.values.size();j<=i;j++){
				this.values.add(new Double(0));
			}
		}
		this.values.set(i, value);
	}

	
	/**
	 * Gibt eine Stringdarstellung der x1..xn aus.
	 * @return Stringdarstellung der x1..xn
	 */
	public String valuesToString(){
		String re = "";
		re += Math.round(this.values.get(0)*100.)/100. +"x1";
		for(int i=1;i<this.values.size();i++){
			if(this.values.get(i)<0){
				re += " " + String.valueOf(Math.round(this.values.get(i)*100.)/100.) +"x" +(i+1);
			}else{
				re += " + " + String.valueOf(Math.round(this.values.get(i)*100.)/100.) +"x" +(i+1);
			}			
		}
		return re;
	}
	
	/**
	 * Gibt eine Stringdarstellung der x1..xn aus.
	 * @return Stringdarstellung der x1..xn
	 */
	public String valueToString(int i) throws IndexOutOfBoundsException{
		String re = Math.round(this.values.get(i)*100)/100 +"x" +(i+1);
		return re;
	}

	
}
