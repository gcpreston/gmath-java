package com.gmoneycorp.gmath;
import java.util.ArrayList;

public class GMath {
	
	public static ArrayList<Integer> factor (int num) {
		ArrayList<Integer> factors = new ArrayList<Integer>();

		if (num > 0) {
			for (int i = 1; i <= num; i++) {
				if (num % i == 0)
					factors.add(i);
			}
		}
		else {
			for (int i = 1; i <= num*-1; i++) {
				if (num % i == 0)
					factors.add(i);
			}
		}

		return factors;
	}

	public static int findGCF (int num1, int num2) {
		ArrayList<Integer> factorsNum1 = factor(num1);
		ArrayList<Integer> factorsNum2 = factor(num2);
		int GCF = 1;

		for (int i = 0; i < factorsNum1.size(); i++) {
			for (int j = 0; j < factorsNum2.size(); j++) {
				if (factorsNum1.get(i) == factorsNum2.get(j))
					GCF = factorsNum1.get(i);
			}
		}
		return GCF;
	}
	
	public static int[] findWorkingFactors (int a, int b, int c) {
		ArrayList<Integer> factors = factor(a*c);
		
		int[] workingFactors = new int[2];

		if (c > 0) {
			for (int i = 0; i < factors.size(); i++) {
				if (factors.get(i) + factors.get(factors.size() - 1 - i) == b) {
					workingFactors[0] = factors.get(i);
					workingFactors[1] = factors.get(factors.size() - 1 - i);
					return workingFactors;
				}
			}

			for (int i = 0; i < factors.size(); i++) {
				if ((factors.get(i) * -1) + (factors.get(factors.size() - 1 - i) * -1) == b) {
					workingFactors[0] = (factors.get(i) * -1);
					workingFactors[1] = (factors.get(factors.size() - 1 - i) * -1);
					return workingFactors;
				}
			}
		}
		else {
			for (int i = 0; i < factors.size(); i++) {
				if (factors.get(i) + (factors.get(factors.size() - 1 - i) * -1) == b) {
					workingFactors[0] = factors.get(i);
					workingFactors[1] = (factors.get(factors.size() - 1 - i) * -1);
					return workingFactors;
				}
			}
		}

		return null;
	}

	public static int[] factorQuadratic (int a, int b, int c) {
		int[] workingFactors = findWorkingFactors(a, b, c);

		if (workingFactors == null)
			return null;

		int group1GCF = findGCF(a, workingFactors[0]);
		int group2GCF = findGCF(workingFactors[1], c);

		if ((a/group1GCF) != (workingFactors[1]/group2GCF) || (workingFactors[0]/group1GCF) != (c/group2GCF)) {
			group2GCF *= -1;
		}
		int[] finalEquation = {group1GCF, group2GCF, (a/group1GCF), (workingFactors[0]/group1GCF)};

		return finalEquation;
	}
	
	public static double[] solveQuadratic (int a, int b, int c) {
		double [] answers = new double [2];
		
		answers[0] = ((-1*b) + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
		answers[1] = ((-1*b) - Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
		
		return answers;
	}
}
