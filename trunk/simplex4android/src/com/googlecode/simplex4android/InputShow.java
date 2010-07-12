package com.googlecode.simplex4android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InputShow extends Activity{
	
	ArrayList<Input> input = new ArrayList<Input>();

	static final int CREATE_CONSTRAINT_REQUEST = 0;
	static final int CREATE_TARGET_REQUEST = 1;

	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.input_show);
	    
	    if(this.getIntent().getBooleanExtra("create", false)){
        	Intent ConstraintEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
        	startActivityForResult(ConstraintEditIntent, CREATE_TARGET_REQUEST);
	    }
	    if(this.getIntent().getBooleanExtra("edit", false)){
	    
	    }
	
    	final Button back = (Button) findViewById(R.id.button_cancel);
	    back.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	finish();
	        }
	    });
	    
	    //Neue Zielfunktion anlegen
    	final Button target_new = (Button) findViewById(R.id.button_new_target);
	    target_new.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v){
	        	Intent InputCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputShow");
	        	startActivity(InputCreateIntent);
	    	}
	    });
	    
	    //Neue Nebenbedingung anlegen
    	final Button constraint_new = (Button) findViewById(R.id.button_new_target);
	    constraint_new.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	Intent ConstraintCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
	        	startActivity(ConstraintCreateIntent);
	        }
	    });

	    
	}
}
