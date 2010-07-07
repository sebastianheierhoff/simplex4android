package com.googlecode.simplex4android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class InputShow extends Activity{

	static final int CREATE_CONSTRAINT_REQUEST = 0;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.input_show);
	    
	    if(this.getIntent().getBooleanExtra("create", false)){
        	Intent ConstraintEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
        	startActivityForResult(ConstraintEditIntent, CREATE_CONSTRAINT_REQUEST);
	    }
	}
}
