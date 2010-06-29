package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class simplex4android extends Activity {

	WebView mWebView;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.view);

		double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}}; 
		int[] target = {1,2,7,5,0,0,0}; 
		SimplexProblem firstProblem = new SimplexProblem(tableau, target);
	    
	    mWebView = (WebView) findViewById(R.id.tableau);
	    //mWebView.setWebViewClient(new HelloWebViewClient());
	    //mWebView.getSettings().setJavaScriptEnabled(true);

	    String summary = firstProblem.tableauToHtml();
	    mWebView.loadData(summary, "text/html", "utf-8");
	    // ... although note that there are restrictions on what this HTML can do.
	    // See the JavaDocs for loadData() and loadDataWithBaseURL() for more info.

	}	
}