package com.gmoneycorp.math;
import java.util.List;
import java.util.ArrayList;

public class Polynomial {

	private int[] coeffs;
	
	public Polynomial(int[] coeffs) {
		this.coeffs = coeffs;
	}
	
	public List<Double> possibleRoots() {
		int q = coeffs[0];
		int p = coeffs[coeffs.length - 1];
		
		List<Integer> qFactors = Factor.factor(q);
		List<Integer> pFactors = Factor.factor(p);
		List<Double> possibleRoots = new ArrayList<>();
		
		for (int i = 0; i < qFactors.size(); i++) {
			for (int j = 0; j < pFactors.size(); j++) {
				double possibleRoot = (double)pFactors.get(j) / (double)qFactors.get(i);
				if (!possibleRoots.contains(possibleRoot))
					possibleRoots.add(possibleRoot);
			}
		}
		
		return possibleRoots;
	}
	
	public List<Fraction> possibleRootsFractions() {
		int q = coeffs[0];
		int p = coeffs[coeffs.length - 1];
		
		List<Integer> qFactors = Factor.factor(q);
		List<Integer> pFactors = Factor.factor(p);
		List<Fraction> possibleRoots = new ArrayList<>();
		
		for (int i = 0; i < qFactors.size(); i++) {
			for (int j = 0; j < pFactors.size(); j++) {
				Fraction currentRoot =  new Fraction(pFactors.get(j), qFactors.get(i));
				if (!possibleRoots.contains(currentRoot))
					possibleRoots.add(currentRoot);
			}
		}
		
		return possibleRoots;
	}
}
