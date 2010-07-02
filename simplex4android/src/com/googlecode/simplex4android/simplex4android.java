package com.googlecode.simplex4android;

import com.googlecode.simplex4android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class simplex4android extends Activity {
	
/*	
 * Startet entweder 
		Activity um neues Problem anzulegen, oder 
		ListView um Probleme zu laden
			l�dt View zum �ndern/anlegen von Problemen mit gespeicherten Werten
			
			laden Ausgabe, Zur�ckbutton, um wieder auf den HomeScreen zur�ck zu gelangen, Speichern Button, um zur ListView zur�ck zu kehren
*/	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
    	final Button button_new = (Button) findViewById(R.id.button_new);
	    button_new.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            Intent ConstraintEditIntent = new Intent();
	            ConstraintEditIntent.setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
	        	startActivity(ConstraintEditIntent);
	        }
	    });
	    	    
    	final Button button_load = (Button) findViewById(R.id.button_load);
	    button_load.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            Intent SimplexHistoryLoadIntent = new Intent();
	            SimplexHistoryLoadIntent.setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.SimplexHistoryLoad");
	        	startActivity(SimplexHistoryLoadIntent);
	        }
	    });
	    
	    
//    	final Button button_tutorial = (Button) findViewById(R.id.button_tutorial);
//	    button_tutorial.setOnClickListener(new OnClickListener() {
//	        public void onClick(View v) {
//	            Intent TutorialIntent = new Intent();
//	            TutorialIntent.setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.Tutorial");
//	        	startActivity(TutorialIntent);
//	        }
//	    });
	    
	    //registerForContextMenu(getListView());
	}
	
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        menu.add(0, INSERT_ID, 0, "Hinzuf�gen");
//        return true;
//    }

//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//        switch(item.getItemId()) {
//            case INSERT_ID:
//                createNote();
//                return true;
//        }
//
//        return super.onMenuItemSelected(featureId, item);
//    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//            ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.add(0, DELETE_ID, 0, "L�schen");
//    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case DELETE_ID:
//                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
//                //mDbHelper.deleteNote(info.id);
//                //fillData();
//                return true;
//        }
//        return super.onContextItemSelected(item);
//    }

//    private void createSimplexHistory() {
//        Intent i = new Intent(this, SimplexProblemEdit.class);
//        startActivityForResult(i, ACTIVITY_CREATE);
//    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        Intent i = new Intent(this, NoteEdit.class);
//        i.putExtra(NotesDbAdapter.KEY_ROWID, id);
//        startActivityForResult(i, ACTIVITY_EDIT);
//    }
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PICK_CONTACT) {
//            if (resultCode == RESULT_OK) {
                //Cursor contact = getContentResolver().query(data.getData(), null, null, null, null);
                //contact.moveToFirst();
                //String name = contact.getString(contact.getColumnIndexOrThrow(People.NAME));
                //Toast.makeText(this, getString(R.string.chosen) + " " + name, Toast.LENGTH_LONG).show();
//            }
//	      }
    }
}    

	    
