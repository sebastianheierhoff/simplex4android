package com.googlecode.simplex4android.test;

import java.io.IOException;

import com.googlecode.simplex4android.Constraint;

import junit.framework.TestCase;

public class TestConstraint extends TestCase {
	Constraint c;
	public static void setUpClass() throws Exception{
	}
	public static void tearDownClass() throws Exception{
	}
	public void setUp(){
		c = new Constraint();
	}
	public void tearDown(){
	}
	
	public void testSetValue(){
		c.setValue(5, new Double(5));
		assertEquals(c.getValue(5), new Double(5).doubleValue());
	}
	
	public void testFractionToDbl(){
		String s = "24/12";
		double d = 24.0/12.0;
		try{
			assertEquals(d,Constraint.fractionToDbl(s));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
