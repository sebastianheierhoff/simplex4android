
//TODO: Buttons deaktivieren, solange sie nicht benutzt werden d�rfen
//TODO: Pr�fungen einbauen, ob gespeichert/gestartet werden kann (Eingaben korrekt, etc.)

package com.googlecode.simplex4android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

public class InputShow extends Activity{
	
	private static ArrayList<Input> inputs;
	static SimplexHistory[] simplexhistoryarray;
	
	private static ArrayAdapter<String> adapter_list_constraint;
	private static ArrayAdapter<String> adapter_list_target;

	private static final String[] settings = {"Primal", "Dual"};
	
	//TODO: Anpassen!
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
	
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    	setContentView(R.layout.input_show);
	    
	    //Behandlung der verschiedenen Intents
    	if(this.getIntent().getBooleanExtra("create", false)){
	    	inputs = new ArrayList<Input>();
	    	inputs.add(null);
	    	Intent TargetCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
        	TargetCreateIntent.putExtra("create", true);
        	startActivityForResult(TargetCreateIntent, TARGET_CREATE_REQUEST);
	    }
	    else if(this.getIntent().getBooleanExtra("load", false)){

	    }
	    else if(this.getIntent().getBooleanExtra("currentProblem", false)){
	    	inputs = (ArrayList<Input>) this.getIntent().getSerializableExtra("inputs");
	    	fillData();
	    }
	    else{
	    	
	    }
	
	    //Anzeigen/Ausbleden der Meldungen "Keine Nebenbedinung/Zielfunktion eingegeben."
    	hideOrShowEmptyTexts();

        //ArrayAdapter der ListViews
    	adapter_list_target = new ArrayAdapter<String>(this, R.layout.listview_target, R.id.tv_row);
        adapter_list_constraint = new ArrayAdapter<String>(this, R.layout.listview_constraint, R.id.tv_row);
        adapter_list_target.setNotifyOnChange(true);
        adapter_list_constraint.setNotifyOnChange(true);
        ListView lv_target = (ListView) findViewById(R.id.list_target);
        lv_target.setAdapter(adapter_list_target);
        ListView lv_constraint = (ListView) findViewById(R.id.list_constraint);
        lv_constraint.setAdapter(adapter_list_constraint);
        
	    //Zur�ck-Button
	    final Button back = (Button) findViewById(R.id.btn_cancel);
	    back.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
		    	Intent ShowMainIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.simplex4android");
	        	ShowMainIntent.putExtra("inputs", inputs); 
	            ShowMainIntent.putExtra("currentProblem", true);
	        	startActivity(ShowMainIntent);
	     	}
	    });
	    
	    //Nebenbedingung hinzuf�gen - Button
    	final Button constraint_new = (Button) findViewById(R.id.btn_new_constraint);
	    constraint_new.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	Intent ConstraintCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
	        	ConstraintCreateIntent.putExtra("maxi", inputs.get(0).getValues().size());
	        	ConstraintCreateIntent.putExtra("create", true);
	        	startActivityForResult(ConstraintCreateIntent, CONSTRAINT_CREATE_REQUEST);
	        }
	    });

	    //"Einstellungen �ndern" - Button
    	final Button btn_settings = (Button) findViewById(R.id.btn_settings);
	    btn_settings.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	    	    //Dialog, um Einstellungen vorzunehmen
	        	AlertDialog.Builder builder = new AlertDialog.Builder(InputShow.this);
	        	builder.setTitle("Simplex-Methode");
	        	builder.setItems(settings, new DialogInterface.OnClickListener() {
	        		public void onClick(DialogInterface dialog, int item) {
	        			Toast.makeText(getApplicationContext(), settings[item], Toast.LENGTH_SHORT).show();    }});
	        	AlertDialog alert = builder.create();
	        	alert.show();
	        }
	    });
	    
	    if(inputs.get(0) == null) btn_settings.setEnabled(false); //Button deaktiviert, solange keine Zielfunktion angelegt wurde, da die Settings in der Zielfunktion gespeichert werden.

	    //Speichern-Button
    	final Button btn_save = (Button) findViewById(R.id.btn_save);
	    btn_save.setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
		        
		        //if(){//Falls das Problem geladen wurde
		        	AlertDialog.Builder builder = new AlertDialog.Builder(InputShow.this);
		        	builder.setMessage("Problem �berschreiben?")
		        		   .setCancelable(false)
		        		   .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
		        			   public void onClick(DialogInterface dialog, int id) {
		        				   //Problem speichern;
		        			   }       
		        		   })
		        		   .setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
		        			   public void onClick(DialogInterface dialog, int id) {
		        			   }
		        		   })
		        		   .setNeutralButton("Als neues Problem", new DialogInterface.OnClickListener() {
		        			   public void onClick(DialogInterface dialog, int id) {
		        				   dialog.cancel();
		        			   }
		        		   });
		        	AlertDialog alert = builder.create();
		        	alert.show();
		        //}
		        //else{
		        	//TODO: Speichern Methode einbinden
		        	//InputsDb.addProblem(InputShow.inputs);
		        	//Toast "Problem gespeichert"
		        	//Falls schon gespeichert aber ge�ndert/geladenes Problem - Frage: "Als neues Problem speichern, oder �berschreiben?"
	        	//Buttons: "Neues Problem" "�berschreiben"
	        	
	        }
	    });

		//Start-Button
		final Button btn_start = (Button) findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				if(((Target)inputs.get(0)).getUserSettings()==0){ //je nach Einstellung ein Problem erzeugen
					try{
						SimplexProblemPrimal problem = new SimplexProblemPrimal(inputs);
						simplexhistoryarray = SimplexLogic.twoPhaseSimplex(problem); //2-Phasen Simplex auf dieses Problem anwenden, man erh�lt ein SimplexHistory[2]
					}
					catch(Exception ex){
						Toast.makeText(InputShow.this,"Unbekannter Fehler",Toast.LENGTH_LONG).show();
					}
				}
				else{
					try{
						SimplexProblemDual problem = new SimplexProblemDual(inputs);
						simplexhistoryarray = SimplexLogic.twoPhaseSimplex(problem); //2-Phasen Simplex auf dieses Problem anwenden, man erh�lt ein SimplexHistory[2]
					}
					catch(Exception ex){
						Toast.makeText(InputShow.this,"Unbekannter Fehler",Toast.LENGTH_LONG).show();
					}
				}
				Intent SHShowIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.SimplexHistoryShow");
				//SHShowIntent.putExtra("simplexhistoryarray", simplexhistoryarray); //Weitergeben des Arrays an SimplexHistoryShow zur Ausgabe //TODO: Serializable implementieren
				startActivity(SHShowIntent);
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		Target target;
		Constraint constraint;
		switch (resultCode) {
        	case RESULT_CANCELED:
        		if(requestCode == TARGET_EDIT_REQUEST){
        			//TODO: Code einf�gen
        		}
        		break;
        	case TARGET_EDIT_RESULT:
        		//Constraints beschneiden, falls Target k�rzer geworden ist.
        		if(inputs.size()>=2){ // nur Target in inputs enthalten
        	      for(int i=1;i<inputs.size();i++){ // durch alle Inputs
        	    	  if(inputs.get(0).getValues().size()<inputs.get(i).getValues().size()){ // Constraint enth�lt ung�ltige xi
        	    		  int del = inputs.get(i).getValues().size() - inputs.get(0).getValues().size(); // Anzahl zu l�schender xi
        	    		  for(int j=0;j<del;j++){ // del-mal letztes xi l�schen
        	    			  inputs.get(i).getValues().remove(inputs.get(i).getValues().size()-1);
        	    		  }             
        	    	  }
        	      }
        		}
        		Toast.makeText(InputShow.this,"Ung�ltige xi aus den Nebenbedingungen entfernt.",Toast.LENGTH_LONG).show();
        		target = TargetEdit.target;
        		adapter_list_target.insert(target.toString(),0);
        		inputs.set(0, target);
        		break;
        	case TARGET_CREATE_RESULT:
            	try{
            		target = TargetEdit.target; //TODO: ersetzen durch getSerializableExtra()
            		adapter_list_target.insert(target.toString(),0);
            		inputs.set(0, target);
                    ListView lv_target = (ListView) findViewById(R.id.list_target);
                    lv_target.refreshDrawableState();
        		    Toast.makeText(InputShow.this,"Zielfunktion angelegt",Toast.LENGTH_LONG).show();
            	}
            	catch(Exception ex){
	    			Toast.makeText(InputShow.this,"Unbekannter Fehler",Toast.LENGTH_LONG).show();
            	}
            	break;
        	case CONSTRAINT_EDIT_RESULT:
        		constraint = (Constraint) this.getIntent().getSerializableExtra("constraint");
        		int position = this.getIntent().getIntExtra("id", -1);
        		inputs.set(position+1, constraint);
        		adapter_list_constraint.insert(constraint.toString(), position);
    		    Toast.makeText(InputShow.this,"Nebenbedingung editiert",Toast.LENGTH_LONG).show();
        		break;
        	case CONSTRAINT_CREATE_RESULT:
            	try{
            		constraint = (Constraint) this.getIntent().getSerializableExtra("constraint");
            		inputs.add(constraint);
            		adapter_list_constraint.add(constraint.toString());
        		    Toast.makeText(InputShow.this,"Nebenbedingung angelegt",Toast.LENGTH_LONG).show();
            	}
            	catch(ClassCastException e){
	    			Toast.makeText(InputShow.this,"Fehler: " + e.getMessage(),Toast.LENGTH_LONG).show();
            	}
            	catch(Exception ex){
	    			Toast.makeText(InputShow.this,ex.getMessage(),Toast.LENGTH_LONG).show();
            	}
            	break;
			default:
                break;
        }
		hideOrShowEmptyTexts();
    }

	public void ConstraintDeleteClickHandler(View v){
		ListView lv_constraint = (ListView) findViewById(R.id.list_constraint);
		RelativeLayout rl_row = (RelativeLayout)v.getParent();
        int position = lv_constraint.indexOfChild(rl_row);
        adapter_list_constraint.remove(adapter_list_constraint.getItem(position));
        inputs.remove(position +1);
        hideOrShowEmptyTexts();
	}
	
	public void ConstraintEditClickHandler(View v){
		ListView lv_constraint = (ListView) findViewById(R.id.list_constraint);
		RelativeLayout rl_row = (RelativeLayout)v.getParent();
        int position = lv_constraint.indexOfChild(rl_row);
        Constraint constraint = (Constraint) inputs.get(position+1);
        Intent ConstraintEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
        ConstraintEditIntent.putExtra("constraint", constraint);
        ConstraintEditIntent.putExtra("edit", true);
        ConstraintEditIntent.putExtra("id", position);
        ConstraintEditIntent.putExtra("maxi", inputs.get(0).getValues().size());
    	startActivityForResult(ConstraintEditIntent, CONSTRAINT_EDIT_REQUEST);
	}
	
	public void TargetDeleteClickHandler(View v){
        inputs.set(0, null);
        adapter_list_target.clear();
        hideOrShowEmptyTexts();
	    Toast.makeText(InputShow.this,"Zielfunktion gel�scht. Bitte neue Zielfunktion eingeben.",Toast.LENGTH_LONG).show();
        Intent TargetCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
    	TargetCreateIntent.putExtra("create", true);
    	startActivityForResult(TargetCreateIntent, TARGET_CREATE_REQUEST);
    }

	public void TargetEditClickHandler(View v){
        Target target = (Target) inputs.get(0);
        Intent ConstraintEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
        ConstraintEditIntent.putExtra("target", target);
        ConstraintEditIntent.putExtra("edit", true);
    	startActivityForResult(ConstraintEditIntent, CONSTRAINT_EDIT_REQUEST);
	}

	private void fillData() {
        if(inputs.get(0) != null){
			String[] target_string = new String[1];
		    target_string[0] = inputs.get(0).toString();
	        adapter_list_target = new ArrayAdapter<String>(this, R.layout.listview_target, R.id.tv_row, target_string);
	    }
        if(inputs.size()>1){
			List<Input> constraints = inputs.subList(1, inputs.size());
	        String[] constraints_string = new String[constraints.size()];
	        Iterator<Input> iter = constraints.iterator(); 
	        int i = 0;
	        while(iter.hasNext() ) { 
	            constraints_string[i] = iter.next().toString();
	            i++;
	        }
	        adapter_list_constraint = new ArrayAdapter<String>(this, R.layout.listview_constraint, R.id.tv_row, constraints_string);
        }
	}

	private void hideOrShowEmptyTexts(){
		TextView txt_target_empty = (TextView) findViewById(R.id.text_target_empty);
		ViewGroup.LayoutParams params_target = txt_target_empty.getLayoutParams();
		if(inputs.get(0) != null){
			params_target.height = 0;
		}
		else{
			params_target.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		}
		txt_target_empty.requestLayout();

		TextView txt_constraint_empty = (TextView) findViewById(R.id.text_constraint_empty);
		ViewGroup.LayoutParams params_constraint = txt_constraint_empty.getLayoutParams();
		if(inputs.size()>1){
			params_constraint.height = 0;
		}
		else{
			params_constraint.height = ViewGroup.LayoutParams.WRAP_CONTENT;
			
		}
		txt_constraint_empty.requestLayout();
	}
}
