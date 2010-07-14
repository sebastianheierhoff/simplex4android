package com.googlecode.simplex4android;

import java.io.IOException;
import java.util.ArrayList;

public class Target extends Input{
	private boolean minOrMax;	// true bedeutet Minimierung, false bedeutet Maximierung
	private int userSettings; 	// legt die vom Benutzer gew�nschten Settings f�r das L�sen eines SimplexProblems fest
								// 0=primal, 1=dual;
	
	/**
	 * Standardkonstruktor
	 */
	public Target() {
		super();
	}
	
	/**
	 * Gibt die vom Benutzer gew�nschten Settings f�r das L�sen eines SimplexProblems zur�ck.
	 * @return 0=primal, 1=dual
	 */
	public int getUserSettings(){
		return this.userSettings;
	}
	
	/**
	 * boolean der angibt ob Minimierung oder Maximierung vorliegt
	 * @return true steht f�r Minimierung, false f�r Maximierung
	 */
	public boolean isMinOrMax() {
		return minOrMax;
	}
	
	/**
	 * boolean der angibt ob Minimierung oder Maximierung vorliegt
	 * @param minOrMax true steht f�r Minimierung, false f�r Maximierung
	 */
	public void setMinOrMax(boolean minOrMax) {
		this.minOrMax = minOrMax;
	}
	
	/**
	 * Setzt die vom Benutzer gew�nschten Settings f�r das L�sen eines SimplexProblems.
	 * @param setting 0=primal, 1=dual
	 * @throws IOException falls ung�ltiger Wert gesetzt werden soll.
	 */
	public void setUserSettings(int setting) throws IOException{
		ArrayList<Integer> settings = new ArrayList<Integer>();
		for(int i=0;i<2;i++){
			settings.add(new Integer(i));
		}
		if(settings.contains(new Integer(setting))){
			this.userSettings = setting;
		}else{
			throw new IOException("Ung�ltige Settings!");
		}
		
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
