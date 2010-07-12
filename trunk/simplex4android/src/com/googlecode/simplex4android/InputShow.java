package com.googlecode.simplex4android;

import java.util.ArrayList;
import java.util.Iterator;

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
	static final int CONSTRAINT_EDIT_RESULT = 1;
	static final int CONSTRAINT_CREATE_RESULT = 2;
	static final int TARGET_EDIT_RESULT = 3;
	static final int TARGET_CREATE_RESULT = 4;

	//RequestCodes
	static final int CONSTRAINT_EDIT_REQUEST = 1;
	static final int CONSTRAINT_CREATE_REQUEST = 2;
	static final int TARGET_EDIT_REQUEST = 3;
	static final int TARGET_CREATE_REQUEST = 4;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    	setContentView(R.layout.input_show);
	    
	    if(this.getIntent().getBooleanExtra("create", false)){
        	Intent ConstraintEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
        	ConstraintEditIntent.putExtra("create", true);
        	startActivityForResult(ConstraintEditIntent, TARGET_EDIT_REQUEST);
	    }
	    else{
	    	
	    }
	
	    //Abbruch-Button
	    final Button back = (Button) findViewById(R.id.button_cancel);
	    back.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	finish();
	        }
	    });
	    
	    //Neue Zielfunktion anlegen - Button
    	final Button target_new = (Button) findViewById(R.id.button_new_target);
	    target_new.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v){
	        	Intent InputCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
	        	startActivity(InputCreateIntent);
	    	}
	    });
	    
	    //Neue Nebenbedingung anlegen - Button
    	final Button constraint_new = (Button) findViewById(R.id.button_new_constraint);
	    constraint_new.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	Intent ConstraintCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
	        	ConstraintCreateIntent.putExtra("maxi", input.get(0).getValues().size()-1);
	        	ConstraintCreateIntent.putExtra("create", true);
	        	startActivityForResult(ConstraintCreateIntent, CONSTRAINT_EDIT_REQUEST);
	        }
	    });
	    
	    //Rechnen-Button
	    
	    //Speichern-Button
	    
	
	}

	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (resultCode) {
        	case RESULT_CANCELED:
        		if(requestCode == TARGET_EDIT_REQUEST){
        			finish();
        		}
        		break;
        	case TARGET_EDIT_RESULT:
        		
        		break;
        	case TARGET_CREATE_RESULT:
            	try{
            		Target target = TargetEdit.target;
        	        input.add(0, target);
                	
        		    if(input.size()>0){
        		    	findViewById(R.id.text_target_empty).setVisibility(View.INVISIBLE);
        		    }
        		    if(input.size()>1){
        		    	findViewById(R.id.text_constraint_empty).setVisibility(View.INVISIBLE);
        		    }
        	        
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
	    			Toast.makeText(InputShow.this,"Unbekannter Fehler",Toast.LENGTH_LONG).show();
            	}
            	break;
        	case CONSTRAINT_EDIT_RESULT:
        		
        		break;
        	case CONSTRAINT_CREATE_RESULT:
            	try{
            		Constraint constraint = ConstraintEdit.constraint;
        	        input.add(constraint);
                	
        		    if(input.size()>0){
        		    	findViewById(R.id.text_target_empty).setVisibility(View.INVISIBLE);
        		    }
        		    if(input.size()>1){
        		    	findViewById(R.id.text_constraint_empty).setVisibility(View.INVISIBLE);
        		    }
        	        
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
	    			Toast.makeText(InputShow.this,"Unbekannter Fehler",Toast.LENGTH_LONG).show();
            	}
            	break;
			default:
                break;
        }
    }

	
}
