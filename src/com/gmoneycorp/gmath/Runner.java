package com.gmoneycorp.gmath;

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
				if (args.length > 2)
					returnUsage();
				else
					System.out.println("Factors of " + args[1] + ": " + GMath.factor(parseInt(args[1])));
				break;

			//Return the greatest common factor of two integers
			case "gcf":
				if (args.length > 3)
					returnUsage();
				else
					System.out.println("GCF of " + args[1] + " and " + args[2] + ": " + GMath.findGCF(parseInt(args[1]), parseInt(args[2])));
				break;

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
				+ "add: add two or more numbers\n\t"
				+ "subtract: subtract two or more numbers"
				+ "factor: return a list of factors of an integer\n\t"
				+ "gcf: return the greatest common factor of two integers";

		if (operation == null) {
			System.out.println(usage);
			System.exit(0);
		}

		switch (operation) {
		case "add":
			System.out.println("Usage: java -jar gmath.jar add [input]\nwhere input is two or more numbers.");
			break;
		case "subtract":
			System.out.println("Usage: java -jar gmath.jar subtract [input]\nwhere input is two or more numbers.\nThe second number and after are subtracted from the first number.");
			break;
		case "factor":
			System.out.println("Usage: java -jar gmath.jar factor [input]\nwhere input is an integer.");
			break;
		case "gcf":
			System.out.println("Usage: java -jar gmath.jar gcf [input]\nwhere input is two integers.");
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
