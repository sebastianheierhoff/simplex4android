package com.googlecode.simplex4android;

//import com.googlecode.simplex4android.Simplex;
import com.googlecode.simplex4android.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class simplex4android extends Activity {
	
/*	
 * Startet entweder 
		Activity um neues Problem anzulegen, oder 
		ListView um Probleme zu laden
			l�dt View zum �ndern/anlegen von Problemen mit gespeicherten Werten
			
			laden Ausgabe, Zur�ckbutton, um wieder auf den HomeScreen zur�ck zu gelangen, Speichern Button, um zur ListView zur�ck zu kehren
*/	
	
//    private static final int ACTIVITY_CREATE=0;
//    private static final int ACTIVITY_EDIT=1;
//
//    private static final int INSERT_ID = Menu.FIRST;
//    private static final int DELETE_ID = Menu.FIRST + 1;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
    	final Button button = (Button) findViewById(R.id.button_new);
	    button.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	    	    Intent ConstraintEditIntent = new Intent();
	    	    ConstraintEditIntent.setClassName("com.googlecode.simplex4android.simplex4android", "com.googlecode.simplex4android.ConstraintEdit");
	    	    //myIntent.putExtra("com.android.samples.SpecialValue", "Hello, Joe!"); 
	    	    startActivity(ConstraintEditIntent);   
	        }
	    });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //fillData();
    }
}    

	    
