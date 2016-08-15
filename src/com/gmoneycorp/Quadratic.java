package com.gmoneycorp;

import java.util.ArrayList;

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
