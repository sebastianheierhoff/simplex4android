package com.googlecode.simplex4android;

import android.os.Parcel;
import android.os.Parcelable;


public class Target extends Input{
	private boolean minOrMax; //true bedeutet Minimierung, false bedeutet Maximierung
	
	/**
	 * Standardkonstruktor
	 */
	public Target() {
		super();
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
	 * Gibt die Zielfunktion (komplett) als String aus.
	 */
	@Override
	public String toString(){
		String s = this.valuesToString() + " = ";
		if(this.minOrMax){
			s += "min";
		}else{
			s += "max";
		}
		return s;
	}
}
