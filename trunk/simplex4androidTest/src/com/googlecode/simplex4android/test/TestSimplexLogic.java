package com.googlecode.simplex4android.test;

import com.googlecode.simplex4android.SimplexLogic;
import com.googlecode.simplex4android.SimplexProblemPrimal;

import junit.framework.TestCase;

public class TestSimplexLogic extends TestCase {
	public static void setUpClass() throws Exception{
	}
	public static void tearDownClass() throws Exception{
	}
	public void setUp(){
	}
	public void tearDown(){
	}
	
	/**
	 * Testet, ob mit checkInput() geprüfte Stringeingaben richtig erkannt werden.
	 */
	public void testcheckInput(){
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
	 * Testet, ob die benötigten künstlichen Variablen hinzugefügt wurden.
	 */
	public void testAddArtificialVars(){
		double[][] tableau = {{-1.5,3,0,0,5,-1,6},{0,1,0,5,0,-1,3},{0.5,-1,5,0,0,1,1},{0,0,0,0,0,0,0}};
		double[] target = {1,2,7,5,0,0,0};
		SimplexProblemPrimal problem = new SimplexProblemPrimal(tableau, target);
		problem = (SimplexProblemPrimal) SimplexLogic.addArtificialVars(problem);
		assertEquals(problem.getField(0, 6),1.0);
		assertEquals(problem.getField(1, 7),1.0);
		assertEquals(problem.getField(2, 8),1.0);
	}
}
