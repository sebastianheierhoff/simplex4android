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

public class ConstraintEdit extends Activity {

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

	protected static Constraint constraint;
	private EditText addto;
	private int maxi; //Höchstes xi in der Zielfunktion
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	    
	    setContentView(R.layout.constraint_edit);
	    
	    if(this.getIntent().getBooleanExtra("create", false) ==  true){
	    	constraint = new Constraint();
	    }
	    else{
	    	//Constraint laden
	    }
	    
	    addto = (EditText) findViewById(R.id.edittext_target_element);
	    addto.requestFocus();

	    maxi = this.getIntent().getIntExtra("maxi", 0);
	    if(maxi == 0){
        	setResult(RESULT_CANCELED);
	    	finish();
	    }
	    
	    //Spinner gtltoreq
		Spinner gtltoreq = (Spinner) findViewById(R.id.spinner_gtltoreq);
		ArrayAdapter<CharSequence> adapter_gtltoreq = ArrayAdapter.createFromResource(this, R.array.spinner_gtltoreq_values, android.R.layout.simple_spinner_item); 
		adapter_gtltoreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gtltoreq.setAdapter(adapter_gtltoreq);

	    gtltoreq.setOnItemSelectedListener(new OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		    	//Index "0" enspricht "<=", "2" entspricht "=" und "1" entspricht ">="
		    	//Sign "-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">="
		    	if(arg3 == 0){
		    		ConstraintEdit.constraint.setSign(-1);
		    	}
		    	else if(arg3 == 2){
		    		ConstraintEdit.constraint.setSign(0);
		    	}
		    	else{
		    		ConstraintEdit.constraint.setSign(1);
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
		    		addto = (EditText) findViewById(R.id.edittext_target_element);
	    		}
	    		else{
		    		findViewById(R.id.keyboard).setVisibility(View.INVISIBLE);
	    		}
	    	}
		});

	    //Textfeld Constraint-Target-Value
	    EditText target_value = (EditText) findViewById(R.id.edittext_constraint_target_value);
	    target_value.setOnFocusChangeListener(new OnFocusChangeListener(){
	    	public void onFocusChange(View v, boolean b){
	    		if(b==true){
		    		findViewById(R.id.keyboard).setVisibility(View.VISIBLE);
		    		addto = (EditText) findViewById(R.id.edittext_constraint_target_value);
	    		}
	    		else{
		    		findViewById(R.id.keyboard).setVisibility(View.INVISIBLE);
	    		}
	    	}
		});
	    
	    //Keyboard-Buttons
	    int[] keyboardButtons = {	R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, 
									R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9,
									R.id.button_minus, R.id.button_divide, R.id.button_decimal, R.id.button_backspace};
		
		for(int i=0; i<keyboardButtons.length; i++){
			Button[] buttons = new Button[keyboardButtons.length];
			buttons[i] = (Button) findViewById(keyboardButtons[i]);
		    buttons[i].setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
	        		EditText text = addto;
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
		        }});
		}
		
	    //Hinzufügen-Button
	    final Button add_target_element = (Button) findViewById(R.id.button_add_target_element);
	    add_target_element.setOnClickListener (new OnClickListener(){
	    	public void onClick(View V){
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
		        	constraint.setValue(i, value);
	    			target.setText(constraint.valuesToString());
	        	}
	    		else{
	    			Toast.makeText(ConstraintEdit.this,"Fehlerhafte Eingabe! Bitte korrigieren!",Toast.LENGTH_LONG).show();
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
	    		if(edittext_x_value == maxi){
	    			Toast.makeText(ConstraintEdit.this,"Fehler! Höchstes xi in der Zielfunktion = " + maxi,Toast.LENGTH_LONG).show();
	    		    //Meldung: Höher geht nicht, da höchste xi in der Zielfunktion = maxi
	    		}
	    		else{
	    			edittext_x_value++;//inkrementieren
	    		}    			
	    		edittext_x.setText("x" + edittext_x_value);
	    		try{
	    			target_element.setText(String.valueOf(constraint.getValue(edittext_x_value-1)));
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
	    			Toast.makeText(ConstraintEdit.this,ex.getMessage(),Toast.LENGTH_LONG).show();
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
		    			target_element.setText(String.valueOf(constraint.getValue(edittext_x_value-1)));

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
	    			Toast.makeText(ConstraintEdit.this,ex.getMessage(),Toast.LENGTH_LONG).show();
	    		}
	    	}
	    });
	    
	    //Fertig-Button
    	final Button add = (Button) findViewById(R.id.button_add);
	    add.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	    	    EditText target_value = (EditText) findViewById(R.id.edittext_constraint_target_value);
	    	    EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	    	    if(target_value.getText().toString().equals("")){
	    			Toast.makeText(ConstraintEdit.this,"Eingabe unvollständig! Bitte Zielwert eingeben!",Toast.LENGTH_LONG).show();
        			target_value.setBackgroundResource(R.drawable.textfield_pressed_red);//Hintergrund rot
	    			return;
	        	}
	        	else if(constraint.getValues().isEmpty()){
	    			Toast.makeText(ConstraintEdit.this,"Eingabe unvollständig! Bitte mind. ein xi hinzufügen!",Toast.LENGTH_LONG).show();
        			target_element.setBackgroundResource(R.drawable.textfield_pressed_red);//Hintergrund rot
	    			return;
	        	}
	        	else{
	        		constraint.setTargetValue(Double.valueOf(target_value.getText().toString()));
	        		setResult(CONSTRAINT_CREATE_RESULT);
	        		finish();
	        	}
	        }
	    });
	    
		//Zurück-Button
    	final Button back = (Button) findViewById(R.id.button_cancel);
	    back.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	setResult(RESULT_CANCELED);
	        	finish();
	        }
	    });
	}	
}

	
	
	

