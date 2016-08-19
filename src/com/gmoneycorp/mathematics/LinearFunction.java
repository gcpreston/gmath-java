package com.gmoneycorp.mathematics;

public class LinearFunction {

	private double m;
	private double b;

	public LinearFunction(double m, double b) {
		this.m = m;
		this.b = b;
	}

	public LinearFunction(String coord1, String coord2) {

		double x1, x2, y1, y2;
		coord1 = coord1.replace("(", "");
		coord1 = coord1.replace(")", "");
		coord2 = coord2.replace("(", "");
		coord2 = coord2.replace(")", "");
		String[] arr1 = coord1.split(",");
		String[] arr2 = coord2.split(",");

		x1 = Double.parseDouble(arr1[0]);
		x2 = Double.parseDouble(arr2[0]);
		y1 = Double.parseDouble(arr1[1]);
		y2 = Double.parseDouble(arr2[1]);

		this.m = (y2 - y1) / (x2 - x1);
		this.b = y1 - (this.m * x1);
	}

	public double findXInt() {
		return (-1 * b) / m;
	}

	public double getM() {
		return m;
	}

	public double getB() {
		return b;
	}

	public String toString() {
		String eq = "y = ";

		if (m == Math.floor(m))
			eq += (int)m + "x ";
		else
			eq += m + "x ";
		if (b < 0) {
			eq += "- ";
			if (b == Math.floor(b))
				eq += -1 * (int)b;
			else
				eq += -1 * b;
		}
		else {
			eq += "+ ";
			if (b == Math.floor(b))
				eq += (int)b;
			else
				eq += b;
		}

		return eq;
	}
}
