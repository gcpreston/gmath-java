package com.gmoneycorp.gmath;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Graham Preston
 */
public class Polynomial {

	private double[] coeffs;

	/**
	 * Constructor that initializes the coefficients of the polynomial.
	 * @param coeffs	coefficients of the polynomial
	 */
	public Polynomial(double[] coeffs) {
		int index = 0;
		for (int i = 0; i < coeffs.length; i++) {
			if (coeffs[i] == 0)
				index++;
			else
				break;
		}

		this.coeffs = Arrays.copyOfRange(coeffs, index, coeffs.length);
	}
	
	public Polynomial(List<Fraction> coeffs) {
		int index = 0;
		for (int i = 0; i < coeffs.size(); i++) {
			if (coeffs.get(i).equals(new Fraction(0, 1)))
				index++;
			else
				break;
		}
		
		this.coeffs = new double[coeffs.size() - index];
		for (int i = index; i < coeffs.size(); i++)
			this.coeffs[i] = Fraction.toDecimal(coeffs.get(i + index));
	}

	/**
	 * Uses the rational root theorem to return the possible roots of the polynomial as fractions.
	 * Polynomial must contain whole numbers to work properly.
	 * @return			a List of possible roots in the form of Fractions
	 */
	public List<Fraction> possibleRoots() {
		int q = (int)coeffs[0];
		int p = (int)coeffs[coeffs.length - 1];

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
		List<Fraction> coeffsTemp = arrayToList(coeffs);
		List<Fraction> possibleRoots = possibleRoots();
		List<Fraction> roots = new ArrayList<>();

		int i = 0;
		while (i < possibleRoots.size()) {
			Fraction f = possibleRoots.get(i);
			Polynomial p = new Polynomial(coeffsTemp);
			List<Fraction> subbed = syntheticSub(f, p);
			
			if (subbed.size() == coeffsTemp.size() - 1) {
				roots.add(f);
				coeffsTemp = subbed;
				i--;
			}
			i++;
		}

		return roots;
	}

	/**
	 * Uses synthetic substitution to divide a given Polynomial by (x - f).
	 * @param p			Polynomial to be divided
	 * @param f			Fraction representing f in (x - f)
	 * @return			a Fraction array representing the divided polynomial
	 */
	public static List<Fraction> syntheticSub(Fraction f, Polynomial p) {
		double temp = p.coeffs[0];
		List<Fraction> divided = new ArrayList<Fraction>();
		divided.add(Fraction.toFraction(p.coeffs[0]));

		for (int i = 1; i < p.coeffs.length; i++) {
			temp *= f.toDecimal();
			temp += p.coeffs[i];
			if (i != p.coeffs.length - 1 || temp != 0)
				divided.add(Fraction.toFraction(temp));
		}

		return divided;
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
	 * @param point		the point to solve the derivative at
	 * @return			the derivative at point
	 */
	public double derivative(int point) {
		double q = 0;

		for (int i = 0; i < coeffs.length - 1; i++)
			q += coeffs[i] * (coeffs.length - 1 - i) * Math.pow(point, coeffs.length - 2 - i);

		return q;
	}

	/**
	 * Makes a new List<Integer> out of an int array. Used in the realRoots() method.
	 * @param array		int[] to turn to List<Integer>
	 * @return			array as a List<Integer>
	 */
	private static List<Fraction> arrayToList(final double[] array) {
		final List<Fraction> l = new ArrayList<Fraction>(array.length);

		for (final double s : array) {
			l.add(Fraction.toFraction(s));
		}
		return (l);
	}
	
	private String disp(double d) {
		if (d == (int)d)
			return String.valueOf((int)d);
		else
			return String.valueOf(d);
	}

	/**
	 * Returns the polynomial in standard polynomial notation.
	 */
	public String toString() {
		if (coeffs.length == 0)
			return "0";

		String s = "";

		if (coeffs.length == 1)
			return disp(coeffs[0]);

		if (coeffs.length == 2) {
			if (coeffs[1] == 0)
				return disp(coeffs[0]) + "x";
			else if (coeffs[1] < 0)
				return disp(coeffs[0]) + "x - " + Math.abs(coeffs[1]);
			else
				return disp(0) + "x + " + disp(1);
		}

		s += disp(coeffs[0]) + "x^" + (coeffs.length - 1);

		for (int i = 1; i < coeffs.length; i++) {
			if (coeffs[i] != 0) {
				if (coeffs[i] < 0)
					s += " - ";
				else
					s += " + ";

				s += disp(Math.abs(coeffs[i]));

				if (i < coeffs.length - 2)
					s += "x^" + (coeffs.length - 1 - i);
				else if (i == coeffs.length - 2)
					s += "x";
			}
		}

		return s;
	}
}
