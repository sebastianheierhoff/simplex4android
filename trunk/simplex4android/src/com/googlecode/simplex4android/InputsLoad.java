package com.googlecode.simplex4android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InputsLoad extends Activity {
	
	private static ArrayAdapter<String> adapter_list_problems;
	private InputsDb mInputsDb;

	    /** Called when the activity is first created. */
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.inputs_load);

	        mInputsDb = new InputsDb();
	        try{
	        	mInputsDb.readInputs();
	        }
	        catch(Exception e){
	        	//TODO: Fehlermeldung ausgeben!
	        }

	        ListView listInputs = (ListView) findViewById(R.id.list_problems);
	        try{
	            ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview_inputs, R.id.tv_row, mInputsDb.getNames());
	            listInputs.setAdapter(adapter);
	        }catch(NegativeArraySizeException nase){
	        	
	        }
	    }
	    
		public void DeleteClickHandler(View v){
			ListView lv_problems = (ListView) findViewById(R.id.list_problems);
			RelativeLayout rl_row = (RelativeLayout)v.getParent();
	        int position = lv_problems.indexOfChild(rl_row);
	        adapter_list_problems.remove(adapter_list_problems.getItem(position));
//	        inputs.remove(position +1);
	        hideOrShowEmptyText();
		}

		public void EditClickHandler(View v)
		{
//	        Target target = (Target) inputs.get(0);
//	        Intent ConstraintEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
//	        ConstraintEditIntent.putExtra("target", target);
//	        ConstraintEditIntent.putExtra("maxi_old", target.getValues().size());
//	        ConstraintEditIntent.putExtra("edit", true);
//	    	startActivityForResult(ConstraintEditIntent, TARGET_EDIT_REQUEST);
		}

		private void hideOrShowEmptyText(){
//			TextView txt_target_empty = (TextView) findViewById(R.id.text_target_empty);
//			ViewGroup.LayoutParams params_target = txt_target_empty.getLayoutParams();
//			if(inputs.get(0) != null){
//				params_target.height = 0;
//			}
//			else{
//				params_target.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//			}
//			txt_target_empty.requestLayout();
//
//			TextView txt_constraint_empty = (TextView) findViewById(R.id.text_constraint_empty);
//			ViewGroup.LayoutParams params_constraint = txt_constraint_empty.getLayoutParams();
//			if(inputs.size()>1){
//				params_constraint.height = 0;
//			}
//			else{
//				params_constraint.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//				
//			}
//			txt_constraint_empty.requestLayout();
		}
}
