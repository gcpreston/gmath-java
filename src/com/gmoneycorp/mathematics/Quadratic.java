package com.gmoneycorp.mathematics;
import java.util.ArrayList;
import com.gmoneycorp.mathematics.GMath;

/**
 * @author Graham Preston
 */
public class Quadratic {

	int a;
	int b;
	int c;
	
	/**
	 * The constructor for Quadratic. Stores the values of a, b, and c for
	 * the format ax^2 + bx + c as instance variables.
	 * @param a		the value for a in a quadratic equation
	 * @param b		the value for a in a quadratic equation
	 * @param c		the value for a in a quadratic equation
	 */
	public Quadratic(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	/**
	 * Returns the two factors of c that add up to b.
	 * @return		an integer array with containing two factors of c that add
	 * 				up to b
	 */
	private int[] findWorkingFactors() {
		ArrayList<Integer> factors = GMath.factor(a*c);
		
		int[] workingFactors = new int[2];

		if (c > 0) {
			for (int i = 0; i < factors.size(); i++) {
				if (factors.get(i) + factors.get(factors.size() - 1 - i) == b) {
					workingFactors[0] = factors.get(i);
					workingFactors[1] = factors.get(factors.size() - 1 - i);
					return workingFactors;
				}
			}

			for (int i = 0; i < factors.size(); i++) {
				if ((factors.get(i) * -1) + (factors.get(factors.size() - 1 - i) * -1) == b) {
					workingFactors[0] = (factors.get(i) * -1);
					workingFactors[1] = (factors.get(factors.size() - 1 - i) * -1);
					return workingFactors;
				}
			}
		}
		else {
			for (int i = 0; i < factors.size(); i++) {
				if (factors.get(i) + (factors.get(factors.size() - 1 - i) * -1) == b) {
					workingFactors[0] = factors.get(i);
					workingFactors[1] = (factors.get(factors.size() - 1 - i) * -1);
					return workingFactors;
				}
			}
		}

		return null;
	}
	
	/**
	 * Returns the factored version of the quadratic in the form of an integer array
	 * with four indexes holding the coefficients.
	 * @return		an integer array with the coefficients of the factored equation
	 * 				as its four indexes
	 */
	public int[] factorQuadratic() {
		int[] workingFactors = findWorkingFactors();

		if (workingFactors == null)
			return null;

		int group1GCF = GMath.findGCF(a, workingFactors[0]);
		int group2GCF = GMath.findGCF(workingFactors[1], c);

		if ((a/group1GCF) != (workingFactors[1]/group2GCF) || (workingFactors[0]/group1GCF) != (c/group2GCF)) {
			group2GCF *= -1;
		}
		int[] finalEquation = {group1GCF, group2GCF, (a/group1GCF), (workingFactors[0]/group1GCF)};

		return finalEquation;
	}
	
	/**
	 * Returns the factored form of the quadratic as a String.
	 * @return		a String containing the factored form of the given Quadratic
	 */
	public String factorToString() {
		int[] factoredEquation = factorQuadratic();
		String factoredForm = "";
		
		factoredForm += "(" + factoredEquation[0] + "x ";
		if (factoredEquation[1] > 0)
			factoredForm += "+ " + factoredEquation[1] + ")(" + factoredEquation[2] + "x ";
		else
			factoredForm += "- " + factoredEquation[1] * -1 + ")(" + factoredEquation[2] + "x ";
		if (factoredEquation[3] > 0)
			factoredForm += "+ " + factoredEquation[3] + ")";
		else
			factoredForm += "- " + factoredEquation[3] * -1 + ")";
		
		return factoredForm;
	}
	
	/**
	 * Returns the possible values of x using the quadratic formula.
	 * @return		a double array containing the two possible values of x. Any
	 * 				imaginary x values are entered as NaN
	 */
	public double[] solveQuadratic() {
		double [] answers = new double [2];
		
		answers[0] = ((-1*b) + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
		answers[1] = ((-1*b) - Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
		
		return answers;
	}
	
	/**
	 * Returns the discriminant using b^2 - 4ac.
	 * @return		a double containing the value of the discriminant
	 */
	public double solveDiscriminant()  {
		return Math.pow(b, 2) - (4 * a * c);
	}
	
	/**
	 * Returns the value of a
	 * @return		the value of a
	 */
	public int getA() {
		return a;
	}
	
	/**
	 * Returns the value of b
	 * @return		the value of b
	 */
	public int getB() {
		return b;
	}
	
	/**
	 * Returns the value of c
	 * @return		the value of c
	 */
	public int getC() {
		return c;
	}
	
	/**
	 * Returns the quadratic in the formula ax^2 + bx + c.
	 */
	public String toString() {
		String str = a + "x^2 ";
		if (b < 0)
			str += "- " + -1*b + "x ";
		else
			str += "+ " + b + "x ";
		if (c < 0)
			str += "- " + -1*c;
		else
			str += "+ " + c;
		
		return str;
	}
}
