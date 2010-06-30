package com.googlecode.simplex4android;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;

/**
 * Klasse Simplex - legt eine History an, nimmt die Eingabe entgegen, verarbeitet diese, lässt das Problem je nach Settings 
 * von SimplexLogic lösen und sorgt für die Ausgabe.
 * @author Simplex4Android
 */

public class Simplex extends Activity{

    private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
    //private NotesDbAdapter mDbHelper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mDbHelper = new NotesDbAdapter(this);
        //mDbHelper.open();

        setContentView(R.layout.view);

        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);

        Button confirmButton = (Button) findViewById(R.id.confirm);

        mRowId = (savedInstanceState == null) ? null :
            (Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(NotesDbAdapter.KEY_ROWID)
									: null;
		}

		populateFields();

        confirmButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }

        });
    }

    private void populateFields() {
        if (mRowId != null) {
            Cursor note = mDbHelper.fetchNote(mRowId);
            startManagingCursor(note);
            mTitleText.setText(note.getString(
                    note.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)));
            mBodyText.setText(note.getString(
                    note.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFields();
    }

    private void saveState() {
        String title = mTitleText.getText().toString();
        String body = mBodyText.getText().toString();

        if (mRowId == null) {
            long id = mDbHelper.createNote(title, body);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateNote(mRowId, title, body);
        }
    }
	
	
	
	
	public static void main(String[] args){
		boolean debug = true;
		SimplexHistory sh = new SimplexHistory();
		
		//Settings lesen, SimplexSettings erzeugen
		
		//Eingabe lesen
		//Beispiel-Tableau
		double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}}; 
		//Beispiel-Zielfunktion - Zielfunktion muss um eine 0 verlängert werden, um Zielwert berechnen zu können!!!
		int[] target = {1,2,7,5,0,0,0}; 
		
		//SimplexProblem erzeugen (aus Tableau, Target, SimplexSettings)
		SimplexProblem firstProblem = new SimplexProblem(tableau, target);

		//SimplexProblem in History einfügen
		sh.addElement(firstProblem);
		
		if(debug == true){System.out.println("Tableau: \n" + firstProblem.tableauToString());} 
		if(debug == true){System.out.println("Zielfunktion: " + firstProblem.targetToString());}
		if(debug == true){System.out.println("HTML: "+ firstProblem.tableauToHtml());}

		//SimplexLogic auf SimplexProblem(e) ausführen, bis optimale Lösung gefunden, dabei Ausgabe aller Zwischenschritte
		do{
			SimplexProblem current = sh.getLastElement();
			current = SimplexLogic.simplex(current);

			//Debug-Ausgabe
			if(debug == true){System.out.println("Tableau: \n" + current.tableauToString());}
			if(debug == true){System.out.println("Basisspalten: " + Arrays.toString(current.getPivots()));}
			if(debug == true){System.out.println("Optimal: "+current.getOptimal());}
			if(debug == true){System.out.println("HTML: "+current.tableauToHtml());}
			
			sh.addElement(current);
		}
		while(sh.getLastElement().getOptimal()!=true);
	}	
}
