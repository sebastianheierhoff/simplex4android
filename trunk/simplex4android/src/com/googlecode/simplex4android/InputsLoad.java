package com.googlecode.simplex4android;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class InputsLoad extends Activity {

		private static final int ACTIVITY_CREATE=0;
	    private static final int ACTIVITY_EDIT=1;
	    
	    public static final int VIEW_ID = Menu.FIRST;
	    public static final int DELETE_ID = Menu.FIRST + 1;

	    private InputsDb mInputsDb;

	    /** Called when the activity is first created. */
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.inputs_load);

	        ListView listInputs = (ListView) findViewById(R.id.list_problems);
	        //listBookmarks.setDrawingCacheEnabled(true);
	        listInputs.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mInputsDb.getNames()));
	        //bookmarksShrunkView = bookmarksLayout.getDrawingCache();


        
//	        //mInputsDb = new InputsDb();
//	        try{
//	        	//mInputsDb.readProblems();
//	        }
//	        catch(Exception e){
//	        	//TODO: Fehlermeldung ausgeben!
//	        }
	        
//	        getListView().setOnCreateContextMenuListener(this);
//	      	
//	        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
//	    	        		R.layout.inputs_list, c, from, to);
//	    	          
//	        adapter.setViewBinder(new ViewBinder() 
//	        {	           
//	          @Override
//	          public boolean setViewValue(View view, Cursor theCursor, int column) 
//	          {
//	            final String ColNameModel = theCursor.getString(1); //Name und Model
//	    	((TextView)view).setText(ColNameModel);
//	    	return true;
//	          }
//	        });
//	        this.setListAdapter(adapter);
	        
	        
	        
	    	final Button back = (Button) findViewById(R.id.button_cancel);
		    back.setOnClickListener(new OnClickListener() {
		        public void onClick(View v) {
		        	finish();
		        }
		    });
	    }

//	    private void createInput() {
//	        Intent i = new Intent(this, InputShow.class);
//	        startActivityForResult(i, ACTIVITY_CREATE);
//	    }
//
//	    @Override
//	    protected void onListItemClick(ListView l, View v, int position, long id) {
//	        super.onListItemClick(l, v, position, id);
//	        Intent i = new Intent(this, InputShow.class);
//	    }
//
//	    @Override
//	    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//	        super.onActivityResult(requestCode, resultCode, intent);
//	    }
//	    
//	        
//	    @Override
//	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//	    	AdapterView.AdapterContextMenuInfo info;
//	        try {
//	             info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//	        } catch (ClassCastException e) {
//	            return;
//	        }
//
//	        Cursor cursor = (Cursor) getListAdapter().getItem(info.position);
//	        if (cursor == null) {
//	            return;
//	        }
//
//	        menu.setHeaderTitle(cursor.getString(1));
//
//	        menu.add(0, VIEW_ID, 0, "Anzeigen");
//	        menu.add(0, DELETE_ID, 0, "Löschen");
//	    }
//	    
//	    public boolean onContextItemSelected(MenuItem item) {		
//			AdapterView.AdapterContextMenuInfo info;
//	        try {
//	             info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//	        } catch (ClassCastException e) {
//	            return false;
//	        }
//			
//			switch (item.getItemId()) {
//	    		case VIEW_ID:
//	    			Toast.makeText(this, "Anzeigen", Toast.LENGTH_LONG);
//	    			return true;
//	    		case DELETE_ID:
//	    			Toast.makeText(this, "Löschen", Toast.LENGTH_LONG);
//	    			return true;
//	    		default:
//	    			return super.onContextItemSelected(item);
//	    		}
//	    	}
}