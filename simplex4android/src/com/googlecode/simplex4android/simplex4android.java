package com.googlecode.simplex4android;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class simplex4android extends Activity {

	WebView mWebView;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.view);

	    mWebView = (WebView) findViewById(R.id.tableau);
	    //mWebView.setWebViewClient(new HelloWebViewClient());
	    //mWebView.getSettings().setJavaScriptEnabled(true);

	    String summary = "<html><body>Platzhalter f&uuml;r die Ausgabe eines SimplexTableaus</body></html>";
	    mWebView.loadData(summary, "text/html", "utf-8");
	    // ... although note that there are restrictions on what this HTML can do.
	    // See the JavaDocs for loadData() and loadDataWithBaseURL() for more info.
	}	
}