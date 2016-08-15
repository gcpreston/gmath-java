package com.gmoneycorp.gmath;

public class Runner {

	private static String operation;
	
	public static void main(String[] args) {

		try {
			String op = args[0].toLowerCase();
			operation = op;

			switch (operation) {
			case "factor":
				if (args.length > 2) {
					returnUsage();
					break;
				}
				System.out.println("Factors of " + args[1] + ": " + GMath.factor(parseInt(args[1])));
				break;
			case "gcf":
				if (args.length > 3) {
					returnUsage();
					break;
				}
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
	
	public static void returnUsage() {
		String usage = "Usage: java -jar gmath.jar [operation] [input]\n\nwhere operations include:\n\tfactor: return a list of factors of an integer\n\tgcf: return the greatest common factor of two integers";
		
		if (operation == null) {
			System.out.println(usage);
			System.exit(0);
		}
		
		switch (operation) {
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
}
