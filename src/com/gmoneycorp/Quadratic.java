package com.gmoneycorp;
import java.util.ArrayList;
import com.gmoneycorp.GMath;

public class Quadratic {

	public int a;
	public int b;
	public int c;
	
	public Quadratic(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public int[] findWorkingFactors() {
		ArrayList<Integer> factors = GMath.factor(a*c);
		
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
	
	public int[] factorQuadratic() {
		int[] workingFactors = findWorkingFactors();

		if (workingFactors == null)
			return null;

		int group1GCF = GMath.findGCF(a, workingFactors[0]);
		int group2GCF = GMath.findGCF(workingFactors[1], c);

		if ((a/group1GCF) != (workingFactors[1]/group2GCF) || (workingFactors[0]/group1GCF) != (c/group2GCF)) {
			group2GCF *= -1;
		}
		int[] finalEquation = {group1GCF, group2GCF, (a/group1GCF), (workingFactors[0]/group1GCF)};

		return finalEquation;
	}
	
	public double[] solveQuadratic() {
		double [] answers = new double [2];
		
		answers[0] = ((-1*b) + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
		answers[1] = ((-1*b) - Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
		
		return answers;
	}
	
	public double solveDiscriminant()  {
		return Math.pow(b, 2) - (4 * a * c);
	}
	
	public String toString() {
		String str = a + "x^2 ";
		if (b < 0)
			str += "- " + -1*b + "x ";
		else
			str += "+ " + b + "x ";
		if (c < 0)
			str += "- " + -1*c;
		else
			str += "+ " + c;
		
		return str;
	}
}
