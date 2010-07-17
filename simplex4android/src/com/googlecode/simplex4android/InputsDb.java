package com.googlecode.simplex4android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//Klasse zum Laden/Speichern von Zielfunktionen/Nebenbedingungen - TODO: Kommentare hinzuf�gen
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
	 * Methode zum Hinzuf�gen eines Problems
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
	 * Gibt Array mit den Namen der einzelnen Namen der Probleme zur�ck.
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
	 * Gibt die komplette Liste mit den gespeicherten Problemen zur�ck
	 * @return komplette Liste mit den gespeicherten Problemen
	 */
	public ArrayList<ArrayList<Input>> getListOfInputs(){
		return this.listOfInputs;
	}
	
	/**
	 * Gibt das Problem an Stelle i zur�ck.
	 * @param i Index des zu �bergebenen Problems
	 * @return Problem an Stelle i
	 * @throws IndexOutOfBoundsException falls i<0 || i>=size
	 */
	public ArrayList<Input> getInput(int i) throws IndexOutOfBoundsException{
		return this.listOfInputs.get(i);	
	}
	
	/**
	 * Setzt das �bergebene Tableau an Index i.
	 * �berschreitet i die gr��e der aktuellen listOfInputs, wird es am Ende eingef�gt.
	 * @param i Index, an dem eingef�gt werden soll.
	 * @param problem einzuf�gendes Problem
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
		int length = ois.readInt();
		for(int i=0;i<length;i++){ // f�r alle Probleme
			ArrayList<Input> input = new ArrayList<Input>();
			int size = ois.readInt();
			Target t = new Target(); // f�r die Zielfunktion
			t.readObject(ois);
			input.add(t);
			for(int j=1;j<size;j++){ // f�r die einzelnen Nebenbedingungen jedes Problems
				Constraint c = new Constraint();
				c.readObject(ois);
				input.add(c);
			}
			this.listOfInputs.add(input);
		}		
		ois.close();		
	}

	/**
	 * Speichert die ArrayList listOfProblems in der Datei simplexProbleme.dat ab. Kann mit der Methode readHistory wieder eingelesen werden.
	 * @throws IOException
	 */	
	public void saveInputs()throws IOException{
		FileOutputStream fos = new FileOutputStream("simplexProblems.dat", false);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		int length = this.listOfInputs.size();
		oos.writeInt(length);
		for(int i=0;i<length;i++){ // f�r alle Probleme
			int size = this.listOfInputs.get(i).size();
			oos.writeInt(size);
			for(int j=0;j<size;j++){ // f�r die einzelnen Inputs jedes Problems
				this.listOfInputs.get(i).get(j).writeObject(oos);
			}			
		}		
		oos.close();
	}	
}
