package com.gmoneycorp.mathematics;
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
					ArrayList<Integer> primeFactors = Factor.primeFactorize(parseInt(args[1]));
					
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
					Quadratic quad = new Quadratic(parseDouble(args[1]), parseDouble(args[2]), parseInt(args[3]));
					System.out.print(quad + " = ");

					if (quad.factorQuadratic() == null)
						System.out.println("Not factorable.\n");
					else
						System.out.println(quad.factorToString() + "\n");

					if (quad.solveDiscriminant() > 0) {
						System.out.println("Two real solutions:");
						System.out.println("x = " + quad.solveQuadratic()[0] + ", " + quad.solveQuadratic()[1]);
					}
					else if (quad.solveDiscriminant() == 0) {
						System.out.println("One real solution (duplicate solutions):");
						System.out.println("x = " + quad.solveQuadratic()[0]);
					}
					else if (quad.solveDiscriminant() < 0)
						System.out.println("Two imaginary solutions.");
				}
				break;

			//Return discriminant of quadratic function
			case "discrim":
				if (args.length > 4)
					returnUsage();
				else {
					Quadratic quad = new Quadratic(parseInt(args[1]), parseInt(args[2]), parseInt(args[3]));
					System.out.println("The discriminant of " + quad + " is " + quad.solveDiscriminant());
				}
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
				+ "x-int\t\treturn the x-intercept of a linear function\n\t"
				+ "calcline\tcalculate a line given two coordinates\n\t"
				+ "quadratic\tfactor quadratic and return the answer(s)\n\t"
				+ "discrim\t\treturn the discriminant of a quadratic";

		if (operation == null) {
			System.out.println(usage);
			System.exit(0);
		}

		switch (operation) {
		case "factor":
			System.out.println("Usage: java -jar gmath.jar factor [input]\n"
					+ "where input is a positive integer.");
			break;

		case "primefactor":
			System.out.println("Usage: java -jar gmath.jar primefactor [input]\n"
					+ "where input is a positive integer.");
			break;
			
		case "isprime":
			System.out.println("Usage: java -jar gmath.jar isprime [input]\n"
					+ "where input is a positive integer.");
			break;
			
		case "gcf":
			System.out.println("Usage: java -jar gmath.jar gcf [input]\n"
					+ "where input is two positive integers.");
			break;

		case "x-int":
			System.out.println("Usage: java -jar gmath.jar x-int [input]\n"
					+ "where input is two numbers m and b, from the linear function f(x) = mx + b.");
			break;
			
		case "calcline":
			System.out.println("Usage: java -jar gmath.jar calcline [input]\n"
					+ "where input is two coordinates in the format (x1,y1) (x2,y2)");
			break;
			
		case "quadratic":
			System.out.println("Usage: java -jar gmath.jar quadratic [input]\n"
					+ "where input is three integers a, b, and c, from the quadratic f(x) = ax^2 + bx + c");
			break;

		case "discrim":
			System.out.println("Usage: java -jar gmath.jar disc [input]\n"
					+ "where input is three integers a, b, and c, from the quadratic f(x) = ax^2 + bx + c");
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
