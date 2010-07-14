package com.googlecode.simplex4android;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Target extends Input implements Serializable{
	private static final long serialVersionUID = -4523337831850396294L; // NICHT ÄNDERN!
	
	/**
	 * true bedeutet Minimierung, false bedeutet Maximierung
	 * @serial
	 */
	private boolean minOrMax;
	
	/**
	 * legt die vom Benutzer gewünschten Settings für das Lösen eines SimplexProblems fest
	 * 0=primal, 1=dual
	 * @serial
	 */
	private int userSettings;
	
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
		String s = this.valuesToString() + " = ";
		if(this.minOrMax){
			s += "min";
		}else{
			s += "max";
		}
		return s;
	}
	
	/**
	 * Always treat de-serialization as a full-blown constructor, by
	 * validating the final state of the de-serialized object.
	 */
	private void readObject(java.io.ObjectInputStream aInputStream) throws ClassNotFoundException, IOException{
		aInputStream.defaultReadObject();

		//make defensive copy of the mutable Date field
		this.values = new ArrayList<Double>(values);

		//ensure that object state has not been corrupted or tampered with maliciously
	}

	/**
	 * This is the default implementation of writeObject.
	 * Customise if necessary.
	 */
	private void writeObject(java.io.ObjectOutputStream aOutputStream) throws IOException {
		//perform the default serialization for all non-transient, non-static fields
		aOutputStream.defaultWriteObject();
	}
//	
//	/**
//	 * Schreibt die Constraint in den gewünschten OutputStream.
//	 * @param out OutputStream, in den geschrieben werden soll.
//	 * @throws IOException
//	 */
//	private void writeObject(java.io.ObjectOutputStream out) throws IOException{
//		out.writeBoolean(this.minOrMax);
//		out.writeInt(this.userSettings);
//		out.writeObject(this.values.toArray());
//		out.flush();
//		out.close();
//	}
//	
//	/**
//	 * Liest die Constraint aus dem InputStream.
//	 * @param in zu lesender InputStream
//	 * @throws IOException
//	 * @throws ClassNotFoundException
//	 */
//	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
//		this.minOrMax = in.readBoolean();
//		this.userSettings = in.readInt();
//		this.values = this.convertToDblArrayList((double[]) in.readObject());
//		in.close();
//	}
}
