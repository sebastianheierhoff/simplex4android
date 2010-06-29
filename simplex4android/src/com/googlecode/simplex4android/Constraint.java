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
	private int sign;
	private int slack; // Gibt an, ob eine Schlupfvariable eingefügt wurde ("0" -> keine, "-1" -> negative, "1" -> positive)
	
	/**
	 * Legt für ein übergebenes int-Array und ein Vergleichssymbol eine Nebenbedingung an.
	 * @param c int-Array mit den a-Werten der Nebenbedingung, an letzter Stelle steht b
	 * @param s Vergleichssymbol ("-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">=")
	 */
	public Constraint(double[] c, int sign){
		this.values = convertToDblArrayList(c);
		this.sign = sign;
		this.normalise();
	}
	
	/**
	 * Fügt der Nebenbedingung an letzter Stelle ein neues Element hinzu.
	 * @param value hinzuzufügendes Element
	 */
	public void add(Double value){
		this.values.add(value);
	}
	
	/**
	 * Fügt der Nebenbedingung an Index i ein neues Element hinzu. Alle darauffolgenden Elemente werden um einen Index verschoben.
	 * @param i Index,an dem das Element eingefügt werden soll.
	 * @param value hinzuzufügendes Element
	 * @throws IndexOutOfBoundsException falls (i<0 || i>(size()-1))
	 */
	public void add(int i, Double value) throws IndexOutOfBoundsException{
		this.values.add(i, value);
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
	 * Gibt die einzufügende Schlupfvariable zurück.
	 * @return einzufügende Schlupfvariable ("0" -> keine, "-1" -> negative, "1" -> positive)
	 */
	public int getSlack(){
		return this.slack;
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
	 * Bringt die Nebenbedingung in Standardform.
	 */
	private void normalise(){
		switch(sign){
		case -1:
			this.slack = 1;
			break;
		case 1:
			this.slack = -1;
			break;
		default:
			this.slack = 0;
		}
	}
	
	/**
	 * Setzt das Vergleichssymbol und daraufhin die ggf. benötigte Schlupfvariable, um die Nebenbedingung in Standardform zu bringen.
	 * @param s "-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">="
	 */
	public void setSign(int s){
		this.sign = s;
		this.normalise();
	}
	
	/**
	 * Setzt den Wert an Index j auf den übergebenen double-Wert.
	 * @param j Index des zu setzenden Elements
	 * @param value Wert, auf den das Element gestetzt werden soll.
	 * @throws IndexOutOfBoundsException falls (i<0 || i>(size()-1))
	 */
	public void setValue(int j, double value) throws IndexOutOfBoundsException{
		this.values.set(j, value);
	}

}
