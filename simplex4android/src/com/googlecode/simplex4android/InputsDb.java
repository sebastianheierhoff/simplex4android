package com.googlecode.simplex4android;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

/**
 * Klasse zum Speichern und Laden alles bisher erstellen SimplexProbleme in Simplex4Android.
 */
public class InputsDb {

	private ArrayList<ArrayList<Input>> listOfInputs;//Liste zum Speichern von Problemen

	public InputsDb(Context context) throws StreamCorruptedException, IOException, ClassNotFoundException{
		listOfInputs = new ArrayList<ArrayList<Input>>();
		this.readFile(context);
	}
	
	public void setListOfInputs(ArrayList<ArrayList<Input>> listOfInputs){
		this.listOfInputs = listOfInputs;
	}
	
	public ArrayList<ArrayList<Input>> getListOfInputs(){
		return this.listOfInputs;
	}
	
	public void addInput(Context context, ArrayList<Input> input) throws Exception{
		listOfInputs.add(input);
		this.writeFile(context);
	}

	public void setInput(Context context, int i, ArrayList<Input> input) throws Exception{
		this.listOfInputs.set(i, input);
		this.writeFile(context);
	}
	
	public void removeInput(Context context, int position) throws Exception{
		this.readFile(context);
		listOfInputs.remove(position);
		this.writeFile(context);
	}
	
	public ArrayList<Input> getInput(int i){
		return listOfInputs.get(i);	
	}
	
	public String[] getNames() throws NegativeArraySizeException{
		String[] s = new String[listOfInputs.size()-1];
		for(int i=0;i<listOfInputs.size();i++){
			s[i] = listOfInputs.get(i).get(0).toString();
		}
		return s;
	}
	
	public void writeFile(Context context) throws Exception {
		try{
			context.getFileStreamPath("simplexProblems.dat").delete();
			Toast.makeText(context, "Datei gelöscht!", Toast.LENGTH_SHORT);
		}
		catch(Exception ex){
			Toast.makeText(context, "Fehler beim Löschen der Datei!", Toast.LENGTH_SHORT);
		}
		FileOutputStream fos = context.openFileOutput("simplexProblems.dat", Context.MODE_PRIVATE);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(listOfInputs);
		oos.flush();
		oos.close();
	}

	@SuppressWarnings("unchecked")
	public void readFile(Context context) throws StreamCorruptedException, IOException, ClassNotFoundException{
		FileInputStream fis = null;
		try{
			fis = context.openFileInput("simplexProblems.dat");
		}
		catch(FileNotFoundException e){
			return; // Abbruch, keine Datei vorhanden.
		}
		ObjectInputStream ois = new ObjectInputStream(fis);
		listOfInputs = (ArrayList<ArrayList<Input>>) ois.readObject();
		ois.close();
	}
}
