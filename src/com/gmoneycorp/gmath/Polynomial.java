package com.gmoneycorp.gmath;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
		int index = 0;
		for (int i = 0; i < coeffs.length; i++) {
			if (coeffs[i] == 0)
				index++;
			else
				break;
		}
		
		this.coeffs = Arrays.copyOfRange(coeffs, index, coeffs.length);
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

	/**
	 * Integrates a polynomial at a certain interval. Original algorithm by Matthew Rose.
	 * @param begin		the start of the interval
	 * @param end		the end of the interval
	 * @return			the integral of the polynomial on (begin, end)
	 */
	public double integral(int begin, int end) {
		double x = 0;

		for (int i = coeffs.length; i >= 2; i--)
			x += Math.pow(end, i) * (double)coeffs[coeffs.length - i] / (double)i;
		
		x += coeffs[coeffs.length - 1] * end;
		
		for (int i = coeffs.length; i >= 2; i--)
			x -= Math.pow(begin, i) * (double)coeffs[coeffs.length - i] / (double)i;
		
		x -= coeffs[coeffs.length - 1] * begin;

		return x;
	}

	/**
	 * Returns the derivative of a polynomial at a certain value of x. Original algorithm
	 * by Matthew Rose.
	 * @param point
	 * @return
	 */
	public double derivative(int point) {
		double q = 0;
		
		for (int i = 0; i < coeffs.length - 1; i++)
			q += coeffs[i] * (coeffs.length - 1 - i) * Math.pow(point, coeffs.length - 2 - i);
		
		return q;
	}
	
	/**
	 * Returns the polynomial in standard polynomial notation.
	 */
	public String toString() {
		if (coeffs.length == 0)
			return "0";
		
		String s = "";
		
		if (coeffs.length == 1)
			return String.valueOf(coeffs[0]);
		
		if (coeffs.length == 2) {
			if (coeffs[1] == 0)
				return coeffs[0] + "x";
			else if (coeffs[1] < 0)
				return coeffs[0] + "x - " + Math.abs(coeffs[1]);
			else
				return coeffs[0] + "x + " + coeffs[1];
		}
		
		s += coeffs[0] + "x^" + (coeffs.length - 1);
		
		for (int i = 1; i < coeffs.length; i++) {
			if (coeffs[i] != 0) {
				if (coeffs[i] < 0)
					s += " - ";
				else
					s += " + ";
				
				s += String.valueOf(Math.abs(coeffs[i]));
				
				if (i < coeffs.length - 2)
					s += "x^" + (coeffs.length - 1 - i);
				else if (i == coeffs.length - 2)
					s += "x";
			}
		}
		
		return s;
	}
}
