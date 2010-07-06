package com.googlecode.simplex4android;


public class Target extends Inputs{
	private boolean minOrMax; //true bedeutet Minimierung, false bedeutet Maximierung
	
	/**
	 * Standardkonstruktor
	 */
	public Target() {
		super();
	}
	
	/**
	 * boolean der angibt ob Minimierung oder Maximierung vorliegt
	 * @return true steht f�r Minimierung, false f�r Maximierung
	 */
	public boolean isMinOrMax() {
		return minOrMax;
	}
	
	/**
	 * boolean der angibt ob Minimierung oder Maximierung vorliegt
	 * @param minOrMax true steht f�r Minimierung, false f�r Maximierung
	 */
	public void setMinOrMax(boolean minOrMax) {
		this.minOrMax = minOrMax;
	}
}
