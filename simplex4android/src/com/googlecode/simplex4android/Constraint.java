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
	private double targetValue;
	private int sign;
	
	/**
	 * Legt f�r ein �bergebenes int-Array und ein Vergleichssymbol eine Nebenbedingung an.
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
	 */
	public void add(int i, Double value){
		if(i>=this.values.size()){
			for(int j=this.values.size();j<i;j++){
				this.values.add(new Double(0));
			}
		}
		this.values.set(i, value);
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
	 * Gibt den Zielfunktionswert zur�ck.
	 * @return Zielfunktionswert
	 */
	public double getTargetValue(){
		return this.targetValue;
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
	 * Setzt das Vergleichssymbol und daraufhin die ggf. ben�tigte Schlupfvariable, um die Nebenbedingung in Standardform zu bringen.
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
	 * Setzt den Wert an Index j auf den �bergebenen double-Wert.
	 * @param j Index des zu setzenden Elements
	 * @param value Wert, auf den das Element gestetzt werden soll.
	 * @throws IndexOutOfBoundsException falls (i<0 || i>(size()-1))
	 */
	public void setValue(int j, double value) throws IndexOutOfBoundsException{
		this.values.set(j, value);
	}

	
	/**
	 * Gibt eine Stringdarstellung der x1..xn aus.
	 * @return Stringdarstellung der x1..xn
	 */
	public String valuesToString(){
		String re = "";
		re += this.values.get(0) +"x1";
		for(int i=1;i<this.values.size()-2;i++){
			if(this.values.get(i)<0){
				re += " " +this.values.get(i).intValue() +"x" +(i+1);
			}else{
				re += " + " +this.values.get(i).intValue() +"x" +(i+1);
			}			
		}
		return re;
	}
}
