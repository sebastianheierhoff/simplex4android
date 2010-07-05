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
    static final int CREATE_CONSTRAINT_REQUEST = 0;
    static final int CREATE_TARGET_REQUEST = 1;
    static final int VIEW_CONSTRAINT_REQUEST = 2;
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case CREATE_CONSTRAINT_REQUEST:
                if (resultCode == RESULT_CANCELED){
                } 
                else {
                	Intent constraintView = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintView");
                	startActivity(constraintView);
                }
            default:
                break;
        }
    }
    
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    final Button button_new = (Button) findViewById(R.id.button_new);
	    button_new.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v){
	        	Intent ConstraintEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
	        	ConstraintEditIntent.putExtra("target", true);
	        	startActivityForResult(ConstraintEditIntent, CREATE_CONSTRAINT_REQUEST);
	    	}
	    });
	    	    
    	final Button button_load = (Button) findViewById(R.id.button_load);
	    button_load.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            Intent SimplexHistoryLoadIntent = new Intent();
	            SimplexHistoryLoadIntent.setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.SimplexHistoryLoad");
	        	startActivity(SimplexHistoryLoadIntent);
	        }
	    });
	    
	    
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

	    
