package com.googlecode.simplex4android;

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
 *			laden Ausgabe, Zurückbutton, um wieder auf den HomeScreen zurück zu gelangen, Speichern Button, um zur ListView zurück zu kehren
 */	
	
	//TODO: Standardisieren/Anpassen!
    static final int INPUT_CREATE_REQUEST = 0;
    static final int CREATE_TARGET_REQUEST = 1;
    static final int VIEW_CONSTRAINT_REQUEST = 2;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    //NEUES PROBLEM ANLEGEN
	    final Button button_new = (Button) findViewById(R.id.button_new);
	    button_new.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v){
	        	Intent InputCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputShow");
	        	InputCreateIntent.putExtra("create", true);
	        	startActivity(InputCreateIntent);
	    	}
	    });
	    	    
	    //PROBLEM LADEN
    	final Button button_load = (Button) findViewById(R.id.button_load);
	    button_load.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	Intent InputsLoadIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputsLoad");
	        	startActivity(InputsLoadIntent);
	        }
	    });
	    
	    //TUTORIAL
//    	final Button button_tutorial = (Button) findViewById(R.id.button_tutorial);
//	    button_tutorial.setOnClickListener(new OnClickListener() {
//	        public void onClick(View v) {
//	            Intent TutorialIntent = new Intent();
//	            TutorialIntent.setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.Tutorial");
//	        	startActivity(TutorialIntent);
//	        }
//	    });
    
	}
}    

	    
