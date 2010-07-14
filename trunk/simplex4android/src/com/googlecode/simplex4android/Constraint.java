package com.googlecode.simplex4android;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Datenhaltungklasse zur Repräsentation von Nebenbedingungen.
 * Nebenbedingungen können in Gleichheits- oder Ungleichheitsform ("<=" und ">=") eingegeben werden. Direkt beim Erstellen werde sie in Gleichheitsform gebracht und mit den nötigen Schlupfvariablen versehen.
 * @author Simplex4Android
 *
 */
public class Constraint extends Input implements Serializable{
	private static final long serialVersionUID = -4853003520885704436L; // NICHT ÄNDERN!

	/**
	 * @serial
	 */
	private double targetValue;

	/**
	 * @serial
	 */
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
	//		out.writeDouble(this.targetValue);
	//		out.writeInt(this.sign);
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
	//		this.targetValue = in.readDouble();
	//		this.sign = in.readInt();
	//		this.values = this.convertToDblArrayList((double[]) in.readObject());
	//	}
}
