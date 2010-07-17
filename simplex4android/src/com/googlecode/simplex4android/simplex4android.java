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
 *		lädt View zum ändern/anlegen von Problemen mit gespeicherten Werten
 *		laden Ausgabe, Zurückbtn, um wieder auf den HomeScreen zurück zu gelangen, Speichern Button, um zur ListView zurück zu kehren
 */	
	
	//ResultCodes
	private static final int CONSTRAINT_EDIT_RESULT = 1;
	private static final int CONSTRAINT_CREATE_RESULT = 2;
	private static final int TARGET_EDIT_RESULT = 3;
	private static final int TARGET_CREATE_RESULT = 4;
	
	//RequestCodes
	private static final int CONSTRAINT_EDIT_REQUEST = 1;
	private static final int CONSTRAINT_CREATE_REQUEST = 2;
	private static final int TARGET_EDIT_REQUEST = 3;
	private static final int TARGET_CREATE_REQUEST = 4;
    
	//Ressourcen
	private static ArrayList<Input> inputs;
	private static int id;
    
    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    //Ressourcen
	    final Button btn_current = (Button) findViewById(R.id.btn_current);
	    final Button btn_new = (Button) findViewById(R.id.btn_new);
    	final Button btn_load = (Button) findViewById(R.id.btn_load);
    	final Button btn_tutorial = (Button) findViewById(R.id.btn_tutorial);

	    //Behandlung der verschiedenen Intents
	    if(this.getIntent().getBooleanExtra("edit", false) == true){
		    inputs = (ArrayList<Input>) this.getIntent().getSerializableExtra("inputs");
		    id = this.getIntent().getIntExtra("id", -1);
	    	btn_current.setEnabled(true);
	    }
	    
	    //Aktuelles Problem - Button
	    btn_current.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v){
	    		Intent InputCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputShow");
	        	InputCreateIntent.putExtra("edit", true);
	    		InputCreateIntent.putExtra("inputs", inputs);
	    		InputCreateIntent.putExtra("id", id);
	    		startActivity(InputCreateIntent);
	    	}
	    });
	    
	    //Neues Problema anlegen - Button
	    btn_new.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v){
	        	Intent InputCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputShow");
	        	InputCreateIntent.putExtra("create", true);
	        	startActivity(InputCreateIntent);
	    	}
	    });
	    	    
	    //Problem laden - Button
	    btn_load.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	Intent InputsLoadIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputsLoad");
	        	startActivity(InputsLoadIntent);
	        }
	    });
	    
	    //Tutorial - Button
	    btn_tutorial.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            //TODO: WebView anlegen, HTML-Dokumentation einbinden.
	        	Intent TutorialIntent = new Intent();
	            TutorialIntent.setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.Tutorial");
	        	startActivity(TutorialIntent);
	        }
	    });
	}
}    

	    
