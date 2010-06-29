package com.googlecode.simplex4android.test;

import com.googlecode.simplex4android.SimplexProblem;

import junit.framework.TestCase;

public class TestSimplexProblem extends TestCase {
	private double[][] tableau;
	private int[] target;
	private SimplexProblem problem;

	
	public static void setUpClass() throws Exception{
	}
	public static void tearDownClass() throws Exception{
	}
	public void setUp(){
		double[][] tab = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}};
		this.tableau = tab;
		int[] tar = {1,2,7,5,0,0,0};
		this.target = tar;
		this.problem = new SimplexProblem(tableau, target);
	}
	public void tearDown(){
	}
	
	public void testGetField(){
		assertEquals(problem.getField(0, 0),-1.5);
		assertEquals(problem.getField(2, 2),1.0);
	}
}
