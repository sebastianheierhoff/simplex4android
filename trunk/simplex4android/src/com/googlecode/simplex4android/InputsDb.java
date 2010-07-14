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

	private ArrayList<ArrayList<Input>> listOfInputs = new ArrayList<ArrayList<Input>>(); //Liste zum Speichern von Problemen

	/**
	 * Leerer Konstruktor zum Anlegen der Liste der Probleme
	 */
	public InputsDb(){}

	/**
	 * Konstruktor um Liste der zu speichernden Probleme zu erstellen und direkt das erste einzufügen.
	 * @param problem
	 */
	public InputsDb(ArrayList<Input> problem){
		listOfInputs.add(problem);
	}

	/**
	 * Methode zum Hinzufügen eines Problems
	 * @param problem
	 */
	public void addProblem(ArrayList<Input> problem){
		listOfInputs.add(problem);
	}

	protected ArrayList<ArrayList<Double>> convertTo2DArrayList(double[][] array){
		ArrayList<ArrayList<Double>> tableau = new ArrayList<ArrayList<Double>>();
		for(int i=0;i<array.length;i++){
			tableau.add(i,new ArrayList<Double>());
			for(int j=0;j<array[0].length;j++){
				tableau.get(i).add(j,new Double(array[i][j]));
			}
		}
		return tableau;
	}

	/**
	 * Gibt Array mit den Namen der einzelnen Namen der Probleme zurück.
	 * @return String[] mit den Namen der Probleme
	 */
	public String[] getNames(){
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
	public ArrayList<ArrayList<Input>> getListOfProblems(){
		return this.listOfInputs;
	}

	/**
	 * Liest aus der Datei simplexProbleme.dat, eine ArrayList<SimplexProblem> aus und speichert diese in listOfInputs.
	 *  
	 * @throws ClassNotFoundException	wird nur geschmissen wenn die Klasse SimplexProblem nicht gefunden wird
	 * @throws IOException
	 */	
	@SuppressWarnings("unchecked")
	public void readProblems() throws ClassNotFoundException, IOException{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = new FileInputStream("simplexProbleme.dat");
		}
		catch(FileNotFoundException e){
			FileOutputStream fos = new FileOutputStream("simplexProbleme.dat"); //neue Datei anlegen
			fos.close();
			return;
		}
		ois = new ObjectInputStream(fis);
		listOfInputs = (ArrayList<ArrayList<Input>>) ois.readObject();
		ois.close();		
	}

	/**
	 * Speichert die ArrayList listOfProblems in der Datei simplexProbleme.dat ab. Kann mit der Methode readHistory wieder eingelesen werden.
	 * @throws IOException
	 */	
	public void saveProblems()throws IOException{
		FileOutputStream fos = new FileOutputStream("simplexProbleme.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Object[] inputArrays = listOfInputs.toArray();
		oos.writeObject(inputArrays);
		oos.close();
	}	
}
