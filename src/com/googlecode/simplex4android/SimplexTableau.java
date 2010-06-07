package com.googlecode.simplex4android;

public class SimplexTableau {

	private double[][]tableau;
	private int[] target;
	
	public SimplexTableau(double[][] tableau, int[] target){
		this.tableau = tableau;
		this.target = target;
	}

	public double[][] getTableau() {
		return tableau;
	}

	public void setTableau(double[][] tableau) {
		this.tableau = tableau;
	}

	public int[] getTarget() {
		return target;
	}

	public void setTarget(int[] target) {
		this.target = target;
	}
	
	
}
