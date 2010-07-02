package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConstraintEdit extends Activity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.constraint_edit);
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	
	    
		//Zielfunktion: ausgrauen von <, <=, >=, >
		int[] keyboardButtons = {	R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5,
									R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
									R.id.button_x1, R.id.button_x2, R.id.button_x3, R.id.button_x4, R.id.button_xi,
									R.id.button_plus, R.id.button_minus, R.id.button_divide, R.id.button_decimal, R.id.button_eq,
									R.id.button_gt, R.id.button_geq, R.id.button_lt, R.id.button_leq, R.id.button_max, R.id.button_min, R.id.button_backspace};
		
		for(int i=0; i<keyboardButtons.length; i++){
			Button[] buttons = new Button[keyboardButtons.length];
			buttons[i] = (Button) findViewById(keyboardButtons[i]);
		    buttons[i].setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
		        	EditText text = (EditText) findViewById(R.id.target);
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
	
	
	

