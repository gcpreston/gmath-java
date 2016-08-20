package com.gmoneycorp.mathematics;
import java.util.ArrayList;

/**
 * @author Graham Preston
 */
public abstract class Factor {
	
	/**
	 * Returns all factors of a specified integer as an ArrayList.
	 * @param num	an integer to be factored
	 * @return		an ArrayList of factors of num
	 */
	public static ArrayList<Integer> factor(int num) {
		ArrayList<Integer> factors = new ArrayList<Integer>();

		if (num > 0) {
			for (int i = 1; i <= num; i++) {
				if (num % i == 0)
					factors.add(i);
			}
		}
		else {
			for (int i = 1; i <= num*-1; i++) {
				if (num % i == 0)
					factors.add(i);
			}
		}

		return factors;
	}

	/**
	 * Returns the prime factorization of a specified integer as
	 * an ArrayList.
	 * @param num	an integer to be prime factorized
	 * @return		an ArrayList of the prime factorization of num
	 */
	public static ArrayList<Integer> primeFactorize(int num) {
		ArrayList<Integer> primeFactors = new ArrayList<>();
	    for (int i = 2; i <= num / i; i++) {
	        while (num % i == 0) {
	            primeFactors.add(i);
	            num /= i ;
	        }
	    }
	    if (num > 1)
	        primeFactors.add(num);
	    
		return primeFactors;
	}

	/**
	 * Evaluates whether or not a specified integer is prime.
	 * @param num	an integer to be evaluated as prime or not
	 * @return		true if num is prime, false otherwise
	 */
	public static boolean isPrime(int num) {
		if (factor(num).size() == 2)
			return true;
		else
			return false;
	}
	
	/**
	 * Returns the greatest common factor of two specified integers.
	 * @param num1	the first integer
	 * @param num2	the second integer
	 * @return		the greatest common factor of num1 and num2
	 */
	public static int findGCF(int num1, int num2) {
		ArrayList<Integer> factors1 = factor(num1);
		ArrayList<Integer> factors2 = factor(num2);
		int GCF = 1;

		for (int i = 0; i < factors1.size(); i++) {
			for (int j = 0; j < factors2.size(); j++) {
				if (factors1.get(i) == factors2.get(j))
					GCF = factors1.get(i);
			}
		}
		return GCF;
	}

	/**
	 * Returns the lowest common multiple of two specified integers.
	 * @param num1	the first integer
	 * @param num2	the second integer
	 * @return		the least common multiple of num1 and num2
	 */
	public static int findLCM(int num1, int num2) {
		ArrayList<Integer> primeFactors1 = primeFactorize(num1);
		ArrayList<Integer> primeFactors2 = primeFactorize(num2);
		
		for (int x : primeFactors2) {
			int count1 = countOf(x, primeFactors1);
			int count2 = countOf(x, primeFactors2);
			if (count2 > count1) {
				for (int i = 0; i < count2 - count1; i++)
					primeFactors1.add(x);
			}
		}
		
		int LCM = 1;
		for (int x : primeFactors1)
			LCM *= x;
		
		return LCM;
	}
	
	//KNOWM BUG: Gets stuck in loop when working with repeating decimals
	/**
	 * Returns the given input in fraction form.
	 * @param num	a double to be converted to a fraction
	 * @return		a String containing the fraction form of num
	 */
	public static String toFraction(double d) {
		String s = String.valueOf(d);
		int numDecimals = s.length() - 1 - s.indexOf('.');
		
		if (d == Math.floor(d) || numDecimals >= 16)
			return s;
		
		int denom = (int)Math.pow(10, numDecimals);
		int workingNum = (int)(d * denom);
		int GCF = findGCF(workingNum, denom);
		workingNum /= GCF;
		denom /= GCF;
		
		return workingNum + "/" + denom;
	}
	
	/**
	 * A utility to find the number of occurrences of a certain integer
	 * in a specified ArrayList. This is used in findLCM().
	 * @param num	the integer to find the number of occurrences of
	 * @param arr	the ArrayList to search for num in
	 * @return		the number of occurrences of num in arr
	 */
	private static int countOf(int num, ArrayList<Integer> arr) {
		int count = 0;
		for (int x : arr) {
			if (x == num)
				count++;
		}
		return count;
	}
}
