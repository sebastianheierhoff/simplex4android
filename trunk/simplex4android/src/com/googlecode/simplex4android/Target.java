package com.googlecode.simplex4android;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Target extends Input implements Serializable{
	private boolean minOrMax;	// true bedeutet Minimierung, false bedeutet Maximierung
	private int userSettings; 	// legt die vom Benutzer gewünschten Settings für das Lösen eines SimplexProblems fest
	// 0=primal, 1=dual;

	/**
	 * Standardkonstruktor
	 */
	public Target() {
		super();
	}

	/**
	 * Gibt die vom Benutzer gewünschten Settings für das Lösen eines SimplexProblems zurück.
	 * @return 0=primal, 1=dual
	 */
	public int getUserSettings(){
		return this.userSettings;
	}

	/**
	 * boolean der angibt ob Minimierung oder Maximierung vorliegt
	 * @return true steht für Minimierung, false für Maximierung
	 */
	public boolean getMinOrMax() {
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
	 * Setzt die vom Benutzer gewünschten Settings für das Lösen eines SimplexProblems.
	 * @param setting 0=primal, 1=dual
	 * @throws IOException falls ungültiger Wert gesetzt werden soll.
	 */
	public void setUserSettings(int setting){
		this.userSettings = setting;
	}

	/**
	 * Gibt die Zielfunktion (komplett) als String aus.
	 */
	@Override
	public String toString(){
		String s = this.valuesToString() + " \u2192 ";
		if(this.minOrMax){
			s += "min!";
		}else{
			s += "max!";
		}
		return s;
	}

	/**
	 * Lesemethode zum Auslesen einer Zielfunktion.
	 * @param aInputStream 
	 */
	protected void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
		this.minOrMax = ois.readBoolean();
		this.userSettings = ois.readInt();
		int length = ois.readInt();
		for(int i=0;i<length;i++){
			this.values.add((Double)ois.readObject());
		}		
		ois.close();
	}

	/**
	 * Methode zum Speichern einer Zielfunktion.
	 * @param oos
	 * @throws IOException
	 */
	protected void writeObject(ObjectOutputStream oos) throws IOException {
		oos.writeBoolean(minOrMax);
		oos.writeInt(userSettings);
		int length = this.values.size();
		oos.writeInt(length);
		for(int i=0;i<length;i++){
			oos.writeObject(this.values.get(i));
		}		
		oos.close();
	}
}
