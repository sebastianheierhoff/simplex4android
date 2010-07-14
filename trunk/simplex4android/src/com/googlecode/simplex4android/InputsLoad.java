package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class InputsLoad extends Activity {
	
	    public static final int VIEW_ID = Menu.FIRST;
	    public static final int DELETE_ID = Menu.FIRST + 1;

	    private InputsDb mInputsDb;

	    /** Called when the activity is first created. */
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.inputs_load);

	        mInputsDb = new InputsDb();
	        try{
	        	mInputsDb.readProblems();
	        }
	        catch(Exception e){
	        	//TODO: Fehlermeldung ausgeben!
	        }

	        ListView listInputs = (ListView) findViewById(R.id.list_problems);
	        //listBookmarks.setDrawingCacheEnabled(true);
	        try{
	            ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.tv_row, mInputsDb.getNames());
	            listInputs.setAdapter(adapter);
	            listInputs.setVisibility(View.VISIBLE);
	        }catch(NegativeArraySizeException nase){
	        	
	        }
	    }
	    
		public void DeleteClickHandler(View v)
		{
			RelativeLayout vwParentRow = (RelativeLayout)v.getParent(); // get the row the clicked button is in
			Button btnChild = (Button)vwParentRow.getChildAt(1); //get the 2nd child of our ParentRow 
			vwParentRow.refreshDrawableState(); // and redraw our row
			}

		public void EditClickHandler(View v)
		{
			RelativeLayout vwParentRow = (RelativeLayout)v.getParent();
			Button btnChild = (Button)vwParentRow.getChildAt(2);
			vwParentRow.refreshDrawableState();
		}

}
