package com.googlecode.simplex4android;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class SimplexHistoryShow extends Activity {
	
	//Ressourcen
	private static SimplexHistory[] simplexhistoryarray;
	private static int currentphase;
	private static SimplexHistory current;
	private static int currenti;
	private static boolean twoPhases;
	private static boolean solutionShown;

	private static WebView mWebView;
	private static String tableauToHtml;
	
    private static TextView label;
    private static TextView txt_solution;
    private static TextView txt_solution_label;
    private static Button btn_switchphases; 
	private static Button btn_first; 
	private static Button btn_previous; 
    private static Button btn_next;
    private static Button btn_last;
    private static Button btn_back;
    
	/**
	 * Methode, die beim Aufruf der Activity aufgerufen wird
	 */
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.simplexhistory_show);

    	//Ressourcen
    	btn_switchphases = (Button) findViewById(R.id.btn_switchphases);
    	btn_first = (Button) findViewById(R.id.btn_first);
    	btn_previous = (Button) findViewById(R.id.btn_previous);
    	btn_next = (Button) findViewById(R.id.btn_next);
    	btn_last = (Button) findViewById(R.id.btn_last);
    	btn_back = (Button) findViewById(R.id.btn_back);
    	label = (TextView) findViewById(R.id.label);
    	txt_solution = (TextView) findViewById(R.id.txt_solution);
    	txt_solution_label = (TextView) findViewById(R.id.txt_solution_label);
    	
    	//simplexhistoryarray = (SimplexHistory[]) this.getIntent().getSerializableExtra("simplexhistoryarray");
    	simplexhistoryarray = InputsShow.simplexhistoryarray;

    	currenti = 0; //Aktueller Index
    	
    	//Werden beide Phasen der 2-Phasen Methode durchgeführt?
    	if(simplexhistoryarray[0] != null && simplexhistoryarray[1] != null){ //beide Phasen werden durchlaufen
    		twoPhases = true;
    		btn_switchphases.setVisibility(View.VISIBLE);
    	}
    	else{
    		twoPhases = false;
    	}
    	
    	//setCurrentPhase();
    	if(simplexhistoryarray[0] == null){ //1. Phase == null -> 1. Phase nicht nötig, direkt in 2. Phase
    		currentphase = 2;
    		current = simplexhistoryarray[1];
    	}
    	else{
    		currentphase = 1;
    		current = simplexhistoryarray[0];
    	}
    	
    	changeLabel();
    	enableButtons();

    	mWebView = (WebView) findViewById(R.id.webview_tableau);
    	mWebView.setWebViewClient(new WebViewClient());
    	mWebView.getSettings().setJavaScriptEnabled(true);
    	mWebView.getSettings().setBuiltInZoomControls(true);

    	tableauToHtml = current.getFirstElement().tableauToHtml();
    	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
    		
    	//First-Button
	    btn_first.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	currenti=0;
	        	tableauToHtml = current.getFirstElement().tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
	        	
	        	changeLabel();
	        	enableButtons();
	        }
	    });	    
	    
	    //Previous-Button
	    btn_previous.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	currenti--;
	        	tableauToHtml = current.getElement(currenti).tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
	        	
	        	changeLabel();
	        	enableButtons();
	        }
	    });	  
	    
	    //Next-Button
	    btn_next.setOnClickListener(new OnClickListener() {
	        public void onClick(View v) {
	        	currenti++;
	        	tableauToHtml = current.getElement(currenti).tableauToHtml();
        		mWebView.loadData(tableauToHtml, "text/html", "utf-8");

	        	changeLabel();
	        	enableButtons();
	        }
	    });	    
	    
	    //Last-Button
	    btn_last.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	        	currenti = current.size()-1;
	    		tableauToHtml = current.getLastElement().tableauToHtml();
	        	mWebView.loadData(tableauToHtml, "text/html", "utf-8");
	        	
	        	changeLabel();
	        	enableButtons();
	    	}
	    });
	    
	    //2.Phase Button (unsichtbar wenn keine 1. Phase notwendig)
	    btn_switchphases.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	    		if(currentphase == 1){
		    		//current auf SimplexHistory der 2. Phase setzen, currenti auf 0 setzen
		    		currentphase = 2;
	    			current = simplexhistoryarray[1];
		    		//Buttontext aktualisieren
		    		((Button) v).setText("1. Phase");
	    		}
	    		else if(currentphase == 2){
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
	        	
	        	solutionShown = false;

	        	changeLabel();
	        	enableButtons();
	    	}
	    });

	    //Zurück-Button (zurück zur InputShow)
	    btn_back.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
		        Intent InputsEditIntent = new Intent().setClassName("com.googlecode.simplex4android", "com.googlecode.simplex4android.InputsShow");
		        InputsEditIntent.putExtra("inputs", (ArrayList<Input>) SimplexHistoryShow.this.getIntent().getSerializableExtra("inputs"));
		        InputsEditIntent.putExtra("edit", true);
		        InputsEditIntent.putExtra("id", SimplexHistoryShow.this.getIntent().getIntExtra("id", -1));
		    	startActivity(InputsEditIntent);
	    	}
	    });
	}	

    /**
     * Öffnet ein Dialogfenster mit der Lösung der aktuellen Phase, "" falls das Tableau nicht optimal ist.
     * @param context - Context der aktuellen Aktivity/Application
     */
	public void showSolution(Context context){
		if(!solutionShown){
			Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.dialog);
			dialog.setTitle("Lösung"); //1. Phase/2. Phase einfügen!
			TextView text = (TextView) dialog.findViewById(R.id.text);
			String solution_string = current.getLastElement().getSolution();
			if(solution_string.equals("")){
				text.setText("Keine optimale Lösung gefunden"); //Lösung anzeigen
			}
			else{
				text.setText(solution_string);
			}
		
			
			text.setText(current.getLastElement().getSolution());
			dialog.show();
			dialog.setCanceledOnTouchOutside(true);
		}
		solutionShown = true;
	}
	
	/**
	 * Aktiviert/Deaktiviert die Navigationsbuttons abhängig davon, welches Tableau gerade angezeigt wird
	 */
	public void enableButtons(){
    	btn_next.setEnabled(true);
    	btn_last.setEnabled(true);
		btn_previous.setEnabled(true);
    	btn_first.setEnabled(true);
    	if(currenti == 0 && currenti == current.size()-1){
			btn_next.setEnabled(false);
			btn_last.setEnabled(false);
    		btn_previous.setEnabled(false);
			btn_first.setEnabled(false);
		}
		else if(currenti == 0 ){
			btn_previous.setEnabled(false);
			btn_first.setEnabled(false);
    	}
		else if(currenti == current.size()-1){
			btn_next.setEnabled(false);
			btn_last.setEnabled(false);
		}
	}
	
	/**
	 * Anpassen der Label txt_label, txt_solution_label, txt_solution abhängig davon, ob zwei Phasen durchlaufen werden und welches Tableau gerade angezeigt wird.
	 */
	public void changeLabel(){
		String typeOfProblem;
		if(current.getElement(currenti).getClass().equals(SimplexProblemPrimal.class)){
			typeOfProblem = "primal";
		}
		else{
			typeOfProblem = "dual";
		}
		if(currenti == current.size()-1){
			if(twoPhases){
				label.setText("Letztes Tableau ("+ currentphase +". Phase, " + typeOfProblem+ "):");
				txt_solution_label.setVisibility(View.VISIBLE);
				txt_solution_label.setText("Lösung ("+ currentphase +". Phase):");
			}
			else{
				label.setText("Letztes Tableau ("+ typeOfProblem+ "):");
				txt_solution_label.setVisibility(View.VISIBLE);
				txt_solution_label.setText("Lösung ("+ typeOfProblem+ "):");
			}
			txt_solution.setVisibility(View.VISIBLE);
			String solution_string = current.getLastElement().getSolution();
			if(solution_string.equals("")){
				txt_solution.setText("Keine optimale Lösung gefunden"); //Lösung anzeigen
			}
			else{
				txt_solution.setText(solution_string);
			}
			showSolution(SimplexHistoryShow.this);
		}
		else if(currenti == 0){
			if(twoPhases){
				label.setText("Starttableau: ("+ currentphase +". Phase, " + typeOfProblem+ "):");
			}
			else{
				label.setText("Starttableau ("+ typeOfProblem+ "):");
			}
			txt_solution_label.setVisibility(View.INVISIBLE);
			txt_solution.setVisibility(View.INVISIBLE);
		}
		else{
			if(twoPhases){
				label.setText("Aktuelles Tableau ("+ currentphase +". Phase, " + typeOfProblem+ "):");
			}
			else{
				label.setText("Aktuelles Tableau ("+ typeOfProblem+ "):");
			}
			txt_solution_label.setVisibility(View.INVISIBLE);
			txt_solution.setVisibility(View.INVISIBLE);
		}
	}
}
