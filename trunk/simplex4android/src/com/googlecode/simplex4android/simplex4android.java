package com.googlecode.simplex4android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class simplex4android extends Activity {
	
/*	
 * Startet entweder 
 *		Activity um neues Problem anzulegen, oder 
 *		ListView um Probleme zu laden
 *			lädt View zum ändern/anlegen von Problemen mit gespeicherten Werten
 *			
 *			laden Ausgabe, Zurückbtn, um wieder auf den HomeScreen zurück zu gelangen, Speichern Button, um zur ListView zurück zu kehren
 */	
	
	//TODO: Standardisieren/Anpassen!
    static final int INPUT_CREATE_REQUEST = 0;
    static final int CREATE_TARGET_REQUEST = 1;
    static final int VIEW_CONSTRAINT_REQUEST = 2;
    
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    final ArrayList<Input> inputs;
	    
	    final Button btn_current = (Button) findViewById(R.id.btn_current);

	    if(this.getIntent().getBooleanExtra("currentProblem", false) == true){
	    	btn_current.setEnabled(true);
	    }
	    
	    inputs = (ArrayList<Input>) this.getIntent().getSerializableExtra("inputs");

	    //Aktuelles Problem - Button
	    btn_current.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v){
	    		Intent InputCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputShow");
	        	InputCreateIntent.putExtra("currentProblem", true);
	    		InputCreateIntent.putExtra("inputs", inputs);
	    		startActivity(InputCreateIntent);
	    	}
	    });
	    
	    //NEUES PROBLEM ANLEGEN
	    final Button btn_new = (Button) findViewById(R.id.btn_new);
	    btn_new.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v){
	        	Intent InputCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputShow");
	        	InputCreateIntent.putExtra("create", true);
	        	startActivity(InputCreateIntent);
	    	}
	    });
	    	    
	    //PROBLEM LADEN
    	final Button btn_load = (Button) findViewById(R.id.btn_load);
	    btn_load.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	Intent InputsLoadIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputsLoad");
	        	startActivity(InputsLoadIntent);
	        }
	    });
	    
	    //TUTORIAL
//    	final Button btn_tutorial = (Button) findViewById(R.id.btn_tutorial);
//	    btn_tutorial.setOnClickListener(new OnClickListener() {
//	        public void onClick(View v) {
//	            Intent TutorialIntent = new Intent();
//	            TutorialIntent.setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.Tutorial");
//	        	startActivity(TutorialIntent);
//	        }
//	    });
    
	}
}    

	    
