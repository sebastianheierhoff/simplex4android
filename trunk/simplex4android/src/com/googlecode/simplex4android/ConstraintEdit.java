package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

public class ConstraintEdit extends Activity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.constraint_edit);
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
	    EditText target = (EditText) findViewById(R.id.target);
	    target.setOnFocusChangeListener(new OnFocusChangeListener(){
	    	public void onFocusChange(View v, boolean b){
	    		if(b==true){
		    		findViewById(R.id.keyboard).setVisibility(View.VISIBLE);
		    		findViewById(R.id.button_back).setVisibility(View.INVISIBLE);
		    		findViewById(R.id.button_add).setVisibility(View.INVISIBLE);
		    		findViewById(R.id.simplex4android_logo).setVisibility(View.INVISIBLE);
	    		}
	    		else{
		    		findViewById(R.id.keyboard).setVisibility(View.INVISIBLE);
		    		findViewById(R.id.button_back).setVisibility(View.VISIBLE);
		    		findViewById(R.id.button_add).setVisibility(View.VISIBLE);
		    		findViewById(R.id.simplex4android_logo).setVisibility(View.VISIBLE);
	    		}
	    	}
		});

//	    target.setOnClickListener(new OnClickListener() {
//	    	public void onClick(View v){
//	    		findViewById(R.id.keyboard).setVisibility(View.VISIBLE);
//	    	}
//	    });
	    
		//Zielfunktion: ausgrauen von <, <=, >=, >
		int[] keyboardButtons = {	R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5,
									R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
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
	
	
	

