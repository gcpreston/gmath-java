package com.gmoneycorp.gmath;

public class Runner {

	public static void main(String[] args) {

		try {
			String operation = args[0].toLowerCase();

			switch (operation) {
			case "factor":
				if (args.length > 2) {
					returnUsage(operation);
					break;
				}
				System.out.println("Factors of " + args[1] + ": " + GMath.factor(parseInt(args[1])));
				break;
			case "gcf":
				if (args.length > 3) {
					returnUsage(operation);
					break;
				}
				System.out.println("GCF of " + args[1] + " and " + args[2] + ": " + GMath.findGCF(parseInt(args[1]), parseInt(args[2])));
				break;
			default:
				returnUsage("null");
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			if (args.length > 0)
				returnUsage(args[0].toLowerCase());
			else
				returnUsage("null");
			System.exit(0);
		}
	}
	
	public static int parseInt(String s) {
		try {
			if (s == null) {
				returnUsage("null");
				System.exit(0);
				return 0;
			} else {
				return Integer.parseInt(s);
			}
		} catch (final NumberFormatException ex) {
			returnUsage(null);
			System.exit(0);
			return 0;
		}
	}
	
	public static void returnUsage(String operation) {
		switch (operation) {
		case "factor":
			System.out.println("Usage: java -jar gmath.jar factor [input]\nwhere input is an integer.");
			break;
		case "gcf":
			System.out.println("Usage: java -jar gmath.jar gcf [input]\nwhere input is two integers.");
			break;
		default:
			System.out.println("Usage: java -jar gmath.jar [operation] [input]\n\nwhere operations include:\n\tfactor: return a list of factors "
					+ "of an integer\n\tgcf: return the greatest common factor of two integers");
		}
	}
}
