package com.googlecode.simplex4android.test;

import com.googlecode.simplex4android.SimplexLogic;
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
}
