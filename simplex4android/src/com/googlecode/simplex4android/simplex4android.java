package com.googlecode.simplex4android;

import com.googlecode.simplex4android.Simplex;
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
			lädt View zum ändern/anlegen von Problemen mit gespeicherten Werten
			
			laden Ausgabe, Zurückbutton, um wieder auf den HomeScreen zurück zu gelangen, Speichern Button, um zur ListView zurück zu kehren
*/	
	
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
        registerForContextMenu(getListView());
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, "Hinzufügen");
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case INSERT_ID:
                createNote();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, "Löschen");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                //mDbHelper.deleteNote(info.id);
                //fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createNote() {
        Intent i = new Intent(this, SimplexProblemEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, NoteEdit.class);
        i.putExtra(NotesDbAdapter.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //fillData();
    }
    

    
    
    
    
    
    
    
    
    
    
	    final Button button = (Button) findViewById(R.id.button_new);
	    button.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	            setContentView(R.layout.constraint);
	        }
	    });

	    
	    //Testproblem
	    double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}}; 
		int[] target = {1,2,7,5,0,0,0}; 
		SimplexProblem firstProblem = new SimplexProblem(tableau, target);

		//WebView mWebView;
	    //mWebView = (WebView) findViewById(R.id.tableau);
	    //mWebView.setWebViewClient(new HelloWebViewClient());
	    //mWebView.getSettings().setJavaScriptEnabled(true);

	    //String summary = firstProblem.tableauToHtml();
	    //mWebView.loadData(summary, "text/html", "utf-8");
	    // ... although note that there are restrictions on what this HTML can do.
	    // See the JavaDocs for loadData() and loadDataWithBaseURL() for more info.

	}	
}