package com.gmoneycorp.mathematics;

public class LinearFunction {

	private double m;
	private double b;
	
	public LinearFunction(double m, double b) {
		this.m = m;
		this.b = b;
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
			eq += " + ";
			if (b == Math.floor(b))
				eq += (int)b;
			else
				eq += b;
		}
		
		return eq;
	}
}
