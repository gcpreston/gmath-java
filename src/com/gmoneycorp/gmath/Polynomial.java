package com.gmoneycorp.gmath;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author Graham Preston
 */
public class Polynomial {

	private double[] coeffs;
	private Fraction mFraction;
	private Fraction bFraction;

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
	
	/**
	 * Constructor using List<Fraction> instead of an array.
	 * @param coeffs	coefficients of the polynomial
	 */
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
	
	public Polynomial(double m, double b) {
		this.coeffs = new double[2];
		this.coeffs[0] = m;
		this.coeffs[1] = b;
		this.mFraction = Fraction.toFraction(m).simplify();
		this.bFraction = Fraction.toFraction(b).simplify();
	}

	public Polynomial(String coord1, String coord2) {
		double x1, x2, y1, y2;
		coord1 = coord1.replace("(", "");
		coord1 = coord1.replace(")", "");
		coord2 = coord2.replace("(", "");
		coord2 = coord2.replace(")", "");

		x1 = Double.parseDouble(coord1.split(",")[0]);
		x2 = Double.parseDouble(coord2.split(",")[0]);
		y1 = Double.parseDouble(coord1.split(",")[1]);
		y2 = Double.parseDouble(coord2.split(",")[1]);

		this.coeffs = new double[2];
		
		this.coeffs[0] = (y2 - y1) / (x2 - x1);
		this.coeffs[1] = y1 - (this.coeffs[0] * x1);
		this.mFraction = new Fraction(y2 - y1, x2 - x1).simplify();
		
		Fraction y1Fraction = Fraction.multiply(mFraction, new Fraction(x1, 1));
		this.bFraction = Fraction.subtract(new Fraction(y1, 1), y1Fraction).simplify();
	}
	
	public Polynomial(String coord1, String coord2, String coord3) {
		double x1, x2, x3, y1, y2, y3;
		coord1 = coord1.replace("(", "");
		coord1 = coord1.replace(")", "");
		coord2 = coord2.replace("(", "");
		coord2 = coord2.replace(")", "");
		coord3 = coord3.replace("(", "");
		coord3 = coord3.replace(")", "");

		x1 = Double.parseDouble(coord1.split(",")[0]);
		x2 = Double.parseDouble(coord2.split(",")[0]);
		x3 = Double.parseDouble(coord3.split(",")[0]);
		y1 = Double.parseDouble(coord1.split(",")[1]);
		y2 = Double.parseDouble(coord2.split(",")[1]);
		y3 = Double.parseDouble(coord3.split(",")[1]);

		this.coeffs = new double[3];
		this.coeffs[0] = ((y1 * (x2 - x3)) + (y2 * (x3 - x1)) + (y3 * (x1 - x2))) / ((x1 - x2) * (x1 - x3) * (x2 - x3));
		this.coeffs[1] = ((y1 * ((x3 * x3) - (x2 * x2))) + (y2 * ((x1 * x1) - (x3 * x3))) + (y3 * ((x2 * x2) - (x1 * x1)))) / ((x1 - x2) * (x1 - x3) * (x2 - x3));
		this.coeffs[2] = y1 - (x1 * x1 * coeffs[0]) - (x1 * coeffs[1]);
	}
	
	//LINEAR FUNCTIONS
	
	public double findXInt() throws PolynomialPowerException {
		if (coeffs.length != 2)
			throw new PolynomialPowerException("Function power must be 1.");
		return (-1 * coeffs[1]) / coeffs[0];
	}

	public static double[] findIntersect(Polynomial l1, Polynomial l2) {
		double[] intersect = new double[2];
		
		try {
			intersect[0] = (l2.getYInt() - l1.getYInt()) / (l1.getSlope() - l2.getSlope());
			intersect[1] = (l1.getSlope() * intersect[0]) + l1.getYInt();
		} catch (PolynomialPowerException e) {
			e.printStackTrace();
		} catch (ArithmeticException e) {
			return null;
		}
		
		return intersect;
	}
	
	public double getSlope() throws PolynomialPowerException {
		if (coeffs.length != 2)
			throw new PolynomialPowerException("Function power must be 1.");
		
		return coeffs[0];
	}

	public double getYInt() throws PolynomialPowerException{
		if (coeffs.length != 2)
			throw new PolynomialPowerException("Function power must be 1.");
		
		return coeffs[1];
	}
	
	public Fraction getMFraction() throws PolynomialPowerException {
		if (coeffs.length != 2)
			throw new PolynomialPowerException("Function power must be 1.");
		
		return mFraction;
	}
	
	public Fraction getBFraction() throws PolynomialPowerException {
		if (coeffs.length != 2)
			throw new PolynomialPowerException("Function power must be 1.");
		
		return bFraction;
	}

	//QUADRATIC FUNCTIONS
	
	/**
	 * Returns the two factors of c that add up to b.
	 * @return		an integer array with containing two factors of c that add
	 * 				up to b
	 */
	private int[] findWorkingFactors() throws PolynomialPowerException {
		if (coeffs.length != 3)
			throw new PolynomialPowerException("Function power must be 2.");
		
		List<Integer> factors = Factor.factor((int)coeffs[0]*(int)coeffs[2]);

		int[] workingFactors = new int[2];

		if (coeffs[2] > 0) {
			for (int i = 0; i < factors.size(); i++) {
				if (factors.get(i) + factors.get(factors.size() - 1 - i) == coeffs[1]) {
					workingFactors[0] = factors.get(i);
					workingFactors[1] = factors.get(factors.size() - 1 - i);
					return workingFactors;
				}
			}

			for (int i = 0; i < factors.size(); i++) {
				if ((factors.get(i) * -1) + (factors.get(factors.size() - 1 - i) * -1) == coeffs[1]) {
					workingFactors[0] = (factors.get(i) * -1);
					workingFactors[1] = (factors.get(factors.size() - 1 - i) * -1);
					return workingFactors;
				}
			}
		}
		else {
			for (int i = 0; i < factors.size(); i++) {
				if (factors.get(i) + (factors.get(factors.size() - 1 - i) * -1) == coeffs[1]) {
					workingFactors[0] = factors.get(i);
					workingFactors[1] = (factors.get(factors.size() - 1 - i) * -1);
					return workingFactors;
				}
			}
		}

		return null;
	}

	/**
	 * Returns the factored version of the quadratic in the form of an integer array
	 * with four indexes holding the coefficients. To invoke this function, the values
	 * of a, b, and c, must be integers.
	 * @return		an integer array with the coefficients of the factored equation
	 * 				as its four indexes
	 * @throws InputMismatchException
	 */
	public int[] factorQuadratic() throws PolynomialPowerException {
		if (coeffs.length != 3)
			throw new PolynomialPowerException("Function power must be 2.");
		
		if (!(coeffs[0] == Math.floor(coeffs[0])) ||
			!(coeffs[1] == Math.floor(coeffs[1])) ||
			!(coeffs[2] == Math.floor(coeffs[2]))) {
			return null;
		}

		int[] workingFactors = findWorkingFactors();

		if (workingFactors == null)
			return null;

		int group1GCF = Factor.gcd((int)coeffs[0], workingFactors[0]);
		int group2GCF = Factor.gcd(workingFactors[1], (int)coeffs[2]);

		if ((coeffs[1]/group1GCF) != (workingFactors[1]/group2GCF) || (workingFactors[0]/group1GCF) != (coeffs[2]/group2GCF)) {
			group2GCF *= -1;
		}
		int[] finalEquation = {group1GCF, group2GCF, ((int)coeffs[0]/group1GCF), (workingFactors[0]/group1GCF)};

		return finalEquation;
	}

	/**
	 * Returns the factored form of the quadratic as a String.
	 * @return		a String containing the factored form of the given Quadratic
	 */
	public String factorToString() throws PolynomialPowerException {
		if (coeffs.length != 3)
			throw new PolynomialPowerException("Function power must be 2.");
		
		int[] factoredEquation = factorQuadratic();
		int totalGCF = 1;
		
		int GCF1 = Factor.gcd(factoredEquation[0], factoredEquation[1]);
		totalGCF *= GCF1;
		factoredEquation[0] /= GCF1;
		factoredEquation[1] /= GCF1;
		
		int GCF2 = Factor.gcd(factoredEquation[2], factoredEquation[3]);
		totalGCF *= GCF2;
		factoredEquation[2] /= GCF2;
		factoredEquation[3] /= GCF2;
		
		String factoredForm = "";

		if (totalGCF != 1)
			factoredForm += String.valueOf(totalGCF);
		
		factoredForm += "(" + factoredEquation[0] + "x ";
		if (factoredEquation[1] > 0)
			factoredForm += "+ " + factoredEquation[1] + ")(" + factoredEquation[2] + "x ";
		else
			factoredForm += "- " + factoredEquation[1] * -1 + ")(" + factoredEquation[2] + "x ";
		if (factoredEquation[3] > 0)
			factoredForm += "+ " + factoredEquation[3] + ")";
		else
			factoredForm += "- " + factoredEquation[3] * -1 + ")";

		return factoredForm;
	}

	/**
	 * Returns the possible values of x using the quadratic formula.
	 * @return		a double array containing the two possible values of x. Any
	 * 				imaginary x values are entered as NaN
	 */
	public double[] solveQuadratic() throws PolynomialPowerException {
		if (coeffs.length != 3)
			throw new PolynomialPowerException("Function power must be 2.");
		
		double [] answers = new double [2];

		answers[0] = ((-1*coeffs[1]) + Math.sqrt(Math.pow(coeffs[1], 2) - (4 * coeffs[0] * coeffs[2]))) / (2 * coeffs[0]);
		answers[1] = ((-1*coeffs[1]) - Math.sqrt(Math.pow(coeffs[1], 2) - (4 * coeffs[0] * coeffs[2]))) / (2 * coeffs[0]);

		return answers;
	}
	
	/**
	 * Returns the discriminant using b^2 - 4ac.
	 * @return		a double containing the value of the discriminant
	 */
	public double solveDiscriminant() throws PolynomialPowerException {
		if (coeffs.length != 3)
			throw new PolynomialPowerException("Function power must be 2.");
		
		return Math.pow(coeffs[1], 2) - (4 * coeffs[0] * coeffs[2]);
	}

	/**
	 * Returns the value of a
	 * @return		the value of a
	 */
	public double getA() throws PolynomialPowerException {
		if (coeffs.length != 3)
			throw new PolynomialPowerException("Function power must be 2.");
		
		return coeffs[0];
	}

	/**
	 * Returns the value of b
	 * @return		the value of b
	 */
	public double getB() throws PolynomialPowerException {
		if (coeffs.length != 3)
			throw new PolynomialPowerException("Function power must be 2.");
		
		return coeffs[1];
	}

	/**
	 * Returns the value of c
	 * @return		the value of c
	 */
	public double getC() throws PolynomialPowerException {
		if (coeffs.length != 3)
			throw new PolynomialPowerException("Function power must be 2.");
		
		return coeffs[2];
	}
	
	//POLYNOMIAL FUNCTIONS
	
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
	public List<Fraction> rationalRoots() {
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
	 * Uses synthetic substitution to factor a Polynomial object.
	 * @return			a 2D List that represents multiple polynomials being multiplied
	 */
	public List<List<Fraction>> factorPolynomial() {
		//Can probably be cleaned up
		List<Fraction> coeffsTemp = arrayToList(coeffs);
		List<Fraction> possibleRoots = possibleRoots();
		List<List<Fraction>> factored = new ArrayList<>();

		int i = 0;
		while (i < possibleRoots.size()) {
			Fraction f = possibleRoots.get(i);
			Polynomial p = new Polynomial(coeffsTemp);
			List<Fraction> subbed = syntheticSub(f, p);
			
			if (subbed.size() == coeffsTemp.size() - 1) {
				factored.add(new ArrayList<>());
				factored.get(factored.size() - 1).add(new Fraction(f.getDenominator(), 1));
				factored.get(factored.size() - 1).add(Fraction.negate(Fraction.multiply(f, new Fraction(f.getDenominator(), 1))));
				
				for (int j = 0; j < subbed.size(); j++)
					 subbed.set(j, Fraction.divide(subbed.get(j), new Fraction(f.getDenominator(), 1)));
				coeffsTemp = subbed;
				i--;
			}
			i++;
		}
		factored.add(coeffsTemp);

		return factored;
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
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		
		if (coeffs.length == 0)
			return "0";

		String s = "";

		if (coeffs.length == 1)
			return disp(coeffs[0]);

		if (coeffs.length == 2) {
			String eq = "y = ";

			if (coeffs[0] == Math.floor(coeffs[0]))
				eq += (int)coeffs[0] + "x ";
			else
				eq += "(" + mFraction + ")" + "x ";
			if (coeffs[1] < 0) {
				if (coeffs[1] == Math.floor(coeffs[1]))
					eq += "- " + -1 * (int)coeffs[1];
				else
					eq += "- " + bFraction.toString().substring(1);
			}
			else {
				eq += "+ ";
				if (coeffs[1] == Math.floor(coeffs[1]))
					eq += (int)coeffs[1];
				else
					eq += bFraction;
			}

			return eq;
		}

		s += df.format(coeffs[0]) + "x^" + (coeffs.length - 1);

		for (int i = 1; i < coeffs.length; i++) {
			if (coeffs[i] != 0) {
				if (coeffs[i] < 0)
					s += " - ";
				else
					s += " + ";

				s += df.format(Math.abs(coeffs[i]));

				if (i < coeffs.length - 2)
					s += "x^" + (coeffs.length - 1 - i);
				else if (i == coeffs.length - 2)
					s += "x";
			}
		}

		return s;
	}
}
