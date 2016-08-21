package com.gmoneycorp.mathematics;

import java.util.ArrayList;
import java.util.List;

public class Fraction {

	private int numerator;
	private int denominator;
	
	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	public static Fraction add(Fraction f, Fraction g) {
		
	}
	
	/**
	 * Returns the decimal equivalent of a given fraction.
	 * @param s		a String in the format "numerator/denominator"
	 * @return		s in decimal form as a double
	 */
	public static double toDecimal(String s) {
		String[] fraction = s.split("/");
		int numerator = Integer.parseInt(fraction[0]);
		int denominator = Integer.parseInt(fraction[1]);
		double d = (double)numerator / (double)denominator;

		return d;
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
	public static String toFraction(double d) {
		String s = String.valueOf(d);

		if (d == Math.floor(d) || s.length() >= 18)
			return s;

		int numDecimals = s.length() - 1 - s.indexOf('.');
		int denom = (int)Math.pow(10, numDecimals);
		int workingNum = (int)(d * denom);
		int GCF = Factor.findGCF(workingNum, denom);
		workingNum /= GCF;
		denom /= GCF;

		return workingNum + "/" + denom;
	}
	
	/**
	 * Returns a simplified version of a given fraction.
	 * @param s		a fraction in the form numerator/denominator
	 * @return		s simplified
	 */
	public static String simplifyFraction(String s) {
		String[] fraction = s.split("/");
		double numerator = Double.parseDouble(fraction[0]);
		double denominator = Double.parseDouble(fraction[1]);
		
		if (numerator == 0)
			return "0";
		
		String numerStr = String.valueOf(numerator);
		String denomStr = String.valueOf(denominator);
		int numDecimalsNumer = numerStr.length() - 1 - numerStr.indexOf('.');
		int numDecimalsDenom = denomStr.length() - 1 - denomStr.indexOf('.');
		
		if (numDecimalsNumer > numDecimalsDenom){
			numerator *= Math.pow(10, numDecimalsNumer);
			denominator *= Math.pow(10, numDecimalsNumer);
		}
		else {
			numerator *= Math.pow(10, numDecimalsDenom);
			denominator *= Math.pow(10, numDecimalsDenom);
		}
		
		int GCF = Factor.findGCF((int)numerator, (int)denominator);
		numerator /= GCF;
		denominator /= GCF;
		
		if ((denominator < 0 && numerator > 0) || (denominator > 0 && numerator < 0)) {
			denominator = Math.abs(denominator);
			numerator = Math.abs(numerator);
			return "- " + (int)numerator + "/" + (int)denominator;
		}
		else {
			denominator = Math.abs(denominator);
			numerator = Math.abs(numerator);
			return (int)numerator + "/" + (int)denominator;
		}
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public int getDenominator() {
		return denominator;
	}

}
