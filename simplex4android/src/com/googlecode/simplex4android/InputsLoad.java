package com.googlecode.simplex4android;

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

public class InputsLoad extends ListActivity {

	private static final int ACTIVITY_CREATE=0;
	    private static final int ACTIVITY_EDIT=1;

	    private InputsDb mInputsDb;

	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.inputs_list);
	        mInputsDb = new InputsDb();
	        try{
	        	mInputsDb.readProblems();
	        }
	        catch(Exception e){
	        	//TODO: Fehlermeldung ausgeben!
	        }
	    }

	    private void createInput() {
	        Intent i = new Intent(this, InputShow.class);
	        startActivityForResult(i, ACTIVITY_CREATE);
	    }

	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long id) {
	        super.onListItemClick(l, v, position, id);
	        Intent i = new Intent(this, InputShow.class);
	    }

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        super.onActivityResult(requestCode, resultCode, intent);
	    }
}