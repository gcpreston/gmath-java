package com.gmoneycorp.gmath;
import java.util.List;
import java.util.ArrayList;

public class Runner {

	private static String operation;

	public static void main(String[] args) {

		try {
			String op = args[0].toLowerCase();
			operation = op;

			switch (operation) {

			//Return a list of factors of an integer
			case "factor":
				if (args.length > 2 || parseInt(args[1]) < 0)
					returnUsage();
				else
					System.out.println("Factors of " + args[1] + ": " + Factor.factor(parseInt(args[1])));
				break;

			//Return prime factorization of an integer
			case "primefactor":
				if (args.length > 2 || parseInt(args[1]) < 0)
					returnUsage();
				else {
					List<Integer> primeFactors = Factor.primeFactorize(parseInt(args[1]));
					
					System.out.print("Prime factorization of " + args[1] + ": ");
					for (int i = 0; i < primeFactors.size(); i++) {
						if (i == primeFactors.size() - 1)
							System.out.println(primeFactors.get(i));
						else
							System.out.print(primeFactors.get(i) + " * ");
					}
				}
				break;
				
			//Return true if an integer is prime, otherwise false
			case "isprime":
				if (args.length > 2 || parseInt(args[1]) < 0)
					returnUsage();
				else {
					if (Factor.isPrime(parseInt(args[1])))
						System.out.println(args[1] + " is prime.");
					else
						System.out.println(args[1] + " is not prime.");
				}
				break;
				
			//Return the gcf of two positive integers
			case "gcf":
				if (args.length > 3 || parseInt(args[1]) < 0 || parseInt(args[2]) < 0)
					returnUsage();
				else
					System.out.println("GCF of " + args[1] + " and " + args[2] + ": " + Factor.findGCF(parseInt(args[1]), parseInt(args[2])));
				break;
				
			//Return the lcm of two positive integers
			case "lcm":
				if (args.length > 3 || parseInt(args[1]) < 0 || parseInt(args[2]) < 0)
					returnUsage();
				else
					System.out.println("LCM of " + args[1] + " and " + args[2] + ": " + Factor.findLCM(parseInt(args[1]), parseInt(args[2])));
				break;
			
			//Return the fraction form of a number
			case "fraction":
				if (args.length > 2)
					returnUsage();
				else
					if (args[1].length() > 9)
						System.out.println("Please enter a number with 7 decimals or less.");
					else
						System.out.println(args[1] + " = " + Fraction.toFraction(parseDouble(args[1])));
				break;
				
			//Return the decimal form of a fraction
			case "decimal":
				if (args.length > 2)
					returnUsage();
				else {
					if (String.valueOf(Fraction.toDecimal(args[1])).length() == 18)
						System.out.println(args[1] + " = " + Fraction.toRepeatingDecimal(args[1]));
					else
						System.out.println(args[1] + " = " + Fraction.toDecimal(args[1]));
				}
				break;
				
			// Return the x-intercept of a linear function
			case "x-int":
				if (args.length > 3)
					returnUsage();
				else {
					LinearFunction func = new LinearFunction(parseDouble(args[1]), parseDouble(args[2]));
					System.out.println("The x-intercept of " + func + " is " + func.findXInt());
				}
				break;
				
			//Calculate linear function from of two coordinates
			case "calcline":
				if (args.length > 3)
					returnUsage();
				else {
					LinearFunction func = new LinearFunction(args[1], args[2]);
					System.out.println(args[1] + " and " + args[2] + " are both on the line " + func);
				}
				break;
				
			//Factor quadratic and return the answer(s)
			case "quadratic":
				if (args.length > 4)
					returnUsage();
				else {
					Quadratic q = new Quadratic(parseDouble(args[1]), parseDouble(args[2]), parseInt(args[3]));
					System.out.print(q + " = ");

					if (q.factorQuadratic() == null)
						System.out.println("Not factorable.\n");
					else
						System.out.println(q.factorToString() + "\n");

					if (q.solveDiscriminant() > 0) {
						System.out.println("Two real solutions:");
						System.out.println("x = " + q.solveQuadratic()[0] + ", " + q.solveQuadratic()[1]);
					}
					else if (q.solveDiscriminant() == 0) {
						System.out.println("One real solution (duplicate solutions):");
						System.out.println("x = " + q.solveQuadratic()[0]);
					}
					else if (q.solveDiscriminant() < 0)
						System.out.println("Two imaginary solutions.");
				}
				break;

			//Return discriminant of quadratic function
			case "discrim":
				if (args.length > 4)
					returnUsage();
				else {
					Quadratic q = new Quadratic(parseInt(args[1]), parseInt(args[2]), parseInt(args[3]));
					System.out.println("The discriminant of " + q + " is " + q.solveDiscriminant());
				}
				break;
				
			//Return the possible roots of a polynomial (rational root formula)
			case "rationalroot":
				double[] coeffs = new double[args.length - 1];
				
				int j = 0;
				for (int i = 1; i < args.length; i++) {
					coeffs[j] = parseInt(args[i]);
					j++;
				}
				
				Polynomial p = new Polynomial(coeffs);
				List<Fraction> possibleRoots = new ArrayList<>();
				List<Fraction> realRoots = new ArrayList<>();
				
				for (Fraction f : p.possibleRoots())
					possibleRoots.add(f);
				
				for (Fraction f : p.realRoots())
					realRoots.add(f);
				
				System.out.print("Possible roots:\n+/- ");
				for (int i = 0; i < possibleRoots.size(); i++) {
					if (!possibleRoots.get(i).isNegative()) {
						if (i == possibleRoots.size() - 2)
							System.out.println(possibleRoots.get(i) + "\n");
						else
							System.out.print(possibleRoots.get(i) + ", ");
					}
				}
				
				System.out.println("Real roots:");
				for (int i = 0; i < realRoots.size(); i++) {
					if (i == realRoots.size() - 1)
						System.out.println(realRoots.get(i));
					else
						System.out.print(realRoots.get(i) + ", ");
				}
				break;
				
			//Return a polynomial divided by a value
			case "syntheticdiv":
				double[] coeffs4 = new double[args.length - 2];
				
				int k = 0;
				for (int i = 1; i < args.length - 1; i++) {
					coeffs4[k] = parseDouble(args[i]);
					k++;
				}
				
				Polynomial q = new Polynomial(coeffs4);
				
				Fraction f;
				if (args[args.length - 1].contains("/")) {
					f = new Fraction(args[args.length - 1]);
				}
				else {
					f = new Fraction(parseDouble(args[args.length - 1]), 1);
				}
				List<Fraction> divided = Polynomial.syntheticSub(f, q);
				
				System.out.println(new Polynomial(divided));
				break;
				
			//Return the integral of a polynomial over a certain interval
			case "integrate":
				int[] interval = {parseInt(args[args.length - 2]), parseInt(args[args.length - 1])};
				double[] coeffs2 = new double[args.length - 3];
				for (int i = 1; i < args.length - 2; i++)
					coeffs2[i - 1] = parseDouble(args[i]);
				
				Polynomial p2 = new Polynomial(coeffs2);
				
				System.out.println("The integral of " + p2 + " over (" + interval[0] + ", " + interval[1] + ") is " + p2.integral(interval[0], interval[1]));
				break;
				
			case "derive":
				int point = parseInt(args[args.length - 1]);
				double[] coeffs3 = new double[args.length - 2];
				for (int i = 1; i < args.length - 1; i++)
					coeffs3[i - 1] = parseDouble(args[i]);
				
				Polynomial p3 = new Polynomial(coeffs3);
				
				System.out.println("The derivative of " + p3 + " at x = " + point + " is " + p3.derivative(point));
				break;
				
			//Return usage
			default:
				returnUsage();
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			returnUsage();
			System.exit(0);
		} catch (NumberFormatException e) {
			returnUsage();
			System.exit(0);
		}
	}

	private static void returnUsage() {
		String usage = "Usage: java -jar gmath.jar <operation> [input]\n\nwhere operations include:\n\t"
				+ "factor\t\treturn a list of factors of a positive integer\n\t"
				+ "primefactor\treturn the prime factorization of a positive integer\n\t"
				+ "isprime\t\treturn whether or not a positive integer is prime\n\t"
				+ "gcf\t\treturn the GCF of two positive integers\n\t"
				+ "lcm\t\treturn the LCM of two positive integers\n\t"
				+ "fraction\treturn the fraction form of a number\n\t"
				+ "decimal\t\treturn the decimal form of a fraction\n\t"
				+ "x-int\t\treturn the x-intercept of a linear function\n\t"
				+ "calcline\treturn the formula of a line given two coordinates\n\t"
				+ "quadratic\tfactor quadratic and return the answer(s)\n\t"
				+ "discrim\t\treturn the discriminant of a quadratic\n\t"
				+ "rationalroot\treturn the possible rational roots of a polynomial\n\t"
				+ "syntheticdiv\treturn a polynomial divided by a value\n\t"
				+ "integrate\treturn the integral of a polynomial over an interval\n\t"
				+ "derive\t\treturn the derivative of a polynomial at a point";

		if (operation == null) {
			System.out.println(usage);
			System.exit(0);
		}

		switch (operation) {
		case "factor":
			System.out.println("Usage: java -jar gmath.jar factor [input]\n\n"
					+ "where input is a positive integer.");
			break;

		case "primefactor":
			System.out.println("Usage: java -jar gmath.jar primefactor [input]\n\n"
					+ "where input is a positive integer.");
			break;
			
		case "isprime":
			System.out.println("Usage: java -jar gmath.jar isprime [input]\n\n"
					+ "where input is a positive integer.");
			break;
			
		case "gcf":
			System.out.println("Usage: java -jar gmath.jar gcf [input]\n\n"
					+ "where input is two positive integers.");
			break;

		case "lcm":
			System.out.println("Usage: java -jar gmath.jar lcm [input]\n\n"
					+ "where input is two positive integers.");
			break;
			
		case "fraction":
			System.out.println("Usage: java -jar gmath.jar fraction [input]\n\n"
					+ "where input one number.");
			break;

		case "decimal":
			System.out.println("Usage: java -jar gmath.jar decimal [input]\n\n"
					+ "where input a fraction in the form numerator/denominaor.");
			break;
			
		case "x-int":
			System.out.println("Usage: java -jar gmath.jar x-int [input]\n\n"
					+ "where input is two numbers m and b, from the linear function f(x) = mx + b.");
			break;
			
		case "calcline":
			System.out.println("Usage: java -jar gmath.jar calcline [input]\n\n"
					+ "where input is two coordinates in the format (x1,y1) (x2,y2)");
			break;
			
		case "quadratic":
			System.out.println("Usage: java -jar gmath.jar quadratic [input]\n\n"
					+ "where input is three integers a, b, and c, from the quadratic\n"
					+ "f(x) = ax^2 + bx + c.");
			break;

		case "discrim":
			System.out.println("Usage: java -jar gmath.jar discrim [input]\n\n"
					+ "where input is three integers a, b, and c, from the quadratic\n"
					+ "f(x) = ax^2 + bx + c.");
			break;
			
		case "rationalroot":
			System.out.println("Usage: java -jar gmath.jar rationalroot [input]\n\n"
					+ "where input is the coefficients of a polynomial function.");
			break;
			
		case "synteticdiv":
			System.out.println("Usage: java -jar gmath.jar syntheticdiv [polynomial] [divisor]\n\n"
					+ "where input is the coefficients of a polynomial function.");
			break;
			
		case "integrate":
			System.out.println("Usage: java -jar gmath.jar integrate [coefficients] [interval]\n\n"
					+ "where coefficients are any number of integers representing the coefficients of a"
					+ "polynomial and interval is two integers representing the start and end of the\n"
					+ "interval to integrate over, respecitvely.");
			break;
			
		case "derive":
			System.out.println("Usage: java -jar gmath.jar derive [coefficients] [point]\n\n"
					+ "where coefficients are any number of integers representing the coefficients of a"
					+ "polynomial and point is the x value to derive at.");
			break;
			
		default:
			System.out.println(usage);
		}
	}

	private static int parseInt(String s) {
		try {
			if (s == null) {
				returnUsage();
				System.exit(0);
				return 0;
			} else {
				return Integer.parseInt(s);
			}
		} catch (final NumberFormatException ex) {
			returnUsage();
			System.exit(0);
			return 0;
		}
	}

	private static double parseDouble(String s) {
		try {
			if (s == null) {
				returnUsage();
				System.exit(0);
				return 0;
			} else {
				return Double.parseDouble(s);
			}
		} catch (final NumberFormatException ex) {
			returnUsage();
			System.exit(0);
			return 0;
		}
	}
}
