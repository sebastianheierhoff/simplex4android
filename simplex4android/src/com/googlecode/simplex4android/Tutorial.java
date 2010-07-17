package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class Tutorial extends Activity {
	
	SimplexHistory[] simplexhistoryarray;
	SimplexHistory current;
	int currenti;
	int currentphase;
	WebView mWebView;
	String tableauToHtml;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    	setContentView(R.layout.simplexhistory_show);

    	//simplexhistoryarray = this.getIntent().getSerializableExtra("simplexhistoryarray");
    	simplexhistoryarray = InputShow.simplexhistoryarray;
    	
    	final Button btn_switchphases = (Button) findViewById(R.id.btn_switchphases);
    	if(simplexhistoryarray[0] != null){
    		currentphase = 1;
    		current = simplexhistoryarray[0];
    		btn_switchphases.setVisibility(View.VISIBLE);
    	}
    	else if(simplexhistoryarray[1] != null){
    		currentphase = 2;
    		current = simplexhistoryarray[1];
    		btn_switchphases.setVisibility(View.INVISIBLE);
    	}
    	else{
			Toast.makeText(Tutorial.this,"Unbekannter Fehler.",Toast.LENGTH_LONG).show();
    	}
    	
    	currenti = 0;
    	mWebView = (WebView) findViewById(R.id.webview_tableau);
    	mWebView.setWebViewClient(new WebViewClient());
    	mWebView.getSettings().setJavaScriptEnabled(true);

    	tableauToHtml = current.getFirstElement().tableauToHtml();
    	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
    	
    	// ... although note that there are restrictions on what this HTML can do.
    	// See the JavaDocs for loadData() and loadDataWithBaseURL() for more info.


    	//First-Button
    	final Button btn_first = (Button) findViewById(R.id.btn_first);
	    btn_first.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	findViewById(R.id.btn_next).setEnabled(true);
	        	findViewById(R.id.btn_last).setEnabled(true);
	        	
	        	currenti=0;
	        	tableauToHtml = current.getFirstElement().tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
        		
        		findViewById(R.id.btn_previous).setEnabled(false);
	        	findViewById(R.id.btn_first).setEnabled(false);
	        }
	    });	    
	    
	    //Previous-Button
    	final Button btn_previous = (Button) findViewById(R.id.btn_previous);
	    btn_previous.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	findViewById(R.id.btn_next).setEnabled(true);
	        	findViewById(R.id.btn_last).setEnabled(true);

	        	currenti--;
	        	tableauToHtml = current.getElement(currenti).tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
	        	if(currenti == 0){
	        		findViewById(R.id.btn_previous).setEnabled(false);
		        	findViewById(R.id.btn_first).setEnabled(false);
	        	}
	        }
	    });	  
	    
	    //Next-Button
    	final Button btn_next = (Button) findViewById(R.id.btn_next);
	    btn_next.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	findViewById(R.id.btn_previous).setEnabled(true);
	        	findViewById(R.id.btn_first).setEnabled(true);

	        	if(currenti+1 <= current.size()-1){
	        		currenti++;
	        	}
	        	tableauToHtml = current.getElement(currenti).tableauToHtml();
        		mWebView.loadData(tableauToHtml, "text/html", "utf-8");

        		if(currenti == current.size()-1){
    	        	findViewById(R.id.btn_next).setEnabled(false);
	        		findViewById(R.id.btn_last).setEnabled(false);
	        	}
	        }
	    });	    
	    
	    //Last-Button
    	final Button btn_last = (Button) findViewById(R.id.btn_last);
	    btn_last.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	        	findViewById(R.id.btn_previous).setEnabled(true);
	        	findViewById(R.id.btn_first).setEnabled(true);
	        	
	        	currenti = current.size()-1;
	    		tableauToHtml = current.getLastElement().tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");

	        	findViewById(R.id.btn_next).setEnabled(false);
	        	findViewById(R.id.btn_last).setEnabled(false);
	    	}
	    });
	    
	    //2.Phase Button (unsichtbar wenn keine 1. Phase notwendig)
	    btn_switchphases.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	    		//TODO: Code einfügen
	    		if(currentphase == 1){
		    		//current auf SimplexHistory der 2. Phase setzen, currenti auf 0 setzen
		    		currentphase = 2;
	    			current = simplexhistoryarray[1];
		    		//Buttontext aktualisieren
		    		((Button) v).setText("1. Phase");
	    		}
	    		if(currentphase == 2){
		    		//current auf SimplexHistory der 2. Phase setzen, currenti auf 0 setzen
		    		currentphase = 1;
	    			current = simplexhistoryarray[0];
		    		//Buttontext aktualisieren
		    		((Button) v).setText("2. Phase");
	    		}
	        	currenti = 0;
	        	//WebView aktualisieren
	        	tableauToHtml = current.getFirstElement().tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
	    		//Buttons btn_first und btn_previous deaktivieren
	        	findViewById(R.id.btn_first).setEnabled(false);
	        	findViewById(R.id.btn_previous).setEnabled(false);
	    	}
	    });

	    //Zurück-Button (zurück zur InputShow)
    	final Button btn_back = (Button) findViewById(R.id.btn_back);
	    btn_back.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	    		//Zurück zu InputShow
	    		//TODO: Code einfügen
	    	}
	    });

	
	}	
}
