package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class simplex4android extends Activity {

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
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