package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class TargetEdit extends Activity {

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
	
	static Target target = new Target();
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	    setContentView(R.layout.target_edit);

	    if(this.getIntent().getBooleanExtra("create", false) ==  true){
	    	target = new Target();
	    }
	    else{
	    	//Constraint laden
	    }
	    
		//Spinner minmax
		Spinner minmax = (Spinner) findViewById(R.id.spinner_minmax);
		ArrayAdapter<CharSequence> adapter_minmax = ArrayAdapter.createFromResource(this, R.array.spinner_minmax_values, android.R.layout.simple_spinner_item); 
		adapter_minmax.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		minmax.setAdapter(adapter_minmax);

	    minmax.setOnItemSelectedListener(new OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		    	//Index "0" enspricht "min", "1" entspricht "max"
		    	////true bedeutet Minimierung, false bedeutet Maximierung
		    	if(arg3 == 0){
		    		target.setMinOrMax(true);
		    	}
		    	else{
		    		target.setMinOrMax(false);
		    	}
		    }
		    	 
		    public void onNothingSelected(AdapterView<?> arg0) {}
	    });
	    
	    //Textfeld Target-Element
	    EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	    target_element.setOnFocusChangeListener(new OnFocusChangeListener(){
	    	public void onFocusChange(View v, boolean b){
	    		if(b==true){
		    		findViewById(R.id.keyboard).setVisibility(View.VISIBLE);
	    		}
	    		else{
		    		findViewById(R.id.keyboard).setVisibility(View.INVISIBLE);
	    		}
	    	}
		});

	    //Keyboard-Button
	    int[] keyboardButtons = {	R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, 
									R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
									R.id.button_minus, R.id.button_divide, R.id.button_decimal, R.id.button_backspace};
		
		for(int i=0; i<keyboardButtons.length; i++){
			Button[] buttons = new Button[keyboardButtons.length];
			buttons[i] = (Button) findViewById(keyboardButtons[i]);
		    buttons[i].setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
		        	EditText text = (EditText) findViewById(R.id.edittext_target_element);
		        	String newtext = text.getText().toString(); 
		        	if(v.getTag().equals("backspace")){
		        		if(newtext.length() > 0){
		        			newtext = newtext.substring(0, newtext.length()-1);
		        		}
		        	}
		        	else{
		        		newtext += v.getTag();
		        	}
		        	if(!SimplexLogic.checkInput(newtext)){
		        		text.setBackgroundResource(R.drawable.textfield_pressed_red);//Hintergrund rot
		        	}
		        	else{
		        		text.setBackgroundResource(R.drawable.textfield_default);
		        	}
	        		text.setText(newtext);
	        		Selection.setSelection(text.getText(), text.length());
		        }
		    });
		}
		
		//Hinzufügen-Button
	    final Button add_target_element = (Button) findViewById(R.id.button_add_target_element);
	    add_target_element.setOnClickListener (new OnClickListener(){
	    	public void onClick(View v){
	        	EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	        	EditText target = (EditText) findViewById(R.id.edittext_target);
	        	if(SimplexLogic.checkInput(target_element.getText().toString())){
	        		double value;
	        		if(target_element.getText().toString().equals("")){
	        			value = 0;
	        		}
	        		else{
	        			value = Double.valueOf(target_element.getText().toString());
	        		}
	        		int i = Integer.valueOf(((EditText) findViewById(R.id.edittext_x)).getText().toString().substring(1)).intValue()-1;
		        	TargetEdit.target.setValue(i, value);
	    			target.setText(TargetEdit.target.valuesToString());
	        	}
	    		else{
	    			Toast.makeText(TargetEdit.this,"Fehlerhafte Eingabe! Bitte korrigieren!",Toast.LENGTH_LONG).show();
	    		}
	    	}
		});
	    
	    //"Xi erhöhen"-Button
	    final Button x_plus = (Button) findViewById(R.id.button_x_plus);
	    x_plus.setOnClickListener (new OnClickListener(){
	    	public void onClick(View V){
	    		EditText edittext_x = (EditText) findViewById(R.id.edittext_x); 
	    		EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	    		int edittext_x_value = Integer.valueOf(edittext_x.getText().toString().substring(1)).intValue();
	    		edittext_x_value++;//inkrementieren
	    		edittext_x.setText("x" + edittext_x_value);
	    		try{
	    			target_element.setText(String.valueOf(target.getValue(edittext_x_value-1)));
	    		}
	    		catch(IndexOutOfBoundsException e){
	    			target_element.setHint("0");
	    		}
	    		catch(Exception e){
	    			
	    		}
	    		try{
	    			target_element.requestFocus();
	    		}
	    		catch(Exception ex){
	    			Toast.makeText(TargetEdit.this,ex.getMessage(),Toast.LENGTH_LONG).show();
	    		}
	    	}
	    });

	    //"Xi verringern"-Button
	    final Button x_minus = (Button) findViewById(R.id.button_x_minus);
	    x_minus.setOnClickListener (new OnClickListener(){
	    	public void onClick(View V){
	    		EditText edittext_x = (EditText) findViewById(R.id.edittext_x); 
	    		EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	    		//target_element.setText("");
	    		int edittext_x_value = Integer.valueOf(edittext_x.getText().toString().substring(1)).intValue();
	    		if(edittext_x_value>1){
	    			edittext_x_value--;
	    			edittext_x.setText("x" + edittext_x_value);
		    		try{
		    			target_element.setText(String.valueOf(target.getValue(edittext_x_value-1)));

		    		}
		    		catch(IndexOutOfBoundsException e){
		    			target_element.setHint("0");
		    		}
		    		catch(Exception e){
		    			
		    		}
	    		}
	    		try{
	    			target_element.requestFocus();
	    		}
	    		catch(Exception ex){
	    			Toast.makeText(TargetEdit.this,ex.getMessage(),Toast.LENGTH_LONG).show();
	    		}
	    	}
	    });

	    //Fertig-Button
    	final Button add = (Button) findViewById(R.id.button_add);
	    add.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	    	    EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	        	if(target.getValues().isEmpty()){
	    			Toast.makeText(TargetEdit.this,"Eingabe unvollständig! Bitte mind. ein xi hinzufügen!",Toast.LENGTH_LONG).show();
        			target_element.setBackgroundResource(R.drawable.textfield_pressed_red);//Hintergrund rot
	    			return;
	        	}
	        	else{
	        		setResult(TARGET_CREATE_RESULT);
	        		finish();
	        	}
	        }
	    });
	    
	    //Zurück-Button
	    final Button back = (Button) findViewById(R.id.button_back);
	    back.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	finish();
	        }
	    });


	}	
}
	
	
	

