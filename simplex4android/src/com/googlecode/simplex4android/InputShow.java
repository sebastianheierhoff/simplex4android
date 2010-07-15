//TODO: Buttons deaktivieren, solange sie nicht benutzt werden dürfen
//TODO: Prüfungen einbauen, ob gespeichert/gestartet werden kann (Eingaben korrekt, etc.)

package com.googlecode.simplex4android;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class InputShow extends Activity{
	
	private static ArrayList<Input> inputs;
	static SimplexHistory[] simplexhistoryarray;

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
	    
	    if(this.getIntent().getBooleanExtra("create", false)){
	    	inputs = new ArrayList<Input>();
	    	Intent ConstraintEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.TargetEdit");
        	ConstraintEditIntent.putExtra("create", true);
        	startActivityForResult(ConstraintEditIntent, TARGET_EDIT_REQUEST);
	    }
	    else if(this.getIntent().getBooleanExtra("load", false)){

	    }
	    else if(this.getIntent().getBooleanExtra("currentProblem", false)){
	    	inputs = (ArrayList<Input>) this.getIntent().getSerializableExtra("inputs");
	    	fillData();
	    }
	    else{
	    	
	    }
	
	    //Zurück-Button
	    final Button back = (Button) findViewById(R.id.btn_cancel);
	    back.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
		    	Intent ShowMainIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.simplex4android");
	        	ShowMainIntent.putExtra("inputs", inputs); 
	            ShowMainIntent.putExtra("currentProblem", true);
	        	startActivity(ShowMainIntent);
	     	}
	    });
	    
	    //Nebenbedingung hinzufügen - Button
    	final Button constraint_new = (Button) findViewById(R.id.btn_new_constraint);
	    constraint_new.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	Intent ConstraintCreateIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
	        	ConstraintCreateIntent.putExtra("maxi", inputs.get(0).getValues().size());
	        	ConstraintCreateIntent.putExtra("create", true);
	        	startActivityForResult(ConstraintCreateIntent, CONSTRAINT_CREATE_REQUEST);
	        }
	    });
	    
	    //Speichern-Button
    	final Button btn_save = (Button) findViewById(R.id.btn_save);
	    btn_save.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	//TODO: Speichern Methode einbinden
	        	//InputsDb.addProblem(InputShow.inputs);
	        	//Toast "Problem gespeichert"
	        	//Falls schon gespeichert aber geändert/geladenes Problem - Frage: "Als neues Problem speichern, oder überschreiben?"
	        	//Buttons: "Neues Problem" "Überschreiben"
	        	
	        }
	    });

	    //"Einstellungen ändern" - Button
    	final Button btn_settings = (Button) findViewById(R.id.btn_settings);
	    btn_settings.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	
	        }
	    });
	    
	    //Start-Button
    	final Button btn_start = (Button) findViewById(R.id.btn_start);
	    btn_start.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	//TODO: Code einfügen.
	        	SimplexProblemPrimal problem = null;
	        	//je nach Einstellung ein Problem erzeugen,
//	        	if(){
	        	try{
	        		//problem = new SimplexProblemPrimal(inputs);
	        		double[] target = {1,2,7,5,0,0,0};
	            	double[][] tableau = {{-1,2,1,0,1,0,7},{0,1,0,1,0,-1,3},{1,0,2,2,0,0,8},{0,0,0,0,0,0,0}};
	            	problem = new SimplexProblemPrimal(tableau, target);
	        	}
	        	catch(Exception ex){
	    			Toast.makeText(InputShow.this,"Unbekannter Fehler beim Anlegen des Simplex-Tableau",Toast.LENGTH_LONG).show();
	        	}
//	        	}
//	        	else{
//	        		
//	        	}
	        	//2-Phasen Simplex auf dieses Problem anwenden, man erhält ein SimplexHistory[2]
	        	try{
	        		simplexhistoryarray = SimplexLogic.twoPhaseSimplex(problem);
	        	}
	        	catch(Exception ex){
	    			Toast.makeText(InputShow.this,"Unbekannter Fehler beim Berechnen der Tableaus",Toast.LENGTH_LONG).show();
	        	}
	        	//Weitergeben des Arrays an SimplexHistoryShow zur Ausgabe
	        	Intent SHShowIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.SimplexHistoryShow");
	        	startActivity(SHShowIntent);
	        }
	    });
	}

	public void onResume(Bundle savedInstanceState) {
	
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (resultCode) {
        	case RESULT_CANCELED:
        		if(requestCode == TARGET_EDIT_REQUEST){
        			finish();
        		}
        		break;
        	case TARGET_EDIT_RESULT:
        		//TODO: Wenn der Index des größten xi kleiner geworden ist
        		
        		//alle Constraints überprüfen, ob xi mit größerem Index enthalten sind
        		
        		//diese Constraints rot markieren, Fehlermeldung ausgeben
        		
        		//Rechnen/Anlegen des SimplexProblems erst erlauben, wenn alle Constraints geändert wurden
        		break;
        	case TARGET_CREATE_RESULT:
            	try{
            		Target target = TargetEdit.target;
        	        inputs.add(target);
                	
        		    if(inputs.size()>0){
        		    	TextView txt_target_empty = (TextView) findViewById(R.id.text_target_empty);
        		    	ViewGroup.LayoutParams params = txt_target_empty.getLayoutParams();
        		    		params.height = 0;
        		    	txt_target_empty.requestLayout();
        		    }
        		    if(inputs.size()>1){
        		    	TextView txt_constraint_empty = (TextView) findViewById(R.id.text_constraint_empty);
        		    	ViewGroup.LayoutParams params = txt_constraint_empty.getLayoutParams();
        		    		params.height = 0;
        		    	txt_constraint_empty.requestLayout();
        		    	        		    }
        	        fillTargetData();
        		    Toast.makeText(InputShow.this,"Zielfunktion angelegt",Toast.LENGTH_LONG).show();
            	}
            	catch(Exception ex){
	    			Toast.makeText(InputShow.this,"Unbekannter Fehler",Toast.LENGTH_LONG).show();
            	}
            	break;
        	case CONSTRAINT_EDIT_RESULT:
        		
        		break;
        	case CONSTRAINT_CREATE_RESULT:
            	try{
            		Constraint constraint = ConstraintEdit.constraint;
        	        inputs.add(constraint);
                	
        		    if(inputs.size()>0){
        		    	findViewById(R.id.text_target_empty).setVisibility(View.INVISIBLE);
        		    }
        		    if(inputs.size()>1){
        		    	findViewById(R.id.text_constraint_empty).setVisibility(View.INVISIBLE);
        		    }
        	        fillConstraintData();
        		    Toast.makeText(InputShow.this,"Nebenbedingung angelegt",Toast.LENGTH_LONG).show();
            	}
            	catch(Exception ex){
	    			Toast.makeText(InputShow.this,"Unbekannter Fehler",Toast.LENGTH_LONG).show();
            	}
            	break;
			default:
                break;
        }
    }

	public void ConstraintDeleteClickHandler(View v){
		ListView listInputs = (ListView) findViewById(R.id.list_constraint);
		DeleteClickHandler(v, listInputs);
	}
	
	public void ConstraintEditClickHandler(View v){
		ListView listInputs = (ListView) findViewById(R.id.list_constraint);
		EditClickHandler(v, listInputs);		
	}
	
	public void TargetDeleteClickHandler(View v){
		ListView listInputs = (ListView) findViewById(R.id.list_target);
		DeleteClickHandler(v, listInputs);
	}

	public void TargetEditClickHandler(View v){
		ListView listInputs = (ListView) findViewById(R.id.list_target);
		DeleteClickHandler(v, listInputs);
	}

	
	@SuppressWarnings("unchecked")
	public void DeleteClickHandler(View v, ListView lv)
	{
		RelativeLayout rl_row = (RelativeLayout)v.getParent();
        int position = lv.indexOfChild(rl_row);
		ArrayAdapter<String> adapter = ((ArrayAdapter<String>) lv.getAdapter());
		adapter.remove(adapter.getItem(position));
	}

	public void EditClickHandler(View v, ListView lv){
		RelativeLayout vwParentRow = (RelativeLayout)v.getParent();
		Button btnChild = (Button)vwParentRow.getChildAt(2);
		vwParentRow.refreshDrawableState();
	}
	
	private void fillData(){
		fillTargetData();
		fillConstraintData();
	}
	
	private void fillTargetData() {
	    String[] target_string = new String[1];
	    target_string[0] = inputs.get(0).toString();
	    
        ListView listInputs = (ListView) findViewById(R.id.list_target);
        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview_target, R.id.tv_row, target_string);
        listInputs.setAdapter(adapter);
	}

	private void fillConstraintData() {
        List<Input> constraints = inputs.subList(1, inputs.size());
        String[] constraints_string = new String[constraints.size()];
        Iterator<Input> iter = constraints.iterator(); 
        int i = 0;
        while(iter.hasNext() ) { 
            constraints_string[i] = iter.next().toString();
            i++;
        }
        ListView listInputs = (ListView) findViewById(R.id.list_constraint);
        listInputs.setVisibility(View.VISIBLE);
        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview_constraint, R.id.tv_row, constraints_string);
        listInputs.setAdapter(adapter);
	}
}
