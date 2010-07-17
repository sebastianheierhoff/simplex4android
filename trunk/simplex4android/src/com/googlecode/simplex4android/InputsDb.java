package com.googlecode.simplex4android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//Klasse zum Laden/Speichern von Zielfunktionen/Nebenbedingungen - TODO: Kommentare hinzufügen
//TODO: Kann man die Klasse statisch machen??? Brauche ich umbeding ein Objekt?

/**
 * Klasse zum Speichern und Laden alles bisher erstellen SimplexProbleme in Simplex4Android.
 */
public class InputsDb {

	private ArrayList<ArrayList<Input>> listOfInputs;//Liste zum Speichern von Problemen

	/**
	 * Leerer Konstruktor zum Anlegen der Liste der Probleme
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public InputsDb() throws ClassNotFoundException, IOException{
		listOfInputs = new ArrayList<ArrayList<Input>>(); 
		this.readInputs();
	}

	/**
	 * Methode zum Hinzufügen eines Problems
	 * @param problem
	 * @throws IOException 
	 */
	public void addInput(ArrayList<Input> input) throws IOException{
		listOfInputs.add(input);
		this.saveInputs();
	}

	public void removeInput(int position) throws IOException{
		listOfInputs.remove(position);
		this.saveInputs();
	}
	
	/**
	 * Gibt Array mit den Namen der einzelnen Namen der Probleme zurück.
	 * @return String[] mit den Namen der Probleme
	 * @throws NegativeArraySizeException
	 */
	public String[] getNames() throws NegativeArraySizeException{
		String[] s = new String[listOfInputs.size()-1];
		for(int i=0;i<listOfInputs.size();i++){
			s[i] = listOfInputs.get(i).get(0).toString();
		}
		return s;
	}

	/**
	 * Gibt die komplette Liste mit den gespeicherten Problemen zurück
	 * @return komplette Liste mit den gespeicherten Problemen
	 */
	public ArrayList<ArrayList<Input>> getListOfInputs(){
		return this.listOfInputs;
	}
	
	/**
	 * Gibt das Problem an Stelle i zurück.
	 * @param i Index des zu übergebenen Problems
	 * @return Problem an Stelle i
	 * @throws IndexOutOfBoundsException falls i<0 || i>=size
	 */
	public ArrayList<Input> getInput(int i) throws IndexOutOfBoundsException{
		return this.listOfInputs.get(i);	
	}
	
	/**
	 * Setzt das übergebene Tableau an Index i.
	 * Überschreitet i die größe der aktuellen listOfInputs, wird es am Ende eingefügt.
	 * @param i Index, an dem eingefügt werden soll.
	 * @param problem einzufügendes Problem
	 * @throws IndexOutOfBoundsException falls i<0
	 * @throws IOException 
	 */
	public void setInput(int i, ArrayList<Input> input) throws IndexOutOfBoundsException, IOException{
		if(i>=(this.listOfInputs.size())){
			this.addInput(input);
		}else{
			this.listOfInputs.set(i, input);
		}
	}

	/**
	 * Liest aus der Datei simplexProbleme.dat, eine ArrayList<SimplexProblem> aus und speichert diese in listOfInputs.
	 *  
	 * @throws ClassNotFoundException	wird nur geschmissen wenn die Klasse SimplexProblem nicht gefunden wird
	 * @throws IOException
	 */	
	@SuppressWarnings("unchecked")
	public void readInputs() throws ClassNotFoundException, IOException{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = new FileInputStream("simplexProbleme.dat");
		}
		catch(FileNotFoundException e){
			return; // Abbruch, keine Datei vorhanden.
		}
		ois = new ObjectInputStream(fis);
		Object[] input = (Object[]) ois.readObject();
		for(int i=0;i<input.length;i++){
			listOfInputs.add((ArrayList<Input>)input[i]);
		}		
		ois.close();		
	}

	/**
	 * Speichert die ArrayList listOfProblems in der Datei simplexProbleme.dat ab. Kann mit der Methode readHistory wieder eingelesen werden.
	 * @throws IOException
	 */	
	public void saveInputs()throws IOException{
		FileOutputStream fos = new FileOutputStream("simplexProbleme.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(listOfInputs.toArray());
		oos.close();
	}	
}
