package com.googlecode.simplex4android;

/**
 * Klasse zum Lösen eines linearen Gleichungssystems für Franzi
 * -> Missbrauch meiner gauss-Methode :P
 * 
 * Bitte noch online lassen, danke.
 * 
 * @author Stefan
 *
 */
public class Franzi {
	
	public static void main(String[] args){
		int[] target = {0,0,0,0,0};
		double[][] tableau = {{-200000,6,1500,700,-468000},
				{15000,-150,6000,500,-594000},
				{20000,8,-350000,1500,-714000},
				{10000,10,3000,-9500,-600000}};
		
		SimplexProblem problem = new SimplexProblem(tableau,target);
		System.out.println(problem.tableauToString());
		try{
			problem = SimplexLogic.gauss(problem, 0, 0);
			System.out.println(problem.tableauToString());
			problem = SimplexLogic.gauss(problem, 1, 1);
			System.out.println(problem.tableauToString());
			problem = SimplexLogic.gauss(problem, 2, 2);
			System.out.println(problem.tableauToString());
			problem = SimplexLogic.gauss(problem, 3, 3);
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
		
		System.out.println(problem.tableauToString());		
	}

}
