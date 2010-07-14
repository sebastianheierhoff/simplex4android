package com.googlecode.simplex4android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Klasse SimplexHistory - speichert SimplexProbleme.
 * @author Simplex4Android
 */
public class SimplexHistory {

	/**
	 * History zur Verwaltung von SimplexPrioblemen.
	 */
	private ArrayList<SimplexProblem> history = new ArrayList<SimplexProblem>();
	
	/**
	 * Leerer Konstruktor zum Anlegen einer SimplexHistory.
	 */
	public SimplexHistory(){
		
	}
	
	/**
	 * F�gt der SimplexHistory ein neues Element hinzu.
	 * @param tableau einzuf�gendes SimplexProblem
	 */
	public void addElement(SimplexProblem tableau){
		this.history.add(tableau);
	}
	
	/**
	 * Gibt das Element mit dem Index index zur�ck.
	 * @param index Index des auszugebenen Elements
	 * @return Element mit Index index
	 * @throws java.lang.IndexOutOfBoundsException
	 */
	public SimplexProblem getElement(int index) throws java.lang.IndexOutOfBoundsException{
		return this.history.get(index);
	}

	/**
	 * Gibt das erste Element der SimplexHistory zur�ck.
	 * @return erstes Element der SimplexHistory
	 */
	public SimplexProblem getFirstElement(){
		return this.history.get(0);
	}
	
	/**
	 * Gibt das letzte Element der SimplexHistory zur�ck.
	 * @return letztes Element der SimplexHistory
	 */
	public SimplexProblem getLastElement(){
		return this.history.get(this.history.size()-1);
	}

	/**
	 * Methode, die aus der Datei simplexHistory, eine ArrayList<SimplexProblem> ausliest und in history speichert.
	 *  
	 * @throws ClassNotFoundException	wird nur geschmissen wenn die Klasse SimplexProblem nicht gefunden wird
	 * @throws IOException
	 * @throws FileNotFoundException	muss aufgefangen werden und in GUI behandelt werden falls die Datei simplexHistory.dat nicht im Programmordner gefunden wird.
	 */
	
	@SuppressWarnings("unchecked")
	public void readHistory()throws ClassNotFoundException, IOException, FileNotFoundException{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		fis = new FileInputStream("simplexHistory.dat");
		ois = new ObjectInputStream(fis);
		history = (ArrayList<SimplexProblem>) ois.readObject();
		ois.close();
	}

	/**
	 * Speichert die ArrayList history in der Datei simplexHistory ab. Kann mit der Methode readHistory wieder eingelesen werden.
	 * @throws IOException
	 */
	
	
	public void saveHistory()throws IOException{
		FileOutputStream fos = new FileOutputStream("simplexHistory.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(history);
		oos.close();
	}
	
	/**
	 * Gibt die Gr��e der History zur�ck
	 * @return Gr��e der History
	 */
	public int size(){
		return this.history.size();
	}
	
	public String toString(){
		String s = null;
		for(int i=0;i<history.size();i++){
			s = s + "\n \n i=" + i + history.get(i).tableauToHtml();
		}
		return s;
	}
	
	/**
	 * l�scht ein Element an Stelle i
	 */
	public void deleteElement(int i){
		this.history.remove(i);
	}
	
}
