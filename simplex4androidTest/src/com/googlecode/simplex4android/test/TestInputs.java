package com.googlecode.simplex4android.test;

import java.io.IOException;
import com.googlecode.simplex4android.Constraint;
import com.googlecode.simplex4android.Inputs;
import junit.framework.TestCase;

public class TestInputs extends TestCase {
	Inputs c;
	public static void setUpClass() throws Exception{
	}
	public static void tearDownClass() throws Exception{
	}
	public void setUp(){
		c = new Constraint();
	}
	public void tearDown(){
	}
	
	/**
	 * Testet, ob das Hinzuf�gen eines neuen Elementes an einem noch nicht existenten Index funktioniert.
	 */
	public void testSetValue(){
		c.setValue(5, new Double(5));
		assertEquals(c.getValue(5), new Double(5).doubleValue());
	}
	
	/**
	 * Testet, ob ein der Methode fractionToDbl(String s) �bergebener Bruch-String fehlerfrei in ein double �berf�hrt wird.
	 */
	public void testFractionToDblCorrect(){
		String s = "24/12";
		double d = 24.0/12.0;
		try{
			assertEquals(d,Constraint.fractionToDbl(s));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Testet, ob die Methode fractionToDbl(String s) bei falscher Eingabe die passende Exception wirft.
	 */
	public void testFractionToDblFalse(){
		String s = "24";
		try{
			Constraint.fractionToDbl(s);
		}catch(IOException e){
			assertEquals("Kein '/' enthalten!",e.toString());
		}
	}

}
