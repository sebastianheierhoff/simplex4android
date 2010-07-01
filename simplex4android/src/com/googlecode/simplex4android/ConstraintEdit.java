package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class ConstraintEdit extends Activity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.constraint_edit);
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
