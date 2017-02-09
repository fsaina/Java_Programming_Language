package hr.fer.zemris.java.tecaj.hw1;

import java.util.ArrayList;

/**
 * Number decomposition application returns all the prime factors of which
 * a natural number is made of
 * 
 * @author Filip Saina
 *
 */

public class NumberDecomposition {

	/**
	 * Entry point to the application
	 * 
	 * @param args - one argument, the number of which
	 * the prime value is required
	 */
	
	public static void main(String args[]){
		if(args.length != 1){
			System.err.println("Invalid number of arguments given. "
					+ "1 required - n, natural number grater than 1");
			System.exit(1);
		}
		
		int n = Integer.parseInt(args[0]);
		
		if(n < 1) {
			System.err.println("Value inputer is required to be a natural"
					+ " number greater than 1");
			System.exit(1);
		}
		
		System.out.println("You requested calculation of "+ n +
				" onto prime factors. Here they are:");
		
		int a =0;
		for(Integer i: numberDecomposition(n)){
			a++;
			System.out.println(a + ". "+i);
		}
	}
	
	/**
	 * Method used to calculate prime factors of a given number
	 * n. Method to accomplish that uses 'brute force' testing
	 * of each number in every iteration.
	 * 
	 * @param n - number to be broken into prime factors
	 * @return ArrayList<Integer> containing all the prime factors
	 */
	
	public static ArrayList<Integer> numberDecomposition(int n){
		int tmpN = n;
		ArrayList<Integer> primeFactors = new ArrayList<Integer>();
		boolean primed = true;
		
		while(primed){
			primed = false;
			for(int i=2; i<tmpN; i++){
				if(n%i == 0){
					//add to array
					primeFactors.add(i);
					n = n/i;
					primed = true;
					break;
				}
			}
		}
		
		return primeFactors;
	}
	
}
