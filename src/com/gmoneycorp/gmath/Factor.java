package com.gmoneycorp.gmath;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Graham Preston
 */
public abstract class Factor {

	/**
	 * Returns all factors of a specified integer as an ArrayList.
	 * @param num	an integer to be factored
	 * @return		an ArrayList of factors of num
	 */
	public static List<Integer> factor(int num) {
		List<Integer> factors = new ArrayList<>();

		if (num == 0)
			return factors;

		factors.add(1);

		if (num == 1)
			return factors;

		if (num < 0) 
			num *= -1;
		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				factors.add(i);
				if (i != (num / i))
					factors.add(num / i);
			}
		}
		factors.add(num);
		factors.sort(null);
		
		return factors;
	}

	/**
	 * Returns the prime factorization of a specified integer as
	 * an ArrayList.
	 * @param num	an integer to be prime factorized
	 * @return		an ArrayList of the prime factorization of num
	 */
	public static List<Integer> primeFactorize(int num) {
		List<Integer> primeFactors = new ArrayList<>();
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
		List<Integer> factors1 = factor(num1);
		List<Integer> factors2 = factor(num2);
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
		List<Integer> primeFactors1 = primeFactorize(num1);
		List<Integer> primeFactors2 = primeFactorize(num2);

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

	/**
	 * A utility to find the number of occurrences of a certain integer
	 * in a specified ArrayList. This is used in findLCM().
	 * @param num	the integer to find the number of occurrences of
	 * @param arr	the ArrayList to search for num in
	 * @return		the number of occurrences of num in arr
	 */
	private static int countOf(int num, List<Integer> arr) {
		int count = 0;
		for (int x : arr) {
			if (x == num)
				count++;
		}
		return count;
	}
}
