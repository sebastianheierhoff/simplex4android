
//TODO: Buttons deaktivieren, solange sie nicht benutzt werden dürfen
//TODO: Prüfungen einbauen, ob gespeichert/gestartet werden kann (Eingaben korrekt, etc.)

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

	private static ArrayAdapter<String> adapter_list_constraint;
	private static ArrayAdapter<String> adapter_list_target;
	private static ArrayList<Input> inputs;
	static SimplexHistory[] simplexhistoryarray;

	final String[] settings = {"Primal", "Dual"};

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

		//"Einstellungen ändern" - Button
		final Button btn_settings = (Button) findViewById(R.id.btn_settings);
		btn_settings.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Dialog, um Einstellungen vorzunehmen
				AlertDialog.Builder builder = new AlertDialog.Builder(InputShow.this);
				builder.setTitle("Simplex-Methode");
				builder.setItems(settings, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						try {
							((Target)inputs.get(0)).setUserSettings(item);
						}catch(IOException e) {
							Toast.makeText(getApplicationContext(), "Fehler beim Setzen der Einstellungen!", Toast.LENGTH_SHORT).show();
						}
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
				//TODO: Speichern Methode einbinden
				//InputsDb.addProblem(InputShow.inputs);
				//Toast "Problem gespeichert"
				//Falls schon gespeichert aber geändert/geladenes Problem - Frage: "Als neues Problem speichern, oder überschreiben?"
				//Buttons: "Neues Problem" "Überschreiben"

			}
		});

		if(inputs.get(0) == null) btn_save.setEnabled(false); //Button deaktiviert, solange kein gültiges Problem angelegt wurde, //TODO: Methode zum Überprüfen einbauen

		//Start-Button
		final Button btn_start = (Button) findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				if(((Target)inputs.get(0)).getUserSettings()==0){ //je nach Einstellung ein Problem erzeugen
					try{
						SimplexProblemPrimal problem = new SimplexProblemPrimal(inputs);
						simplexhistoryarray = SimplexLogic.twoPhaseSimplex(problem); //2-Phasen Simplex auf dieses Problem anwenden, man erhält ein SimplexHistory[2]
					}
					catch(Exception ex){
						Toast.makeText(InputShow.this,"Unbekannter Fehler",Toast.LENGTH_LONG).show();
					}
				}
				else{
					try{
						SimplexProblemDual problem = new SimplexProblemDual(inputs);
						simplexhistoryarray = SimplexLogic.twoPhaseSimplex(problem); //2-Phasen Simplex auf dieses Problem anwenden, man erhält ein SimplexHistory[2]
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
		switch (resultCode) {
		case RESULT_CANCELED:
			if(requestCode == TARGET_EDIT_REQUEST){
				//TODO: Code einfügen
			}
			break;
		case TARGET_EDIT_RESULT:
			//Constraints beschneiden, falls Target kürzer geworden ist.
			if(inputs.size()>=2){ // nur Target in inputs enthalten
				for(int i=1;i<inputs.size();i++){ // durch alle Inputs
					if(inputs.get(0).getValues().size()<inputs.get(i).getValues().size()){ // Constraint enthält ungültige xi
						int del = inputs.get(i).getValues().size() - inputs.get(0).getValues().size(); // Anzahl zu löschender xi
						for(int j=0;j<del;j++){ // del-mal letztes xi löschen
							inputs.get(i).getValues().remove(inputs.get(i).getValues().size()-1);
						}             
					}
				}
			}
			Toast.makeText(InputShow.this,"Ungültige xi aus den Nebenbedingungen entfernt.",Toast.LENGTH_LONG).show();
			fillConstraintData();
			break;
		case TARGET_CREATE_RESULT:
			try{
				Target target = TargetEdit.target; //TODO: ersetzen durch getSerializableExtra()
				inputs.set(0, target);
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
		hideOrShowEmptyTexts();
	}

	public void ConstraintDeleteClickHandler(View v){
		ListView lv = (ListView) findViewById(R.id.list_constraint);
		RelativeLayout rl_row = (RelativeLayout)v.getParent();
		int position = lv.indexOfChild(rl_row) +1;
		inputs.remove(position);
		fillConstraintData();
	}

	public void ConstraintEditClickHandler(View v){
		ListView listInputs = (ListView) findViewById(R.id.list_constraint);
		EditClickHandler(v, listInputs);		
	}

	public void TargetDeleteClickHandler(View v){
		inputs.set(0, null);
		adapter_list_target.remove(adapter_list_target.getItem(0));
		fillTargetData();
	}

	public void TargetEditClickHandler(View v){
		ListView listInputs = (ListView) findViewById(R.id.list_target);
		EditClickHandler(v, listInputs);
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
		if(inputs.get(0) != null){
			String[] target_string = new String[1];
			target_string[0] = inputs.get(0).toString();

			ListView lv_target = (ListView) findViewById(R.id.list_target);
			adapter_list_target = new ArrayAdapter<String>(this, R.layout.listview_target, R.id.tv_row, target_string);
			adapter_list_target.notifyDataSetChanged();
			//adapter_list_target.notifyDataSetInvalidated();
			lv_target.setAdapter(adapter_list_target);
			lv_target.refreshDrawableState();
		}
	}

	private void fillConstraintData() {
		if(inputs.size()>1){
			List<Input> constraints = inputs.subList(1, inputs.size());
			String[] constraints_string = new String[constraints.size()];
			Iterator<Input> iter = constraints.iterator(); 
			int i = 0;
			while(iter.hasNext() ) { 
				constraints_string[i] = iter.next().toString();
				i++;
			}
			ListView listInputs = (ListView) findViewById(R.id.list_constraint);
			adapter_list_constraint = new ArrayAdapter<String>(this, R.layout.listview_constraint, R.id.tv_row, constraints_string);
			adapter_list_constraint.notifyDataSetChanged();
			listInputs.setAdapter(adapter_list_constraint);
			listInputs.refreshDrawableState();
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
