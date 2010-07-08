package com.googlecode.simplex4android.test;

import java.util.ArrayList;
import java.util.Arrays;

import com.googlecode.simplex4android.SimplexProblemPrimal;

import junit.framework.TestCase;

public class TestSimplexProblemPrimal extends TestCase {
	private double[][] tableau;
	private double[] target;
	private SimplexProblemPrimal problem;

	
	public static void setUpClass() throws Exception{
	}
	public static void tearDownClass() throws Exception{
	}
	public void setUp(){
		double[][] tab = {{-1.5,3,0,0,1,-1,6},{0,1,0,1,0,-1,3},{0.5,-1,1,0,0,1,1},{0,0,0,0,0,0,0}};
		this.tableau = tab;
		double[] tar = {1,2,7,5,0,0,0};
		this.target = tar;
		this.problem = new SimplexProblemPrimal(tableau, target);
	}
	public void tearDown(){
	}
	
	public void testAddRow(){
		double[] add = {5,10,0,0,0,0,-1,20};
		double[] e = {5,10,0,0,0,0,1,20};
		ArrayList<Double> row = new ArrayList<Double>();
		for(int i=0;i<add.length;i++){
			row.add(new Double(add[i]));
		}
		this.problem.addRow(row);
		double[] addedRow = this.problem.getRow(this.problem.getNoRows()-2);
		assertTrue(Arrays.equals(e,addedRow));
	}
}
