package com.gmoneycorp.mathematics;

public class LinearFunction {

	private double m;
	private double b;
	private String mFraction;
	private String bFraction;

	public LinearFunction(double m, double b) {
		this.m = m;
		this.b = b;
		this.mFraction = Fraction.simplifyFraction(Fraction.toFraction(m));
		this.bFraction = Fraction.simplifyFraction(Fraction.toFraction(b));
	}

	public LinearFunction(String coord1, String coord2) {

		double x1, x2, y1, y2;
		coord1 = coord1.replace("(", "");
		coord1 = coord1.replace(")", "");
		coord2 = coord2.replace("(", "");
		coord2 = coord2.replace(")", "");

		x1 = Double.parseDouble(coord1.split(",")[0]);
		x2 = Double.parseDouble(coord2.split(",")[0]);
		y1 = Double.parseDouble(coord1.split(",")[1]);
		y2 = Double.parseDouble(coord2.split(",")[1]);

		this.m = (y2 - y1) / (x2 - x1);
		this.b = y1 - (this.m * x1);
		this.mFraction = Fraction.simplifyFraction((y2 - y1) + "/" + (x2 - x1));
		this.bFraction = String.valueOf(Math.abs((double)Math.round(b * 1000d) / 1000d));
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
	
	public String getMFraction() {
		return mFraction;
	}
	
	public String getBFraction() {
		return bFraction;
	}

	public String toString() {
		String eq = "y = ";

		if (m == Math.floor(m))
			eq += (int)m + "x ";
		else
			eq += "(" + mFraction + ")" + "x ";
		if (b < 0) {
			eq += "- ";
			if (b == Math.floor(b))
				eq += -1 * (int)b;
			else
				eq += bFraction;
		}
		else {
			eq += "+ ";
			if (b == Math.floor(b))
				eq += (int)b;
			else
				eq += bFraction;
		}

		return eq;
	}
}
