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
	
	public void testCheckString(){
		assertTrue(SimplexLogic.checkString("0"));
		assertTrue(SimplexLogic.checkString("2"));
		assertTrue(SimplexLogic.checkString("2.0"));
		assertTrue(SimplexLogic.checkString("-2/5"));
		assertTrue(!SimplexLogic.checkString("2/0"));
		assertTrue(!SimplexLogic.checkString("/2"));
		assertTrue(!SimplexLogic.checkString("2/4/"));
		assertTrue(!SimplexLogic.checkString("-2-"));
		assertTrue(!SimplexLogic.checkString("2.0."));
	}
}
