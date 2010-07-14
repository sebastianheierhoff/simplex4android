package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class SimplexHistoryShow extends Activity {
	
	SimplexHistory current;
	int currenti;
	WebView mWebView;
	String tableauToHtml;

	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    	setContentView(R.layout.simplexhistory_show);
    	
    	if(InputShow.simplexhistoryarray[0] != null){
    		current = InputShow.simplexhistoryarray[0];
    	}
    	else if(InputShow.simplexhistoryarray[1] != null){
    		current = InputShow.simplexhistoryarray[1];
    	}
    	else{
			Toast.makeText(SimplexHistoryShow.this,"Unbekannter Fehler. Beide SimplexHistories == null",Toast.LENGTH_LONG).show();
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
        		v.setEnabled(false);
        		btn_first.setEnabled(false);
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
	        		v.setEnabled(false);
	        		btn_first.setEnabled(false);
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
	        		v.setEnabled(false);
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
	        	
	        	v.setEnabled(false);
	        	btn_next.setEnabled(false);
	    	}
	    });
	}	
}
