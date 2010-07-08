package com.googlecode.simplex4android.test;

import java.io.IOException;
import java.util.Arrays;

import com.googlecode.simplex4android.SimplexLogic;
import com.googlecode.simplex4android.SimplexProblemDual;
import com.googlecode.simplex4android.SimplexProblemPrimal;

import junit.framework.TestCase;

public class TestSimplexLogic extends TestCase {
	private SimplexProblemPrimal problemPrimal;
	private SimplexProblemDual problemDual;
	
	public static void setUpClass() throws Exception{
	}
	public static void tearDownClass() throws Exception{
	}
	public void setUp(){
		double[][] tableau = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}};
		double[] target = {1,2,7,5,0,0,0};
		problemPrimal = new SimplexProblemPrimal(tableau, target);
	}
	public void tearDown(){
	}
	
	/**
	 * Testet, ob die benötigten künstlichen Variablen hinzugefügt wurden.
	 */
	public void testAddArtificialVars(){
		double[][] tab = {{-1.5,3,0,0,5,-1,6},{0,1,0,5,0,-1,3},{0.5,-1,5,0,0,1,1},{0,0,0,0,0,0,0}};
		this.problemPrimal.setTableau(tab);
		this.problemPrimal = (SimplexProblemPrimal) SimplexLogic.addArtificialVars(problemPrimal);
		assertEquals(this.problemPrimal.getField(0, 6),1.0);
		assertEquals(this.problemPrimal.getField(1, 7),1.0);
		assertEquals(this.problemPrimal.getField(2, 8),1.0);
	}
	
	/**
	 * Testet, ob testAddArtificialVars null zurück gibt, falls das Hinzufügen von künstlichen Variablen nicht nötig ist.
	 */
	public void testAddArtificialVarsNull(){
		assertEquals(null,SimplexLogic.addArtificialVars(problemPrimal));
	}
	
	/**
	 * Testet, ob die Delta-Werte richtig berechnet werden.
	 */
	public void testCalcDeltas(){
		SimplexLogic.calcDeltas(this.problemPrimal);
		double[] deltas = {2.5,-4,0,0,0,2,22};
		assertTrue(Arrays.equals(deltas, this.problemPrimal.getRow(this.problemPrimal.getNoRows()-1)));
	}
	
	/**
	 * Prüft, ob die x/f-Werte korrekt berechnet wurden.
	 */
	public void testCalcXByF(){
		SimplexLogic.calcDeltas(this.problemPrimal);
		SimplexLogic.calcXByF(problemPrimal);
		assertEquals(-4.0, problemPrimal.getXByF()[0]);
		assertTrue(Double.isInfinite(problemPrimal.getXByF()[1]));
		assertEquals(2.0, problemPrimal.getXByF()[2]);
	}
	
	/**
	 * Testet, ob mit checkInput() geprüfte Stringeingaben richtig erkannt werden.
	 */
	public void testCheckInput(){
		assertTrue(SimplexLogic.checkInput("0"));
		assertTrue(SimplexLogic.checkInput("2"));
		assertTrue(SimplexLogic.checkInput("2.0"));
		assertTrue(SimplexLogic.checkInput("-2/5"));
		assertTrue(!SimplexLogic.checkInput("2/0"));
		assertTrue(!SimplexLogic.checkInput("/2"));
		assertTrue(!SimplexLogic.checkInput("2/4/"));
		assertTrue(!SimplexLogic.checkInput("-2-"));
		assertTrue(!SimplexLogic.checkInput("2.0."));
	}
	
	/**
	 * Testet, ob die Optimalität das SimplexProblems korrekt erkannt wird.
	 */
	public void testCheckOptimal(){
		SimplexLogic.calcDeltas(problemPrimal);
		assertTrue(!SimplexLogic.checkOptimal(problemPrimal));
		double[] row = {-2,-100,0,0,0,-5,12};
		problemPrimal.setRow(row, problemPrimal.getNoRows()-1);
		assertTrue(SimplexLogic.checkOptimal(problemPrimal));
	}
	
	/**
	 * Testet, ob die korrekte Pivotspalte gewählt wird.
	 */
	public void testChoosePivotColumn(){
		SimplexLogic.calcDeltas(problemPrimal);
		int pivotColumn = SimplexLogic.choosePivotColumn(problemPrimal);
		assertEquals(0,pivotColumn);
	}
	
	/**
	 * Prüft, ob die korrekte Zeile des neuen Pivotelements gefunden wird.
	 */
	public void testChoosePivotRow(){
		SimplexLogic.calcDeltas(problemPrimal);
		SimplexLogic.calcXByF(problemPrimal);
		assertEquals(2, SimplexLogic.choosePivotRow(problemPrimal));
		double[] xByF = {-4,-2,-1};
		problemPrimal.setXByF(xByF);
		assertEquals(-1, SimplexLogic.choosePivotRow(problemPrimal));
	}
	
	/**
	 * Prüft, ob die Zeilenindizes der Einsen in den Pivotspalten korrekt gefunden werden. 
	 */
	public void testFindPivotRows(){
		int[] pivots = {2,1,0};
		assertTrue(Arrays.equals(pivots, SimplexLogic.findPivotRows(problemPrimal)));
	}
	
	/**
	 * Prüft, ob die Pivotspalten korrekt gefunden werden.
	 */
	public void testFindPivots(){
		SimplexLogic.findPivots(problemPrimal);
		int [] pivots = {2,3,4};
		assertTrue(Arrays.equals(pivots, problemPrimal.getPivots()));
		
	}
	
	/**
	 * Prüft, ob der Gauß-Algorithmus korrekt durchgeführt wird.
	 */
	public void testGauss(){
		SimplexLogic.calcDeltas(problemPrimal);
		double[][] tableau = {{0,0,3,0,1,2,9},{0,1,0,1,0,-1,3},{1,-2,2,0,0,2,2},{0,1,-5,0,0,-3,17}};
		try{
			SimplexLogic.gauss(problemPrimal, 2, 0);
		}catch(IOException e){
			e.toString();
		}
		assertTrue(Arrays.equals(tableau[0], problemPrimal.getTableau()[0]));
		assertTrue(Arrays.equals(tableau[1], problemPrimal.getTableau()[1]));
		assertTrue(Arrays.equals(tableau[2], problemPrimal.getTableau()[2]));
		assertTrue(Arrays.equals(tableau[3], problemPrimal.getTableau()[3]));
	}
	
	/**
	 * Prüft, ob die primale Zulässigkeit eines SimplexProblems korrekt erkannt wird.
	 */
	public void testPrimalValid(){
		SimplexLogic.calcDeltas(problemPrimal);
		SimplexLogic.calcXByF(problemPrimal);
		assertTrue(SimplexLogic.primalValid(problemPrimal));
		double[] c = {4,-2,0};
		problemPrimal.setColumn(c, problemPrimal.getNoColumns()-1);
		assertTrue(!SimplexLogic.primalValid(problemPrimal));
	}
}
