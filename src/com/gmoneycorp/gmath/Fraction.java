package com.gmoneycorp.gmath;
import java.util.ArrayList;
import java.util.List;

public class Fraction {

	private int numerator;
	private int denominator;
	private boolean isNegative;

	/**
	 * Constructs a Fraction object using integers.
	 * @param numerator		the numerator of the fraction
	 * @param denominator	the denominator of the fraction
	 */
	public Fraction(int numerator, int denominator) {
		this.numerator = Math.abs(numerator);
		this.denominator = Math.abs(denominator);

		if ((numerator < 0 && denominator > 0) || (numerator > 0 && denominator < 0))
			this.isNegative = true;
		else
			this.isNegative = false;
	}

	/**
	 * Constructs a Fraction object using doubles.
	 * @param numerator		the numerator of the fraction
	 * @param denominator	the denominator of the fraction
	 */
	public Fraction(double numerator, double denominator) {
		int numDecimalsNumer;
		int numDecimalsDenom;
		
		if ((int)numerator == numerator)
			numDecimalsNumer = 0;
		else
			numDecimalsNumer = String.valueOf(numerator).length() - 1 - String.valueOf(numerator).indexOf('.');
		
		if ((int)denominator == denominator)
			numDecimalsDenom = 0;
		else
			numDecimalsDenom = String.valueOf(denominator).length() - 1 - String.valueOf(denominator).indexOf('.');

		if (numDecimalsNumer > numDecimalsDenom){
			numerator *= Math.pow(10, numDecimalsNumer);
			denominator *= Math.pow(10, numDecimalsNumer);
		}
		else {
			numerator *= Math.pow(10, numDecimalsDenom);
			denominator *= Math.pow(10, numDecimalsDenom);
		}

		int gcd = Factor.gcd((int)numerator, (int)denominator);
		numerator /= gcd;
		denominator /= gcd;

		this.numerator = Math.abs((int)numerator);
		this.denominator = Math.abs((int)denominator);

		if ((numerator < 0 && denominator > 0) || (numerator > 0 && denominator < 0))
			this.isNegative = true;
		else
			this.isNegative = false;
	}

	/**
	 * Constructor that specifies whether or not the fraction is negative via a separate parameter instead of
	 * the signs of the numerator and denominator.
	 * @param numerator		the numerator of the fraction
	 * @param denominator	the denominator of the fraction
	 * @param isNegative	true of the fraction is negative, otherwise false
	 */
	public Fraction(int numerator, int denominator, boolean isNegative) {
		this.numerator = Math.abs(numerator);
		this.denominator = Math.abs(denominator);
		this.isNegative = isNegative;
	}

	/**
	 * Constructs Fraction object from String in format "numerator/denominator".
	 * @param s				String in format "numerator/denominator"
	 */
	public Fraction(String s) {
		String[] fraction = s.split("/");
		this.numerator = Integer.parseInt(fraction[0]);
		this.denominator = Integer.parseInt(fraction[1]);

		if (this.numerator < 0) {
			this.isNegative = true;
			this.numerator *= -1;
		}
	}

	/**
	 * Adds two Fraction objects.
	 * @param f		first Fraction object
	 * @param g		second Fraction object
	 * @return		a Fraction equal to f + g
	 */
	public static Fraction add(Fraction f, Fraction g) {
		Fraction answer = new Fraction((f.getNumerator() * g.getDenominator()) + (g.getNumerator() * f.getDenominator()), f.getDenominator() * g.getDenominator());
		return answer.simplify();
	}

	/**
	 * Subtracts one Fraction from another.
	 * @param f		first Fraction object
	 * @param g		Fraction to be subtracted from f
	 * @return		a Fraction equal to f - g
	 */
	public static Fraction subtract(Fraction f, Fraction g) {
		Fraction answer = new Fraction((f.getNumerator() * g.getDenominator()) - (g.getNumerator() * f.getDenominator()), f.getDenominator() * g.getDenominator());
		return answer.simplify();
	}

	/**
	 * Multiplies two Fraction objects.
	 * @param f		first Fraction object
	 * @param g		second Fraction object
	 * @return		a Fraction equal to f * g
	 */
	public static Fraction multiply(Fraction f, Fraction g) {
		Fraction answer = new Fraction(f.getNumerator() * g.getNumerator(), f.getDenominator() * g.getDenominator());
		return answer.simplify();
	}

	/**
	 * Divides one Fraction by another.
	 * @param f		Fraction to be divided by g
	 * @param g		second Fraction object 
	 * @return		a Fraction equal to f / g
	 */
	public static Fraction divide(Fraction f, Fraction g) {
		Fraction answer = new Fraction(f.getNumerator() * g.getDenominator(), f.getDenominator() * g.getNumerator());
		return answer.simplify();
	}

	/**
	 * Negates a given Fraction.
	 * @param f		Fraction to be negated
	 * @return		f negated
	 */
	public static Fraction negate(Fraction f) {
		return new Fraction(-1 * f.getNumerator(), f.getDenominator());
	}
	
	/**
	 * Returns the decimal equivalent of a given fraction.
	 * @param s		a String in the format "numerator/denominator"
	 * @return		s in decimal form as a double
	 */
	public static double toDecimal(String s) {
		Fraction f = new Fraction(s);
		double d = (double)f.getNumerator() / (double)f.getDenominator();

		return d;
	}

	/**
	 * Returns the decimal equivalent of a given Fraction object.
	 * @param f		a Fraction object
	 * @return		s in decimal form as a double
	 */
	public static double toDecimal(Fraction f) {
		return (double)f.getNumerator() / (double)f.getDenominator();
	}

	/**
	 * Returns the decimal equivalent of a Fraction
	 * @return		f in decimal form as a double
	 */
	public double toDecimal() {
		return (double)getNumerator() / (double)getDenominator();
	}

	/**
	 * Returns the decimal equivalent of a given fraction, including repeating decimal notation.
	 * @param s		a String in the format "numerator/denominator"
	 * @return		s in decimal form as a String
	 */
	public static String toRepeatingDecimal(String s) {
		String[] fraction = s.split("/");
		int numerator = Integer.parseInt(fraction[0]);
		int denominator = Integer.parseInt(fraction[1]);

		List<Integer> numsDivided = new ArrayList<>();

		int a = numerator;

		int b = a / denominator;
		String decimal = b + ".";

		while (a % denominator != 0) {
			a = (a - (b * denominator)) * 10;
			b = a / denominator;

			if (numsDivided.contains(a)) {
				int repeatIndex = 2 + numsDivided.indexOf(a);
				decimal = decimal.substring(0, repeatIndex) + "(" + decimal.substring(repeatIndex) + ")";
				break;
			}
			numsDivided.add(a);
			decimal += b;
		}

		return decimal;
	}

	/**
	 * Returns the given input in fraction form. This will start getting very slow when processing around
	 * 8 decimals, so be wary of this when implementing the method.
	 * @param num	a double to be converted to a fraction
	 * @return		the fraction form of num
	 */
	public static Fraction toFraction(double d) {
		String s = String.valueOf(d);

		if (s.length() >= 18)
			return null;

		int numDecimals = s.length() - 1 - s.indexOf('.');
		int denom = (int)Math.pow(10, numDecimals);
		int workingNum = (int)(d * denom);
		int gcd = Factor.gcd(workingNum, denom);
		workingNum /= gcd;
		denom /= gcd;

		return new Fraction(workingNum, denom);
	}

	/**
	 * Returns the given input in fraction form. Input represents a repeating decimal and has the notation of
	 * "x.x(yy)" with x representing digits that do not repeat and y representing repeating digits.
	 * @param s		double represented as a String to be converted to a fraction
	 * @return		the fraction form of s, null if s is not in the correct format
	 */
	public static Fraction toFractionRepeating(String s) {
		if (s.length() - s.replace(".", "").length() != 1 ||
				s.length() - s.replace("(", "").length() != 1 ||
				s.length() - s.replace(")", "").length() != 1 ||
				!s.substring(s.length() - 1, s.length()).equals(")"))
			return null;
		
		if (s.substring(0, 1).equals("."))
			s = 0 + s;
		
		int pow1 = s.substring(s.indexOf(".") + 1, s.indexOf(")")).length() - 1;
		int pow2 = s.substring(s.indexOf(".") + 1, s.indexOf("(")).length();
		
		String temp = s.replace(".", "");
		temp = temp.replace("(", "");
		temp = temp.replace(")", "");
		
		int num1 = Integer.parseInt(temp);
		
		temp = s.replace(".", "");
		temp = temp.substring(0, temp.indexOf("("));
		
		int num2 = Integer.parseInt(temp);
		
		return new Fraction(num1 - num2, Math.pow(10, pow1) - Math.pow(10, pow2));
	}

	/**
	 * Returns a simplified version of a given fraction.
	 * @param s		a fraction in the form numerator/denominator
	 * @return		s simplified
	 */
	public static Fraction simplify(String s) {
		String[] fraction = s.split("/");
		double numerator = Double.parseDouble(fraction[0]);
		double denominator = Double.parseDouble(fraction[1]);

		if (numerator == 0)
			return new Fraction(0, 1);

		return new Fraction(numerator, denominator).simplify();
	}

	/**
	 * Returns a simplified version of the Fraction object.
	 * @return		the Fraction object simplified
	 */
	public Fraction simplify() {
		int gcd = Factor.gcd(numerator, denominator);
		int newNumer = numerator / gcd;
		int newDenom = denominator / gcd;
		Fraction g = new Fraction(newNumer, newDenom, isNegative);

		return g;
	}

	public int getNumerator() {
		if (isNegative)
			return -1 * numerator;
		else
			return numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public boolean isNegative() {
		return isNegative;
	}

	public boolean equals(Object obj) {
		if (numerator == ((Fraction) obj).getNumerator() &&
				denominator == ((Fraction) obj).getDenominator() &&
				isNegative == ((Fraction) obj).isNegative()) {
			return true;
		}
		else
			return false;
	}

	public String toString() {
		if (numerator == 0)
			return "0";

		if (isNegative) {
			if (denominator == 1)
				return "-" + (int)numerator;
			else
				return "-" + numerator + "/" + denominator;
		}
		else {
			if (denominator == 1)
				return String.valueOf(numerator);
			else
				return numerator + "/" + denominator;
		}
	}
}
