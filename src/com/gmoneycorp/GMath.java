package com.gmoneycorp;
import java.util.ArrayList;

public abstract class GMath {
	
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

	public static ArrayList<Integer> primeFactorize(int n) {
		ArrayList<Integer> primeFactors = new ArrayList<>();
	    for (int i = 2; i <= n / i; i++) {
	        while (n % i == 0) {
	            primeFactors.add(i);
	            n /= i ;
	        }
	    }
	    if (n > 1)
	        primeFactors.add(n);
	    
		return primeFactors;
	}

	public static boolean isPrime(int num) {
		if (factor(num).size() == 2)
			return true;
		else
			return false;
	}
	
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
	
	public static int countOf(int num, ArrayList<Integer> arr) {
		int count = 0;
		for (int x : arr) {
			if (x == num)
				count++;
		}
		return count;
	}
}
