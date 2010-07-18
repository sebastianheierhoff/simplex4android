package com.googlecode.simplex4android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SimplexHistoryShow extends Activity {
	
	//Ressourcen
	private static SimplexHistory[] simplexhistoryarray;
	private static int currentphase;
	private static SimplexHistory current;
	private static int currenti;
	private static int lasti;
	private static boolean twoPhases;
	private static WebView mWebView;
	private static String tableauToHtml;
    private long lastTouchTime = -1;
    
    private static TextView label;
    private static TextView txt_solution;
    private static Button btn_switchphases; 
	private static Button btn_first; 
	private static Button btn_previous; 
    private static Button btn_next;
    private static Button btn_last;

    
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    	setContentView(R.layout.simplexhistory_show);

    	//Ressourcen
    	btn_switchphases = (Button) findViewById(R.id.btn_switchphases);
    	btn_first = (Button) findViewById(R.id.btn_first);
    	btn_previous = (Button) findViewById(R.id.btn_previous);
    	btn_next = (Button) findViewById(R.id.btn_next);
    	btn_last = (Button) findViewById(R.id.btn_last);
    	label = (TextView) findViewById(R.id.label);
    	txt_solution = (TextView) findViewById(R.id.txt_solution);
    	
    	try{
    	simplexhistoryarray = (SimplexHistory[]) this.getIntent().getSerializableExtra("simplexhistoryarray");
    	}
    	catch(Exception ex){
    		simplexhistoryarray = InputsShow.simplexhistoryarray;
    		Toast.makeText(SimplexHistoryShow.this, "Fehler", Toast.LENGTH_LONG);
    	}
    
    	currenti = 0;
    	//Werden beide Phasen der 2-Phasen Methode durchgeführt?
    	if(simplexhistoryarray[0] != null && simplexhistoryarray[1] != null){ //beide Phasen werden durchlaufen
    		twoPhases = true;
    		btn_switchphases.setVisibility(View.VISIBLE);
    	}
    	else{
    		twoPhases = false;
    	}
    	
    	//findCurrentPhase();
    	if(simplexhistoryarray[0] == null){ //1. Phase == null -> 1. Phase nicht nötig, direkt in 2. Phase
    		currentphase = 2;
    		current = simplexhistoryarray[1];
    		//btn_switchphases.setVisibility(View.VISIBLE); //unnötig
    	}
    	else{
    		currentphase = 1;
    		current = simplexhistoryarray[0];
    	}
    	
    	findLastI();
    	changeLabel();

    	mWebView = (WebView) findViewById(R.id.webview_tableau);
    	mWebView.setWebViewClient(new WebViewClient());
    	mWebView.getSettings().setJavaScriptEnabled(true);
    	mWebView.getSettings().setBuiltInZoomControls(true);

    	tableauToHtml = current.getFirstElement().tableauToHtml();
    	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
    	
    	//LongClick to showSolution();
    	mWebView.setOnLongClickListener(new OnLongClickListener() {
    		public boolean onLongClick(View v){
    			showSolution(SimplexHistoryShow.this);
    			return false;
    		}});
    		
    	//First-Button
	    btn_first.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	if(currenti >= lasti-1){
	        		findViewById(R.id.btn_next).setEnabled(true);
	        		findViewById(R.id.btn_last).setEnabled(true);
	        	}
	        	
	        	currenti=0;
	        	tableauToHtml = current.getFirstElement().tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
	        	
	        	changeLabel();
	        	
        		findViewById(R.id.btn_previous).setEnabled(false);
	        	findViewById(R.id.btn_first).setEnabled(false);
	        }
	    });	    
	    
	    //Previous-Button
	    btn_previous.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	if(currenti >= lasti-1){
	        		findViewById(R.id.btn_next).setEnabled(true);
	        		findViewById(R.id.btn_last).setEnabled(true);
	        	}

	        	currenti--;
	        	tableauToHtml = current.getElement(currenti).tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
	        	
	        	changeLabel();
	        	
	        	if(currenti == 0){
	        		findViewById(R.id.btn_previous).setEnabled(false);
		        	findViewById(R.id.btn_first).setEnabled(false);
	        	}
	        }
	    });	  
	    
	    //Next-Button
	    btn_next.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	findViewById(R.id.btn_previous).setEnabled(true);
	        	findViewById(R.id.btn_first).setEnabled(true);

	        	currenti++;
	        	tableauToHtml = current.getElement(currenti).tableauToHtml();
        		mWebView.loadData(tableauToHtml, "text/html", "utf-8");

	        	changeLabel();
        		
	        	if(currenti == lasti){
    	        	findViewById(R.id.btn_next).setEnabled(false);
	        		findViewById(R.id.btn_last).setEnabled(false);
	        	}
	        }
	    });	    
	    
	    //Last-Button
	    btn_last.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	        	findViewById(R.id.btn_previous).setEnabled(true);
	        	findViewById(R.id.btn_first).setEnabled(true);
	        	
	        	currenti = current.size()-1;
	    		tableauToHtml = current.getLastElement().tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
	        	
	        	changeLabel();

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
	        	
	        	changeLabel();
	    		//Buttons btn_first und btn_previous deaktivieren
	        	findViewById(R.id.btn_first).setEnabled(false);
	        	findViewById(R.id.btn_previous).setEnabled(false);
	        	findViewById(R.id.btn_next).setEnabled(false);
	        	findViewById(R.id.btn_last).setEnabled(false);
	    	}
	    });

	    //Zurück-Button (zurück zur InputShow)
    	final Button btn_back = (Button) findViewById(R.id.btn_back);
	    btn_back.setOnClickListener(new OnClickListener() {
	    	@SuppressWarnings("unchecked")
			public void onClick(View v) {
		        Intent InputsEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputsShow");
		        InputsEditIntent.putExtra("inputs", SimplexHistoryShow.this.getIntent().getSerializableExtra("inputs"));
		        InputsEditIntent.putExtra("edit", true);
		        InputsEditIntent.putExtra("id", SimplexHistoryShow.this.getIntent().getIntExtra("id", -1));
		    	startActivity(InputsEditIntent);
	    	}
	    });
	}	

	public void showSolution(Context context){
    	//letztes Element der 1. Phase == null -> keine optimale Lösung auffindbar, 2. Phase ebenfalls == null
    	
		//ansonsten letztes Element der 1. Phase optimal
    	
    	//letztes Element der 2. Phase == null -> keine optimale Lösung auffindbar
    	
    	//ansonsten letztes Element der 2. Phase optimal
		Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog);
		dialog.setTitle("Lösung"); //1. Phase/2. Phase einfügen!
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText(current.getElement(lasti).getSolution());
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
	}
	
	public void findLastI(){
		if(current.getLastElement() == null){
			lasti = current.size()-2;
		}
		else{
			lasti = current.size()-1;
		}
	}
	
	public void changeLabel(){
		//Label anpassen, je nachdem, um das wie vielte Tableau es sich handelt
		if(currenti == lasti){
			if(twoPhases){
				label.setText("Letztes Tableau ("+ currentphase +". Phase) : Lange Klicken zeigt Lösung!");
			}
			else{
				label.setText("Letztes Tableau: Lange Klicken zeigt Lösung!");
			}
			txt_solution.setText(current.getElement(lasti).getSolution()); //Lösung anzeigen
			btn_next.setEnabled(false);
			btn_last.setEnabled(false);
		}
		else if(currenti == 0){
			if(twoPhases){
				label.setText("Starttableau: ("+ currentphase +". Phase) :");
			}
			else{
				label.setText("Starttableau:");
			}
		}
		else{
			if(twoPhases){
				label.setText("Aktuelles Tableau ("+ currentphase +". Phase) :");
			}
			else{
				label.setText("Aktuelles Tableau:");
			}
		}
	}
}
