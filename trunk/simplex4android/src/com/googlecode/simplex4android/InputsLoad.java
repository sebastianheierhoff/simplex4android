package com.googlecode.simplex4android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InputsLoad extends Activity {
	
	private static ArrayAdapter<String> adapter_list_problems;
	private ArrayList<ArrayList<Input>> listOfInputs;
	private InputsDb mInputsDb;

	    /** Called when the activity is first created. */
	    public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	        setContentView(R.layout.inputs_load);

	    	try {
				mInputsDb = new InputsDb(InputsLoad.this);
	        } catch (Exception ex) {
				ex.printStackTrace();
	        }

	        ListView lv_problems = (ListView) findViewById(R.id.list_problems);
	        try{
	        	adapter_list_problems = new ArrayAdapter<String>(this, R.layout.listview_inputs, R.id.tv_row);
	    		listOfInputs = mInputsDb.getListOfInputs();
	            for(int i=1;i<listOfInputs.size();i++){ // durch alle Inputs
	    			adapter_list_problems.add(listOfInputs.get(i).get(0).toString());
	    		}
	        }catch(Exception ex){
	        	
	        }
            lv_problems.setAdapter(adapter_list_problems);
            lv_problems.refreshDrawableState();
	        
		    //Zurück-Button
		    final Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
		    btn_cancel.setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
		        	finish();
		     	}
		    });
		    
		    //Neues-Problem-Button
		    final Button btn_new_problem= (Button) findViewById(R.id.btn_new_problem);
		    btn_new_problem.setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
		        	Intent InputCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputShow");
		        	InputCreateIntent.putExtra("create", true);
		        	startActivity(InputCreateIntent);
		        	finish();
		        }
		    });
		    
	    }
	    
		public void DeleteClickHandler(View v){
			ListView lv_problems = (ListView) findViewById(R.id.list_problems);
			RelativeLayout rl_row = (RelativeLayout)v.getParent();
	        int position = lv_problems.indexOfChild(rl_row);
	        adapter_list_problems.remove(adapter_list_problems.getItem(position));
	        try{
	        	mInputsDb.removeInput(InputsLoad.this, position);
	        }
	        catch(Exception ex){
	        }	
        	hideOrShowEmptyText();
		}

		public void EditClickHandler(View v)
		{
			ListView lv_problems = (ListView) findViewById(R.id.list_problems);
			RelativeLayout rl_row = (RelativeLayout)v.getParent();
	        int position = lv_problems.indexOfChild(rl_row);
	        ArrayList<Input> inputs = mInputsDb.getInput(position);
	        Intent InputsEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
	        InputsEditIntent.putExtra("inputs", inputs);
	        InputsEditIntent.putExtra("edit", true);
	        InputsEditIntent.putExtra("id", position);
	    	startActivity(InputsEditIntent);
		}

		private void hideOrShowEmptyText(){
			TextView text_list_empty = (TextView) findViewById(R.id.text_list_empty);
			ViewGroup.LayoutParams params_text_list_empty = text_list_empty.getLayoutParams();
			if(!mInputsDb.getListOfInputs().isEmpty()){
				params_text_list_empty.height = 0;
			}
			else{
				params_text_list_empty.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			}
			text_list_empty.requestLayout();
		}
}
