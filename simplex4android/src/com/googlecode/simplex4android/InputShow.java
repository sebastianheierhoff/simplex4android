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
	    
	    //Weitere Nebenbedingung anlegen - Button
    	final Button constraint_new = (Button) findViewById(R.id.button_new_constraint);
	    constraint_new.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	Intent ConstraintCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
	        	ConstraintCreateIntent.putExtra("maxi", input.get(0).getValues().size());
	        	ConstraintCreateIntent.putExtra("create", true);
	        	startActivityForResult(ConstraintCreateIntent, CONSTRAINT_CREATE_REQUEST);
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
        	        input.add(target);
                	
        		    if(input.size()>0){
        		    	findViewById(R.id.text_target_empty).setVisibility(View.INVISIBLE);
        		    }
        		    if(input.size()>1){
        		    	findViewById(R.id.text_constraint_empty).setVisibility(View.INVISIBLE);
        		    }
        	        
        		    String[] target_string = new String[1];
        		    target_string[0] = input.get(0).toString();
        		    
        	        ListView listInputs = (ListView) findViewById(R.id.list_target);
        	        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, target_string);
        	        listInputs.setAdapter(adapter);
	    			Toast.makeText(InputShow.this,"Zielfunktion angelegt",Toast.LENGTH_LONG).show();
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
        	        
                    List<Input> constraints = input.subList(1, input.size());
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
	    			Toast.makeText(InputShow.this,"Nebenbedingung angelegt",Toast.LENGTH_LONG).show();
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
