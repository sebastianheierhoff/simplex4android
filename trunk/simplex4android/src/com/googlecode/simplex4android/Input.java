package com.googlecode.simplex4android;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Input {
	protected ArrayList<Double> values;
	
	public Input(){
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
	 * Liest einen als String übergebenen Bruch aus und gibt den dazugehörigen double-Wert zurück
	 * @param s zu bearbeitender Bruch
	 * @return double-Wert des Bruchs
	 * @throws IOException falls kein "/" im übergebenen String enthalten
	 */
	public static double fractionToDbl(String s) throws IOException{
		int slash = s.indexOf("/");
		int firstDigit = 0;
		int lastDigit = 0;
		if(slash==-1){
			throw new IOException("Kein '/' enthalten");
		}
		for(int i=slash-1;i>0;i--){ // Anfangsindex der Zahl vor dem "/" finden
			if(Integer.valueOf(s.substring(i-1,i))>=0 && Integer.valueOf(s.substring(i-1,i))<=9){
				firstDigit = i-1;
			}
		}
		for(int i=slash+1;i<s.length();i++){
			if(Integer.valueOf(s.substring(i,i+1))>=0 && Integer.valueOf(s.substring(i,i+1))<=9){
				lastDigit = i;
			}
		}
		double numerator = Integer.valueOf(s.substring(firstDigit, slash));
		double denomerator = Integer.valueOf(s.substring(slash+1, lastDigit+1));
		double dbl = numerator/denomerator;
		return dbl;
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
	 * get-Methode für die Zielfunktion
	 * @return Zielfunktion in einer ArrayList mit Doublen
	 */
	public ArrayList<Double> getValues() {
		return this.values;
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
	 * set-Methode für die Zielfunktion
	 * @param values Zielfunktion als Arraylist mit Doublen
	 */
	public void setValues(ArrayList<Double> values) {
		this.values = values;
	}

	/**
	 * Gibt eine Stringdarstellung der x1..xn aus.
	 * @return Stringdarstellung der x1..xn
	 */
	public String valueToString(int i) throws IndexOutOfBoundsException{
		String re = Math.round(this.values.get(i)*100)/100 +"x" +(i+1);
		return re;
	}
	
	/**
	 * Gibt eine Stringdarstellung der x1..xn aus. Es werden nur x-Werte mit Faktor != 0 angezeigt.
	 * @return Stringdarstellung der x1..xn
	 */
	public String valuesToString(){
		String re = "";
		if(!(this.values.get(0)==0.0)){
			re += String.valueOf(Math.round(this.values.get(0)*100.)/100.) +"x1";
		}
		for(int i=1;i<this.values.size();i++){
			if(!(this.values.get(i)==0)){
				if(re.equals("")){
					if(this.values.get(i)<0){
						re += String.valueOf(Math.round(this.values.get(i)*100.)/100.) +"x" +(i+1);
					}else{
						re += String.valueOf(Math.round(this.values.get(i)*100.)/100.) +"x" +(i+1);
					}
				}else{
					if(this.values.get(i)<0){
						re += " " + String.valueOf(Math.round(this.values.get(i)*100.)/100.) +"x" +(i+1);
					}else{
						re += " + " + String.valueOf(Math.round(this.values.get(i)*100.)/100.) +"x" +(i+1);
					}
				}
			}
						
		}
		return re;
	}
}
