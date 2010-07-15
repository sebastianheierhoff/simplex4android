package com.googlecode.simplex4android;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

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
	 * Setzt die vom Benutzer gewünschten Settings für das Lösen eines SimplexProblems.
	 * @param setting 0=primal, 1=dual
	 * @throws IOException falls ungültiger Wert gesetzt werden soll.
	 */
	public void setUserSettings(int setting) throws IOException{
		ArrayList<Integer> settings = new ArrayList<Integer>();
		for(int i=0;i<2;i++){
			settings.add(new Integer(i));
		}
		if(settings.contains(new Integer(setting))){
			this.userSettings = setting;
		}else{
			throw new IOException("Ungültige Settings!");
		}
		
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
	  * Always treat de-serialization as a full-blown constructor, by
	  * validating the final state of the de-serialized object.
	  */
	   private void readObject(
	     ObjectInputStream aInputStream
	   ) throws ClassNotFoundException, IOException {
	     //always perform the default de-serialization first
	     aInputStream.defaultReadObject();
	     //make defensive copy of the mutable Date field
	     //fDateOpened = new Date( fDateOpened.getTime() );
	  }

	    /**
	    * This is the default implementation of writeObject.
	    * Customise if necessary.
	    */
	    private void writeObject(
	      ObjectOutputStream aOutputStream
	    ) throws IOException {
	      //perform the default serialization for all non-transient, non-static fields
	      aOutputStream.defaultWriteObject();
	    }
}
