//Umschreiben 
package com.googlecode.simplex4android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InputsDb {
	
	/**
	 * Liste zum speichern von Problemen
	 */
	private ArrayList<ArrayList<Input>> listOfInputs = new ArrayList<ArrayList<Input>>();
	
	/**
	 * leerer Konstruktor zum anlegen der Liste der Probleme
	 */
	public InputsDb(){}
	
	/**
	 * Konstruktor um Liste der zu speichernden Probleme zu erstellen und direkt eins einzufügen
	 * @param problem
	 */
	public InputsDb(SimplexProblem problem){
		listOfInputs.add(problem);
	}
	
	/**
	 * Methode zum hinzufügen eines Problems
	 * @param problem
	 */
	public void addProblem(SimplexProblem problem){
		listOfInputs.add(problem);
	}
	
	/**
	 * Gibt array mit den Namen der einzelnen Namen der Probleme zurück 
	 * @return String[] mit den Namen der Probleme
	 */
	public String[] getNames(){
		String[] s = new String[listOfInputs.size()-1];
		for(int i=0;i<listOfInputs.size();i++){
			s[i] = listOfInputs.get(i).getName();
		}
		return s;
	}
	
	/**
	 * 
	 * @return gibt die komplette Liste mit den gespeicherten Problemen zurück
	 */
	public ArrayList<SimplexProblem> getListOfProblems(){
		return this.listOfInputs;
	}
	
	/**
	 * Methode, die aus der Datei simplexProbleme.dat, eine ArrayList<SimplexProblem> ausliest und in listOfProblemes speichert.
	 *  
	 * @throws ClassNotFoundException	wird nur geschmissen wenn die Klasse SimplexProblem nicht gefunden wird
	 * @throws IOException
	 * @throws FileNotFoundException	muss aufgefangen werden und in GUI behandelt werden falls die Datei simplexProbleme.dat nicht im Programmordner gefunden wird.
	 */
	
	@SuppressWarnings("unchecked")
	public void readProblems()throws ClassNotFoundException, IOException, FileNotFoundException{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		fis = new FileInputStream("simplexProbleme.dat");
		ois = new ObjectInputStream(fis);
		listOfInputs = (ArrayList<SimplexProblem>) ois.readObject();
		ois.close();
	}

	/**
	 * Speichert die ArrayList listOfProblems in der Datei simplexProbleme.dat ab. Kann mit der Methode readHistory wieder eingelesen werden.
	 * @throws IOException
	 */
	
	
	public void saveProblems()throws IOException{
		FileOutputStream fos = new FileOutputStream("simplexProbleme.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(listOfInputs);
		oos.close();
	}
	
	
}
