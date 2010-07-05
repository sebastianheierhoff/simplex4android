package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ConstraintEdit extends Activity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	    
	  //if(!OptionbeimAufrufenabfragen! != target)
	    setContentView(R.layout.constraint_edit);
	    //else 
	    setContentView(R.layout.target_edit);
	    
	    //Spinner minmax
	    Spinner minmax = (Spinner) findViewById(R.id.spinner_minmax);
	    ArrayAdapter<CharSequence> adapter_minmax = ArrayAdapter.createFromResource(this, R.array.spinner_minmax_values, android.R.layout.simple_spinner_item); 
	    adapter_minmax.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    minmax.setAdapter(adapter_minmax);

	    //Spinner gtltoreq
	    Spinner gtltoreq = (Spinner) findViewById(R.id.spinner_gtltoreq);
	    ArrayAdapter<CharSequence> adapter_gtltoreq = ArrayAdapter.createFromResource(this, R.array.spinner_gtltoreq_values, android.R.layout.simple_spinner_item); 
	    adapter_gtltoreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    gtltoreq.setAdapter(adapter_gtltoreq);
	    
	    
	    
	    
	    
	    
	    EditText target = (EditText) findViewById(R.id.edittext_target_element);
	    target.setOnFocusChangeListener(new OnFocusChangeListener(){
	    	public void onFocusChange(View v, boolean b){
	    		if(b==true){
		    		findViewById(R.id.keyboard).setVisibility(View.VISIBLE);
	    		}
	    		else{
		    		findViewById(R.id.keyboard).setVisibility(View.INVISIBLE);
	    		}
	    	}
		});

//	    target.setOnClickListener(new OnClickListener() {
//	    	public void onClick(View v){
//	    		findViewById(R.id.keyboard).setVisibility(View.VISIBLE);
//	    	}
//	    });
	    
		//Zielfunktion: ausgrauen von <, <=, >=, >
		int[] keyboardButtons = {	R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, 
									R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
									R.id.button_minus, R.id.button_divide, R.id.button_decimal};
		
		for(int i=0; i<keyboardButtons.length; i++){
			Button[] buttons = new Button[keyboardButtons.length];
			buttons[i] = (Button) findViewById(keyboardButtons[i]);
		    buttons[i].setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
		        	EditText text = (EditText) findViewById(R.id.target_element);
		        	String newtext = text.getText().toString(); 
		        	//Verbesserungsmöglichkeit: Taste gedrückt?
		        	if(v.getTag().equals("backspace")){
		        		if(newtext.length() > 0){
		        			newtext = newtext.substring(0, newtext.length()-1);
		        		}
		        	}
		        	else{
		        		newtext += v.getTag();
		        	}
		        	text.setText(newtext);
		        	Selection.setSelection(text.getText(), text.length());
		        }
		    });
		}
		
    	final Button back = (Button) findViewById(R.id.button_back);
	    back.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	finish();
	        }
	    });
	    
	    final Button add_target_element = (Button) findViewById(R.id.button_add_target_element);
	    add_target_element.setOnClickListener (new OnClickListener(){
	    	public void onClick(View V){
	    		//Eingabe prüfen
	    	}
	    });
	    
	    final Button x_plus = (Button) findViewById(R.id.button_x_plus);
	    x_plus.setOnClickListener (new OnClickListener(){
	    	public void onClick(View V){
	    		EditText edittext_x = (EditText) findViewById(R.id.edittext_x); 
	    		int edittext_x_value = edittext_x.getText().toString().substring(1); //to intValue;
	    		edittext_x_value++;//inkrementieren
	    		edittext_x.setText("x" + edittext_x_value);
	    				//Value aus ArrayList abfragen und ins Textfeld eintragen
	    	}
	    });
	    
		
	}	
//		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).getCurrentInputConnection()
	    
//	    try{
//	    	KeyboardView mInputView = (KeyboardView) getLayoutInflater().inflate(R.layout.constraint_edit, null);
//		    Keyboard simplexkeyboard = new Keyboard(this, R.layout.simplexkeyboard);
//		    mInputView.setKeyboard(simplexkeyboard);
//	    }
//	    catch(Exception e){
//	        Toast.makeText(ConstraintEdit.this, "Fehler", Toast.LENGTH_LONG).show();
//	    }
}
	
	
	

