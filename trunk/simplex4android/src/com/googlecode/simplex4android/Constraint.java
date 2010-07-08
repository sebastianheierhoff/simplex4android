package com.googlecode.simplex4android;

/**
 * Datenhaltungklasse zur Repräsentation von Nebenbedingungen.
 * Nebenbedingungen können in Gleichheits- oder Ungleichheitsform ("<=" und ">=") eingegeben werden. Direkt beim Erstellen werde sie in Gleichheitsform gebracht und mit den nötigen Schlupfvariablen versehen.
 * @author Simplex4Android
 *
 */
public class Constraint extends Input{
	private double targetValue;
	private int sign;
	
	/**
	 * Standardkonstruktor
	 */
	public Constraint() {
		super();
	}
	
	/**
	 * Gibt den Vergleichsoperator zurück.
	 * @return Vergleichsoperator ("-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">=")
	 */
	public int getSign(){
		return sign;
	}
		
	/**
	 * Gibt den Zielfunktionswert zurück.
	 * @return Zielfunktionswert
	 */
	public double getTargetValue(){
		return this.targetValue;
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
	 * Gibt die Nebenbedingung (komplett) als String aus.
	 */
	@Override
	public String toString(){
		String s = this.valuesToString();
		if(this.sign==-1){
			s += " &#x2264 ";
		}else if(this.sign==0){
			s += " = ";
		}else if(this.sign==1){
			s += " &#x2265 ";
		}
		s += this.targetValue;
		return s;
	}

	
	

	
}
