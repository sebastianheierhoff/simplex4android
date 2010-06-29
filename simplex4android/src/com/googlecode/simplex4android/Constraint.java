package com.googlecode.simplex4android;

import java.util.ArrayList;

/**
 * Datenhaltungklasse zur Repr�sentation von Nebenbedingungen.
 * Nebenbedingungen k�nnen in Gleichheits- oder Ungleichheitsform ("<=" und ">=") eingegeben werden. Direkt beim Erstellen werde sie in Gleichheitsform gebracht und mit den n�tigen Schlupfvariablen versehen.
 * @author Simplex4Android
 *
 */
public class Constraint {
	private ArrayList<Double> values;
	private int sign;
	private int slack; // Gibt an, ob eine Schlupfvariable eingef�gt wurde ("0" -> keine, "-1" -> negative, "1" -> positive)
	
	/**
	 * Legt f�r ein �bergebenes int-Array und ein Vergleichssymbol eine Nebenbedingung an.
	 * @param c int-Array mit den a-Werten der Nebenbedingung, an letzter Stelle steht b
	 * @param s Vergleichssymbol ("-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">=")
	 */
	public Constraint(double[] c, int sign){
		this.values = convertToDblArrayList(c);
		this.sign = sign;
		this.normalise();
	}
	
	/**
	 * F�gt der Nebenbedingung an letzter Stelle ein neues Element hinzu.
	 * @param value hinzuzuf�gendes Element
	 */
	public void add(Double value){
		this.values.add(value);
	}
	
	/**
	 * F�gt der Nebenbedingung an Index i ein neues Element hinzu. Alle darauffolgenden Elemente werden um einen Index verschoben.
	 * @param i Index,an dem das Element eingef�gt werden soll.
	 * @param value hinzuzuf�gendes Element
	 * @throws IndexOutOfBoundsException falls (i<0 || i>(size()-1))
	 */
	public void add(int i, Double value) throws IndexOutOfBoundsException{
		this.values.add(i, value);
	}

	/**
	 * �berf�hrt das �bergebene Array in eine ArrayList<Double>.
	 * @param array zu �berf�hrendes Array
	 * @return �berf�hrte ArrayList
	 */
	private ArrayList<Double> convertToDblArrayList(double[] array){
		ArrayList<Double> arrayList = new ArrayList<Double>();
		for(int i=0;i<array.length;i++){
			arrayList.add(i,new Double(array[i]));
		}
		return arrayList;
	}
	
	/**
	 * Gibt die einzuf�gende Schlupfvariable zur�ck.
	 * @return einzuf�gende Schlupfvariable ("0" -> keine, "-1" -> negative, "1" -> positive)
	 */
	public int getSlack(){
		return this.slack;
	}
	
	/**
	 * Gibt den Wert der Nebenbedingung an Index j zur�ck.
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
	 * Setzt das Vergleichssymbol und daraufhin die ggf. ben�tigte Schlupfvariable, um die Nebenbedingung in Standardform zu bringen.
	 * @param s "-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">="
	 */
	public void setSign(int s){
		this.sign = s;
		this.normalise();
	}
	
	/**
	 * Setzt den Wert an Index j auf den �bergebenen double-Wert.
	 * @param j Index des zu setzenden Elements
	 * @param value Wert, auf den das Element gestetzt werden soll.
	 * @throws IndexOutOfBoundsException falls (i<0 || i>(size()-1))
	 */
	public void setValue(int j, double value) throws IndexOutOfBoundsException{
		this.values.set(j, value);
	}

}
