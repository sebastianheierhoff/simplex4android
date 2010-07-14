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

	private ArrayList<ArrayList<Input>> listOfInputs = new ArrayList<ArrayList<Input>>(); //Liste zum Speichern von Problemen

	/**
	 * Leerer Konstruktor zum Anlegen der Liste der Probleme
	 */
	public InputsDb(){}

	/**
	 * Konstruktor um Liste der zu speichernden Probleme zu erstellen und direkt das erste einzuf�gen.
	 * @param problem
	 */
	public InputsDb(ArrayList<Input> problem){
		listOfInputs.add(problem);
	}

	/**
	 * Methode zum Hinzuf�gen eines Problems
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
	public void saveProblems()throws IOException{
		FileOutputStream fos = new FileOutputStream("simplexProbleme.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(listOfInputs.toArray());
		oos.close();
	}	
}
