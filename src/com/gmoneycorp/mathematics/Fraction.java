package com.gmoneycorp.mathematics;

import java.util.ArrayList;
import java.util.List;

public class Fraction {

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
		String repeat = "";

		boolean foundRepeat = false;

		while (a % denominator != 0) {
			a = (a - (b * denominator)) * 10;
			b = a / denominator;

			if (numsDivided.contains(a)) {
				foundRepeat = true;
				break;
			}
			numsDivided.add(a);
			repeat += b;
			decimal += b;
		} 

		if (foundRepeat) {
			int index = decimal.indexOf(repeat);
			decimal = decimal.substring(0, index) + "(" + decimal.substring(index) + ")";
		}

		return decimal;
	}
	
	//KNOWM BUG: Gets stuck in loop when working with repeating decimals
	/**
	 * Returns the given input in fraction form.
	 * @param num	a double to be converted to a fraction
	 * @return		a String containing the fraction form of num
	 */
	public static String toFraction(double d) {
		String s = String.valueOf(d);
		int numDecimals = s.length() - 1 - s.indexOf('.');

		if (d == Math.floor(d) || numDecimals >= 16)
			return s;

		int denom = (int)Math.pow(10, numDecimals);
		int workingNum = (int)(d * denom);
		int GCF = Factor.findGCF(workingNum, denom);
		workingNum /= GCF;
		denom /= GCF;

		return workingNum + "/" + denom;
	}

}
