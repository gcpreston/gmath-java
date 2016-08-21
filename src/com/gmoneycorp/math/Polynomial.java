package com.gmoneycorp.math;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Graham Preston
 */
public class Polynomial {

	private int[] coeffs;
	
	/**
	 * Constructor that initializes the coefficients of the polynomial.
	 * @param coeffs	coefficients of the polynomial
	 */
	public Polynomial(int[] coeffs) {
		this.coeffs = coeffs;
	}
	
	/**
	 * Uses the rational root theorem to return the possible roots of the polynomial as fractions.
	 * @return			a List of possible roots in the form of Fractions
	 */
	public List<Fraction> possibleRoots() {
		int q = coeffs[0];
		int p = coeffs[coeffs.length - 1];
		
		List<Integer> qFactors = Factor.factor(q);
		List<Integer> pFactors = Factor.factor(p);
		List<Fraction> possibleRoots = new ArrayList<>();
		
		for (int i = 0; i < qFactors.size(); i++) {
			for (int j = 0; j < pFactors.size(); j++) {
				Fraction currentRoot =  new Fraction(pFactors.get(j), qFactors.get(i));
				currentRoot = currentRoot.simplify();
				if (!possibleRoots.contains(currentRoot)) {
					possibleRoots.add(currentRoot);
					possibleRoots.add(new Fraction(currentRoot.getNumerator(), currentRoot.getDenominator(), true));
				}
			}
		}
		
		return possibleRoots;
	}
	
	/**
	 * Uses synthetic division to find which of the possible roots are real roots.
	 * @return			a List of real roots in the form of Fractions
	 */
	public List<Fraction> realRoots() {
		double temp = coeffs[0];
		List<Fraction> possibleRoots = possibleRoots();
		List<Fraction> roots = new ArrayList<>();
		
		for (Fraction f : possibleRoots) {
			for (int i = 1; i < coeffs.length; i++) {
				temp *= f.toDecimal();
				temp += coeffs[i];
			}
			
			if (temp == 0)
				roots.add(f);
			temp = coeffs[0];
		}
		
		return roots;
	}
}
