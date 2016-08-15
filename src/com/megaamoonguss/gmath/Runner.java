package com.megaamoonguss.gmath;

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
				returnUsage(null);
				break;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			if (args.length > 0)
				returnUsage(args[0].toLowerCase());
			else
				returnUsage(null);
			System.exit(0);
		}
	}
	
	public static int parseInt(String s) {
		try {
			if (s == null) {
				returnUsage(null);
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
		System.out.println("Usage: " + operation);
	}
}
