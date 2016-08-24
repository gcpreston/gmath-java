package com.gmoneycorp.gmath;

public class LinearFunction {

	private double m;
	private double b;
	private Fraction mFraction;
	private Fraction bFraction;

	public LinearFunction(double m, double b) {
		this.m = m;
		this.b = b;
		this.mFraction = Fraction.toFraction(m).simplify();
		this.bFraction = Fraction.toFraction(b).simplify();
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
		this.mFraction = new Fraction(y2 - y1, x2 - x1).simplify();
		
		Fraction y1Fraction = Fraction.multiply(mFraction, new Fraction(x1, 1));
		this.bFraction = Fraction.subtract(new Fraction(y1, 1), y1Fraction).simplify();
	}

	public double findXInt() {
		return (-1 * b) / m;
	}

	/*
	public static Fraction[] findIntersect(LinearFunction l1, LinearFunction l2) {
		Fraction[] intersect = new Fraction[2];
		
	}
	*/
	
	public double getM() {
		return m;
	}

	public double getB() {
		return b;
	}
	
	public Fraction getMFraction() {
		return mFraction;
	}
	
	public Fraction getBFraction() {
		return bFraction;
	}

	public String toString() {
		String eq = "y = ";

		if (m == Math.floor(m))
			eq += (int)m + "x ";
		else
			eq += "(" + mFraction + ")" + "x ";
		if (b < 0) {
			if (b == Math.floor(b))
				eq += "- " + -1 * (int)b;
			else
				eq += "- " + bFraction.toString().substring(1);
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
