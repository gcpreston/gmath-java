package com.gmoneycorp;
import java.util.ArrayList;

public class Runner {

	private static String operation;

	public static void main(String[] args) {

		try {
			String op = args[0].toLowerCase();
			operation = op;

			switch (operation) {

			//Add two or more numbers
			case "add":
				if (args.length < 3) {
					returnUsage();
					break;
				}

				double addAns = 0;
				for (int i = 1; i < args.length; i++)
					addAns += parseDouble(args[i]);

				for (int i = 1; i < args.length; i++) {
					if (i == args.length - 1)
						System.out.print(args[i] + " = ");
					else
						System.out.print(args[i] + " + ");
				}
				System.out.print(addAns);
				break;

			//Subtract two or more numbers
			case "subtract":
				if (args.length < 3) {
					returnUsage();
					break;
				}

				double subAns = parseDouble(args[1]);
				for (int i = 2; i < args.length; i++)
					subAns -= parseDouble(args[i]);

				for (int i = 1; i < args.length; i++) {
					if (i == args.length - 1)
						System.out.print(args[i] + " = ");
					else
						System.out.print(args[i] + " - ");
				}
				System.out.print(subAns);
				break;
				
			//Return a list of factors of an integer
			case "factor":
				if (args.length > 2 || parseInt(args[1]) < 0)
					returnUsage();
				else
					System.out.println("Factors of " + args[1] + ": " + GMath.factor(parseInt(args[1])));
				break;

			//Return prime factorization of an integer
			case "primefactor":
				if (args.length > 2 || parseInt(args[1]) < 0)
					returnUsage();
				else {
					ArrayList<Integer> primeFactors = GMath.primeFactorize(parseInt(args[1]));
					
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
					if (GMath.isPrime(parseInt(args[1])))
						System.out.println(args[1] + " is prime.");
					else
						System.out.println(args[1] + " is not prime.");
				}
				break;
				
			//Return the greatest common factor of two integers
			case "gcf":
				if (args.length > 3 || parseInt(args[1]) < 0 || parseInt(args[2]) < 0)
					returnUsage();
				else
					System.out.println("GCF of " + args[1] + " and " + args[2] + ": " + GMath.findGCF(parseInt(args[1]), parseInt(args[2])));
				break;
				
			//Factor quadratic and return the answer(s)
			case "quadratic":
				if (args.length > 4)
					returnUsage();
				else {
					Quadratic quad = new Quadratic(parseInt(args[1]), parseInt(args[2]), parseInt(args[3]));
					System.out.print(quad + " = ");

					int [] factoredEquation = quad.factorQuadratic();
					if (factoredEquation == null)
						System.out.println("Not factorable.\n");
					else {
						System.out.print("(" + factoredEquation[0] + "x ");
						if (factoredEquation[1] > 0)
							System.out.print("+ " + factoredEquation[1] + ")(" + factoredEquation[2] + "x ");
						else
							System.out.print("- " + factoredEquation[1] * -1 + ")(" + factoredEquation[2] + "x ");
						if (factoredEquation[3] > 0)
							System.out.println("+ " + factoredEquation[3] + ")\n");
						else
							System.out.println("- " + factoredEquation[3] * -1 + ")\n");
					}

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
			case "disc":
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
		}
	}

	public static void returnUsage() {
		String usage = "Usage: java -jar gmath.jar <operation> [input]\n\nwhere operations include:\n\t"
				+ "add\t\tadd two or more numbers\n\t"
				+ "subtract\tsubtract two or more numbers\n\t"
				+ "factor\t\treturn a list of factors of a positive integer\n\t"
				+ "primefactor\treturn the prime factorization of a positive integer\n\t"
				+ "isprime\t\treturn whether or not a positive integer is prime\n\t"
				+ "gcf\t\treturn the GCF of two positive integers\n\t"
				+ "quadratic\tfactor quadratic and return the answer(s)\n\t"
				+ "disc\t\treturn the discriminant of a quadratic";

		if (operation == null) {
			System.out.println(usage);
			System.exit(0);
		}

		switch (operation) {
		case "add":
			System.out.println("Usage: java -jar gmath.jar add [input]\n"
					+ "where input is two or more numbers.");
			break;

		case "subtract":
			System.out.println("Usage: java -jar gmath.jar subtract [input]\n"
					+ "where input is two or more numbers.\n"
					+ "The second number and after are subtracted from the first number.");
			break;

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

		case "quadratic":
			System.out.println("Usage: java -jar gmath.jar quadratic [input]\n"
					+ "where input is three integers a, b, and c, from the quadratic ax^2 + bx + c");
			break;

		case "disc":
			System.out.println("Usage: java -jar gmath.jar disc [input]\n"
					+ "where input is three integers a, b, and c, from the quadratic ax^2 + bx + c");
			break;
			
		default:
			System.out.println(usage);
		}
	}

	public static int parseInt(String s) {
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

	public static double parseDouble(String s) {
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
