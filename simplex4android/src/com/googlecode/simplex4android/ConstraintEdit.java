package com.googlecode.simplex4android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
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
	    setContentView(R.layout.constraint_edit);
	    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

	    //Behandlung der verschiedenen Intents
	    if(this.getIntent().getBooleanExtra("create", false) ==  true){
	    	constraint = new Constraint();
	    }
	    else if(this.getIntent().getBooleanExtra("edit", false) == true){
	    	constraint = (Constraint) this.getIntent().getSerializableExtra("constraint"); //Constraint laden
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
	    		    EditText text = (EditText) findViewById(R.id.edittext_target_element);
	    		    findViewById(R.id.btn_add_target_element).setEnabled(true);
	    		    findViewById(R.id.keyboard).setVisibility(View.VISIBLE);
		    		addto = (EditText) findViewById(R.id.edittext_target_element);
	        		Selection.setSelection(text.getText(), text.length());
	    		}
	    		else{
		    		findViewById(R.id.keyboard).setVisibility(View.INVISIBLE);
	    		}
	    	}
		});

	    imm.hideSoftInputFromWindow(target_element.getWindowToken(), 0);
	    //imm.hideSoftInputFromWindow(target_element.getWindowToken(), 1);
	    
	    //Textfeld Constraint-Target-Value
	    EditText constraint_target_value = (EditText) findViewById(R.id.edittext_constraint_target_value);
	    constraint_target_value.setOnFocusChangeListener(new OnFocusChangeListener(){
	    	public void onFocusChange(View v, boolean b){
	    		if(b==true){
	    		    EditText text = (EditText) findViewById(R.id.edittext_target_element);
	    		    findViewById(R.id.btn_add_target_element).setEnabled(false);
	    		    findViewById(R.id.keyboard).setVisibility(View.VISIBLE);
		    		addto = (EditText) findViewById(R.id.edittext_constraint_target_value);
	        		Selection.setSelection(text.getText(), text.length());
	        		
	    		}
	    		else{
		    		findViewById(R.id.keyboard).setVisibility(View.INVISIBLE);
	    		}
	    	}
		});
	    
	    //Keyboard-Buttons
	    int[] keyboardButtons = {	R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, 
									R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
									R.id.btn_minus, R.id.btn_divide, R.id.btn_decimal, R.id.btn_backspace};
		
		for(int i=0; i<keyboardButtons.length; i++){
			Button[] btns = new Button[keyboardButtons.length];
			btns[i] = (Button) findViewById(keyboardButtons[i]);
		    btns[i].setOnClickListener(new OnClickListener() {
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
	    final Button add_target_element = (Button) findViewById(R.id.btn_add_target_element);
	    add_target_element.setOnClickListener (new OnClickListener(){
	    	public void onClick(View V){
	        	EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	        	EditText target = (EditText) findViewById(R.id.edittext_target);
	        	if(SimplexLogic.checkInput(target_element.getText().toString())){
	        		double value;
	        		if(target_element.getText().toString().equals("")){
	        			value = 0.0;
	        		}
	        		else{
	        			try{
	        				value = Double.valueOf(target_element.getText().toString());
	        			}
	        			catch(Exception ex){
	        				try{
	        					value = Input.fractionToDbl(target_element.getText().toString());
	        				}
	        				catch(Exception ex2){
	        	    			Toast.makeText(ConstraintEdit.this,"Fehler beim Typecast!",Toast.LENGTH_LONG).show();
	        	    			return;
	        				}
	        			}
	        		}
	        		int i = Integer.valueOf(((EditText) findViewById(R.id.edittext_x)).getText().toString().substring(1)).intValue()-1;
		        	constraint.setValue(i, value);
	    			target.setText(constraint.valuesToString());
	    			if(i+1<maxi){
	    				increment_xi();
	    			}
	        	}
	    		else{
	    			Toast.makeText(ConstraintEdit.this,"Fehlerhafte Eingabe! Bitte korrigieren!",Toast.LENGTH_LONG).show();
	    		}
	    	}
		});
	    
	    
	    //"Xi erhöhen"-Button
	    final Button x_plus = (Button) findViewById(R.id.btn_x_plus);
	    x_plus.setOnClickListener (new OnClickListener(){
	    	public void onClick(View V){
	    		EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	    		increment_xi();
	    		try{
	    			target_element.requestFocus();
	    		}
	    		catch(Exception ex){
	    			Toast.makeText(ConstraintEdit.this,ex.getMessage(),Toast.LENGTH_LONG).show();
	    		}
	    	}
	    });

	    //"Xi verringern"-Button
	    final Button x_minus = (Button) findViewById(R.id.btn_x_minus);
	    x_minus.setOnClickListener (new OnClickListener(){
	    	public void onClick(View V){
	    		EditText edittext_x = (EditText) findViewById(R.id.edittext_x); 
	    		EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	    		int edittext_x_value = Integer.valueOf(edittext_x.getText().toString().substring(1)).intValue();
	    		if(edittext_x_value>1){
	    			edittext_x_value--;
//	    			if(edittext_x_value==1){
//	    				V.setEnabled(false);
//	    			}
	    			edittext_x.setText("x" + edittext_x_value);
		    		try{
		    			String target_value =String.valueOf(constraint.getValue(edittext_x_value-1));
		    			if(target_value.equals("0.0")){
		    				target_element.setText("");
		    				target_element.setHint(0);
		    			}
		    			else{
		    				target_element.setText(target_value);
			        		Selection.setSelection(target_element.getText(), target_element.length());
		    			}
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
    	final Button add = (Button) findViewById(R.id.btn_add);
	    add.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	    	    EditText target_value = (EditText) findViewById(R.id.edittext_constraint_target_value);
	    	    EditText target_element = (EditText) findViewById(R.id.edittext_target_element);
	    	    if(target_value.getText().toString().equals("")){
	    			Toast.makeText(ConstraintEdit.this,"Eingabe unvollständig! Bitte Zielwert eingeben!",Toast.LENGTH_LONG).show();
        			target_value.setBackgroundResource(R.drawable.textfield_pressed_red);//Hintergrund rot
        			target_value.requestFocus();
	    			return;
	        	}
	        	else if(constraint.getValues().isEmpty()){
	    			Toast.makeText(ConstraintEdit.this,"Eingabe unvollständig! Bitte mind. ein xi hinzufügen!",Toast.LENGTH_LONG).show();
        			target_element.setBackgroundResource(R.drawable.textfield_pressed_red);//Hintergrund rot
        			target_element.requestFocus();
	        	}
	        	else{
	        		constraint.setTargetValue(Double.valueOf(target_value.getText().toString()));
	        		Intent ConstraintEditIntent = new Intent().putExtra("constraint", constraint);
	        		if(ConstraintEdit.this.getIntent().getBooleanExtra("edit", false)){
		        		ConstraintEditIntent.putExtra("id", ConstraintEdit.this.getIntent().getIntExtra("id", -1));
	        			setResult(CONSTRAINT_EDIT_RESULT,ConstraintEditIntent);
	        		}
	        		else{
	        			setResult(CONSTRAINT_CREATE_RESULT, ConstraintEditIntent);
	        		}
	        		finish();
	        	}
	        }
	    });
	    
		//Zurück-Button
    	final Button back = (Button) findViewById(R.id.btn_cancel);
	    back.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	setResult(RESULT_CANCELED);
	        	finish();
	        }
	    });
	    
	    //Constraint laden, falls "edit" == true
	    if(this.getIntent().getBooleanExtra("edit", false) == true){

	    //Textfeld Target
	    EditText target = (EditText) findViewById(R.id.edittext_target);
	    target.setText(constraint.valuesToString());

    	//Textfeld Target-Element
	    try{
	    	String target_element_string = String.valueOf(constraint.getValue(0));
	    	if(target_element_string.equals("0.0")){
	    		target_element.setText("");
	    		target_element.setHint("0");
	    	}
	    	else{
	    		target_element.setText(target_element_string);
	    		Selection.setSelection(target_element.getText(), target_element.length());
	    	}
	    }
	    catch(Exception e){
	    	target_element.setText("");
	    	target_element.setHint("0");
	    }
	    
	    //Textfeld Target-Value
	    constraint_target_value.setText(String.valueOf(constraint.getTargetValue()));
   
	    //Spinner gtltoreq
    	//Index "0" enspricht "<=", "2" entspricht "=" und "1" entspricht ">="
    	//Sign "-1" enspricht "<=", "0" entspricht "=" und "1" entspricht ">="
	    if(constraint.getSign() == -1){
	    	gtltoreq.setSelection(0);
	    }
	    else if(constraint.getSign() == 0){
	    	gtltoreq.setSelection(2);
	    }
	    else{
	    	gtltoreq.setSelection(1);
	    }
	    }
    }	
	
	public void increment_xi(){
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
			String target_value =String.valueOf(constraint.getValue(edittext_x_value-1));
			if(target_value.equals("0.0")){
				target_element.setText("");
				target_element.setHint("0");
			}
			else{
				target_element.setText(target_value);
        		Selection.setSelection(target_element.getText(), target_element.length());
			}
		}
		catch(IndexOutOfBoundsException e){
    		target_element.setText("");
			target_element.setHint("0");
		}
		catch(Exception e){
			
		}

	}
}

	
	
	

