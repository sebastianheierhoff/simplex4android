package com.googlecode.simplex4android;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Klasse zum Speichern und Laden alles bisher erstellen SimplexProbleme in Simplex4Android.
 */
public class InputsDb {

	private ArrayList<ArrayList<Input>> listOfInputs;//Liste zum Speichern von Problemen

	public InputsDb() throws StreamCorruptedException, IOException, ClassNotFoundException{
		listOfInputs = new ArrayList<ArrayList<Input>>();
		this.readFile();
	}
	
	
	public void setListOfInputs(ArrayList<ArrayList<Input>> listOfInputs){
		this.listOfInputs = listOfInputs;
	}
	
	public ArrayList<ArrayList<Input>> getListOfInputs(){
		return this.listOfInputs;
	}
	
	public void writeFile() throws Exception {
		FileOutputStream fos = new FileOutputStream("simplexProblems.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(listOfInputs);
		oos.flush();
		oos.close();

	}

	@SuppressWarnings("unchecked")
	public void readFile() throws StreamCorruptedException, IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream("simplexProblems.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		listOfInputs = (ArrayList<ArrayList<Input>>) ois.readObject();
		ois.close();
	}
}
