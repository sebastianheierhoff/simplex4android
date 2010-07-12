package com.googlecode.simplex4android;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class InputShow extends Activity{
	
	ArrayList<Input> input = new ArrayList<Input>();

	//ResultCodes
	static final int CONSTRAINT_EDIT_RESULT = 0;
	
	//RequestCodes
	static final int CONSTRAINT_EDIT_REQUEST = 0;
	static final int CREATE_TARGET_REQUEST = 1;

	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    	setContentView(R.layout.input_show);
	    
	    if(this.getIntent().getBooleanExtra("create", false)){
        	Intent ConstraintEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
        	startActivity(ConstraintEditIntent);
        	//startActivityForResult(ConstraintEditIntent, CREATE_TARGET_REQUEST);
	    }
	    //if(this.getIntent().getBooleanExtra("edit", false)){
	    else{
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
	        	Intent InputCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
	        	startActivity(InputCreateIntent);
	    	}
	    });
	    
	    //Neue Nebenbedingung anlegen
    	final Button constraint_new = (Button) findViewById(R.id.button_new_constraint);
	    constraint_new.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	Intent ConstraintCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
	        	startActivityForResult(ConstraintCreateIntent, CONSTRAINT_EDIT_REQUEST);
	        }
	    });
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (resultCode) {
            case CONSTRAINT_EDIT_RESULT:
            	try{
            		Constraint constraint = ConstraintEdit.constraint;
        	        input.add(constraint);
                	
                    //ArrayList<Constraints> constraints = (ArrayList<Constraint>) input.subList(1, input.size()-1);
        	        ArrayList<Input> constraints = input;
        	        String[] constraints_string = new String[constraints.size()];
                    Iterator<Input> iter = constraints.iterator(); 
                    int i = 0;
                    while(iter.hasNext() ) { 
                        constraints_string[i] = iter.next().toString();
                        i++;
                    }
                    
        	        ListView listInputs = (ListView) findViewById(R.id.list_constraint);
        	        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, constraints_string);
        	        listInputs.setAdapter(adapter);
            		
            	}
            	catch(Exception ex){
	    			Toast.makeText(InputShow.this,ex.getMessage(),Toast.LENGTH_LONG).show();
            	}
			default:
                break;
        }
    }

	
}
