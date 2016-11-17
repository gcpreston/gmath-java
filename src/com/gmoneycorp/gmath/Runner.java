package com.gmoneycorp.gmath;
import com.gmoneycorp.gmath.geometry.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Runner {

	private static String operation;
	private static String output = "";

	public static String runner(String[] args) {

		
		try {
			operation = args[0].toLowerCase();

			switch (operation) {

			//Calculate information about a specified shape
			case "shape":
				String[] shapes = {"-t", "-r", "-c"};
				String[] functions = {"-a", "-p"};
				
				if (!(checkArgs(shapes, args) && checkArgs(functions, args))) {
					returnUsage();
					return output;
				}
				
				List<String> argsList = Arrays.asList(args);
				
				Shape s = null;
				
				if (argsList.contains("-t")) {
					if (args.length != 6) {
						returnUsage();
						return output;
					}

					s = new Triangle(parseInt(args[3]), parseInt(args[4]), parseInt(args[5]));
				}
				else if (argsList.contains("-r")) {
					if (args.length != 5) {
						returnUsage();
						return output;
					}
						
					s = new Rectangle (parseInt(args[3]), parseInt(args[4]));
				}
				else if (argsList.contains("-c")) {
					if (args.length != 4) {
						returnUsage();
						return output;
					}
					s = new Circle(parseInt(args[3]));
				}
				else {
					returnUsage();
					return output;
				}
				
				if (argsList.contains("-a"))
					output = "Area = " + s.area();
				else if (argsList.contains("-p"))
					output = "Perimeter = " + s.perimeter();
				break;
				
			//Return a list of factors of an integer
			case "factor":
				if (args.length > 2 || parseInt(args[1]) < 0) {
					returnUsage();
					return output;
				}
				else
					output = "Factors of " + args[1] + ": " + Factor.factor(parseInt(args[1]));
				break;

			//Return prime factorization of an integer
			case "primefactor":
				if (args.length > 2 || parseInt(args[1]) < 0) {
					returnUsage();
					return output;
				}
				else {
					List<Integer> primeFactors = Factor.primeFactorize(parseInt(args[1]));
					
					output = "Prime factorization of " + args[1] + ": ";
					for (int i = 0; i < primeFactors.size(); i++) {
						if (i == primeFactors.size() - 1)
							output += primeFactors.get(i);
						else
							output += primeFactors.get(i) + " * ";
					}
				}
				break;
				
			//Return true if an integer is prime, otherwise false
			case "isprime":
				if (args.length > 2 || parseInt(args[1]) < 0) {
					returnUsage();
					return output;
				}
				else {
					if (Factor.isPrime(parseInt(args[1])))
						output = args[1] + " is prime.";
					else
						output = args[1] + " is not prime.";
				}
				break;
				
			//Return the gcf of two positive integers
			case "gcf":
				if (args.length > 3 || parseInt(args[1]) < 0 || parseInt(args[2]) < 0) {
					returnUsage();
					return output;
				}
				else
					output = "GCF of " + args[1] + " and " + args[2] + ": " + Factor.findGCF(parseInt(args[1]), parseInt(args[2]));
				break;
				
			//Return the lcm of two positive integers
			case "lcm":
				if (args.length > 3 || parseInt(args[1]) < 0 || parseInt(args[2]) < 0) {
					returnUsage();
					return output;
				}
				else
					output = "LCM of " + args[1] + " and " + args[2] + ": " + Factor.findLCM(parseInt(args[1]), parseInt(args[2]));
				break;
			
			//Return the fraction form of a number
			case "fraction":
				if (args.length > 2) {
					returnUsage();
					return output;
				}
				else
					if (args[1].length() > 9)
						output = "Please enter a number with 7 decimals or less.";
					else {
						if (args[1].contains("("))
							output = args[1] + " = " + Fraction.toFractionRepeating(args[1]);
						else	
						output = args[1] + " = " + Fraction.toFraction(parseDouble(args[1]));
					}
				break;
				
			//Return the decimal form of a fraction
			case "decimal":
				if (args.length > 2) {
					returnUsage();
					return output;
				}
				else {
					if (String.valueOf(Fraction.toDecimal(args[1])).length() >= 18)
						output = args[1] + " = " + Fraction.toRepeatingDecimal(args[1]);
					else
						output = args[1] + " = " + Fraction.toDecimal(args[1]);
				}
				break;
				
			//Return the simplified version of the given fraction
			case "simplify":
				if (args.length > 2) {
					returnUsage();
					return output;
				}
				else
					output = args[1] + " = " + Fraction.simplify(args[1]);
				break;
				
			//Return the simplified version of the given radical
			case "simplifyrad":
				if (args.length > 2) {
					returnUsage();
					return output;
				}
				else {
					int[] rad = Factor.simplifyRadical(parseInt(args[1]));
					output = "sqrt(" + args[1] + ")";
					if (rad[0] == 1)
						output += " cannot be simplified.";
					else if (rad[1] == 1)
						output += " = " + rad[0];
					else
						output += " = " + rad[0] + "sqrt(" + rad[1] + ")";
				}
				break;
				
			//Return the x-intercept of a linear function
			case "x-int":
				if (args.length > 4) {
					returnUsage();
					return output;
				}
				else {
					Polynomial func = new Polynomial(parseDouble(args[1]), parseDouble(args[2]));
					output = "The x-intercept of " + func + " is " + func.findXInt();	
				}
				break;
				
			//Calculate function from of two or three coordinates
			case "calcfunction":
				if (args.length > 4) {
					returnUsage();
					return output;
				}
				else {
					if (args.length == 3) {
						if (args[1].charAt(1) == args[2].charAt(1))
							output = args[1] + " and " + args[2] + " are both on the line x = " + args[1].substring(1, 2);
						else {
							Polynomial func = new Polynomial(args[1], args[2]);
							output = args[1] + " and " + args[2] + " are both on the line " + func;
						}
					}
					else {
						if (args[1].charAt(1) == args[2].charAt(1) && args[2].charAt(1) == args[3].charAt(1))
							output = args[1] + ", " + args[2] + " and " + args[3] + " are on the line x = " + args[1].substring(1, 2);
						else {
							Polynomial func = new Polynomial(args[1], args[2], args[3]);
							output = args[1] + ", " + args[2] + " and " + args[3] + " are on the parabola " + func;
						}
					}
				}
				break;
				
			//Calculate the point of intersection of two lines
			case "intersect":
				if (args.length > 5) {
					returnUsage();
					return output;
				}
				else {
					Polynomial line1 = new Polynomial(parseDouble(args[1]), parseDouble(args[2]));
					Polynomial line2 = new Polynomial(parseDouble(args[3]), parseDouble(args[4]));
					
					if (line1.getSlope() == line2.getSlope()) {
						if (line1.getYInt() == line2.getYInt())
							output = "The lines are equal.";
						else
							output = "The lines are parallel.";
					}
					else {
						double[] intersect = Polynomial.findIntersect(line1, line2);
						output = line1 + " and " + line2 + " intersect at (" + intersect[0] + ", " + intersect[1] + ").";
					}
				}
				
			//Factor quadratic and return the answer(s)
			case "quadratic":
				if (args.length > 4) {
					returnUsage();
					return output;
				}
				else {
					double[] coeffs = {parseDouble(args[1]), parseDouble(args[2]), parseDouble(args[3])};
					Polynomial q = new Polynomial(coeffs);
				
					output = q + " = ";

					if (q.factorQuadratic() == null)
						output += "Not factorable.\n";
					else {
						output += q.factorToString() + "\n";
					}

					double[] solved = q.solveQuadratic();
					if (q.solveDiscriminant() > 0) {
						output += "Two real solutions:";
						output += "x = " + solved[0] + ", " + solved[1];
					}
					else if (q.solveDiscriminant() == 0) {
						output += "One real solution (duplicate solutions):";
						output += "x = " + solved[0];
					}
					else if (q.solveDiscriminant() < 0)
						output += "Two imaginary solutions.";
				}
				break;

			//Return discriminant of quadratic function
			case "discrim":
				if (args.length > 4) {
					returnUsage();
					return output;
				}
				else {
					double[] coeffs = {parseInt(args[1]), parseInt(args[2]), parseInt(args[3])};
					Polynomial q = new Polynomial(coeffs);
					
					output = "The discriminant of " + q + " is " + q.solveDiscriminant();
				}
				break;
				
			//Return the possible roots of a polynomial (rational root formula)
			case "rationalroot":
				double[] coeffs = new double[args.length - 1];
				
				for (int i = 1; i < args.length; i++)
					coeffs[i - 1] = parseInt(args[i]);
				
				Polynomial p = new Polynomial(coeffs);
				List<Fraction> possibleRoots = new ArrayList<>();
				List<Fraction> realRoots = new ArrayList<>();
				
				for (Fraction f : p.possibleRoots())
					possibleRoots.add(f);
				
				for (Fraction f : p.rationalRoots())
					realRoots.add(f);
				
				output = "Possible roots:\n+/- ";
				for (int i = 0; i < possibleRoots.size(); i++) {
					if (!possibleRoots.get(i).isNegative()) {
						if (i == possibleRoots.size() - 2)
							output += possibleRoots.get(i) + "\n";
						else
							output += possibleRoots.get(i) + ", ";
					}
				}
				
				output += "Real roots:";
				for (int i = 0; i < realRoots.size(); i++) {
					if (i == realRoots.size() - 1)
						output += realRoots.get(i);
					else
						output += realRoots.get(i) + ", ";
				}
				break;
				
			//Returns factored version of a polynomial
			case "factorpoly":
				double[] coeffs5 = new double[args.length - 1];
				
				for (int i = 1; i < args.length; i++)
					coeffs5[i - 1] = parseDouble(args[i]);
				
				Polynomial r = new Polynomial(coeffs5);
				List<List<Fraction>> factored = r.factorPolynomial();
				
				output = r + " = ";
				for(List<Fraction> l : factored)
					output += "(" + new Polynomial(l) + ")\n";
				break;
				
			//Return a polynomial divided by a value
			case "syntheticdiv":
				double[] coeffs4 = new double[args.length - 2];
				
				for (int i = 1; i < args.length - 1; i++)
					coeffs4[i - 1] = parseDouble(args[i]);
				
				Polynomial q = new Polynomial(coeffs4);
				
				Fraction f;
				if (args[args.length - 1].contains("/")) {
					f = new Fraction(args[args.length - 1]);
				}
				else {
					f = new Fraction(parseDouble(args[args.length - 1]), 1);
				}
				List<Fraction> divided = Polynomial.syntheticSub(f, q);
				
				output = new Polynomial(divided).toString();
				break;
				
			//Return the integral of a polynomial over a certain interval
			case "integrate":
				int[] interval = {parseInt(args[args.length - 2]), parseInt(args[args.length - 1])};
				double[] coeffs2 = new double[args.length - 3];
				for (int i = 1; i < args.length - 2; i++)
					coeffs2[i - 1] = parseDouble(args[i]);
				
				Polynomial p2 = new Polynomial(coeffs2);
				
				output = "The integral of " + p2 + " over (" + interval[0] + ", " + interval[1] + ") is " + p2.integral(interval[0], interval[1]);
				break;
				
			//Return the derivative of a polynomial at a point
			case "derive":
				int point = parseInt(args[args.length - 1]);
				double[] coeffs3 = new double[args.length - 2];
				for (int i = 1; i < args.length - 1; i++)
					coeffs3[i - 1] = parseDouble(args[i]);
				
				Polynomial p3 = new Polynomial(coeffs3);
				
				output = "The derivative of " + p3 + " at x = " + point + " is " + p3.derivative(point);
				break;
				
			//Return usage
			default:
				returnUsage();
				break;
			}
		} catch (NegativeArraySizeException e) {
			returnUsage();
		} catch (ArrayIndexOutOfBoundsException e) {
			returnUsage();
		} catch (NumberFormatException e) {
			returnUsage();
		} catch (PolynomialPowerException e) {
			returnUsage();
		}
		
		return output;
	}

	private static void returnUsage() {
		String usage = "Usage: java -jar gmath.jar <operation> [input]\n\nwhere operations include:\n\t"
				+ "shape\t\tperform a specified calcualtion for a specfied shape\n\t"
				+ "factor\t\treturn a list of factors of a positive integer\n\t"
				+ "primefactor\treturn the prime factorization of a positive integer\n\t"
				+ "isprime\t\treturn whether or not a positive integer is prime\n\t"
				+ "gcf\t\treturn the GCF of two positive integers\n\t"
				+ "lcm\t\treturn the LCM of two positive integers\n\t"
				+ "fraction\treturn the fraction form of a number\n\t"
				+ "decimal\t\treturn the decimal form of a fraction\n\t"
				+ "simplify\treturn the simplified form of a fraction\n\t"
				+ "simplifyrad\treturn the simplified form of a radical\n\t"
				+ "x-int\t\treturn the x-intercept of a linear function\n\t"
				+ "calcfunction\treturn the formula of a function given 2-3 coordinates\n\t"
				+ "intersect\treturn the point of intersection of two lines\n\t"
				+ "quadratic\tfactor quadratic and return the answer(s)\n\t"
				+ "discrim\t\treturn the discriminant of a quadratic\n\t"
				+ "rationalroot\treturn the possible rational roots of a polynomial\n\t"
				+ "factorpoly\treturn the factored version of a polynomial\n\t"
				+ "syntheticdiv\treturn a polynomial divided by a value\n\t"
				+ "integrate\treturn the integral of a polynomial over an interval\n\t"
				+ "derive\t\treturn the derivative of a polynomial at a point";

		if (operation == null)
			output = usage;
		else {
			switch (operation) {
			case "-v":
			case "--version":
				output = "GMath version 0.1.2_117";
				break;
				
			case "shape":
				output = "Usage: java -jar gmath.jar shape <shape> <function> [input]\n\n"
						+ "where shapes include\n\t"
						+ "-t (triangle)\tinput is three side lengths\n\t"
						+ "-r (rectangle)\tinput is length and width\n\t"
						+ "-c (circle)\tinput is radius\n\n"
						+ "where functions include\n\t"
						+ "-a (area)\n\t"
						+ "-p (perimeter)";
				break;
				
			case "factor":
				output = "Usage: java -jar gmath.jar factor [input]\n\n"
						+ "where input is a positive integer.";
				break;
	
			case "primefactor":
				output = "Usage: java -jar gmath.jar primefactor [input]\n\n"
						+ "where input is a positive integer.";
				break;
				
			case "isprime":
				output = "Usage: java -jar gmath.jar isprime [input]\n\n"
						+ "where input is a positive integer.";
				break;
				
			case "gcf":
				output = "Usage: java -jar gmath.jar gcf [input]\n\n"
						+ "where input is two positive integers.";
				break;
	
			case "lcm":
				output = "Usage: java -jar gmath.jar lcm [input]\n\n"
						+ "where input is two positive integers.";
				break;
				
			case "fraction":
				output = "Usage: java -jar gmath.jar fraction [input]\n\n"
						+ "where input is one number. For a repeating decimal, put parenthesis\n"
						+ "around the repeating section.\n\n"
						+ "EXAMPLE:\n"
						+ "0.125 = 1/8\n"
						+ "0.1(6) = 1/6";
				break;
	
			case "decimal":
				output = "Usage: java -jar gmath.jar decimal [input]\n\n"
						+ "where input is a fraction in the form numerator/denominator.";
				break;
				
			case "simplify":
				output = "Usage: java -jar gmath.jar simplfy [input]\n\n"
						+ "where input is a fraction in the form numerator/denominator.";
				
			case "simplifyrad":
				output = "Usage: java -jar gmath.jar simplifyrad [input]\n\n"
						+ "where input is n in sqrt(n).";
				break;
				
			case "x-int":
				output = "Usage: java -jar gmath.jar x-int [input]\n\n"
						+ "where input is two numbers m and b, from the linear function f(x) = mx + b.";
				break;
				
			case "calcfunction":
				output = "Usage: java -jar gmath.jar calcfunction [input]\n\n"
						+ "where input is two coordinates in the format (x1,y1) (x2,y2) for two points\n"
						+ "or (x1,y1) (x2,y2) (x3,y3) for three points.";
				break;
				
			case "intersect":
				output = "Usage: java -jar gmath.jar intersect [input]\n\n"
						+ "where input is the coefficients of two lines.";
				break;
				
			case "quadratic":
				output = "Usage: java -jar gmath.jar quadratic [input]\n\n"
						+ "where input is three integers a, b, and c, from the quadratic\n"
						+ "f(x) = ax^2 + bx + c.";
				break;
	
			case "discrim":
				output = "Usage: java -jar gmath.jar discrim [input]\n\n"
						+ "where input is three integers a, b, and c, from the quadratic\n"
						+ "f(x) = ax^2 + bx + c.";
				break;
				
			case "rationalroot":
				output = "Usage: java -jar gmath.jar rationalroot [input]\n\n"
						+ "where input is the coefficients of a polynomial function.";
				break;
				
			case "factorpoly":
				output = "Usage: java -jar gmath.jar factorpoly [input]\n\n"
						+ "where input is the coefficients of a polynomial function.";
				break;
				
			case "syntheticdiv":
				output = "Usage: java -jar gmath.jar syntheticdiv [polynomial] [divisor]\n\n"
						+ "where polynomial are the coefficients of the polynomial to be divided and\n"
						+ "divisor is the factor to divide the polynomial by.\n"
						+ "Executes (polynomial) / (x - divisor) using synthetic division.";
				break;
				
			case "integrate":
				output = "Usage: java -jar gmath.jar integrate [coefficients] [interval]\n\n"
						+ "where coefficients are any number of integers representing the coefficients of a"
						+ "polynomial and interval is two integers representing the start and end of the\n"
						+ "interval to integrate over, respecitvely.";
				break;
				
			case "derive":
				output = "Usage: java -jar gmath.jar derive [coefficients] [point]\n\n"
						+ "where coefficients are any number of integers representing the coefficients of a"
						+ "polynomial and point is the x value to derive at.";
				break;
			}
		}
	}

	private static int parseInt(String s) {
		try {
			if (s == null) {
				returnUsage();
				return 0;
			} else {
				return Integer.parseInt(s);
			}
		} catch (final NumberFormatException ex) {
			returnUsage();
			return 0;
		}
	}

	private static double parseDouble(String s) {
		try {
			if (s == null) {
				returnUsage();
				return 0;
			} else {
				return Double.parseDouble(s);
			}
		} catch (final NumberFormatException ex) {
			returnUsage();
			return 0;
		}
	}
	
	private static boolean checkArgs(String[] arr1, String[] arr2) {
		int count = 0;
		
		for (String s : arr1) {
			for (String t : arr2) {
				if (s.equals(t))
					count++;
				if (count > 1)
					return false;
			}
		}
		return true;
	}
}
