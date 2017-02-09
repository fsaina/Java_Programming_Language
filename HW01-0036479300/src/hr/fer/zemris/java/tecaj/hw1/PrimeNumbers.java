package hr.fer.zemris.java.tecaj.hw1;

import java.util.ArrayList;

/**
 * Application calculates prime numbers
 * 
 * @author Filip Saina
 * @version 1.0
 */

public class PrimeNumbers {
	
	/**
	 * Application entry point
	 * 
	 * @param args - number of n primer numbers to
	 * write out.
	 */

	public static void main(String[] args) {
		if(args.length != 1){
			System.err.println("Invalid number of arguments given. "
					+ "1 required - n, the number of primes to calculate");
			System.exit(1);
		}
		
		int n = Integer.parseInt(args[0]);
		
		if(n < 1) {
			System.err.println("Value inputer is required to be a natural"
					+ " number greater than 1");
			System.exit(1);
		}
		
		System.out.println("You requested calculation of "+ n +
				" prime numbers. Here they are:");
		
		int a = 0;
		for(Integer i: printPrimes(n)){
			a++;
			System.out.println(a + ". "+ i);
		}
		
	}
	
	/**
	 * Method calculates n first prime numbers.
	 * Prime numbers are natural numbers greater than one 
	 * has not any other divisors except 1 and itself.
	 * 2 is considered the first prime number.
	 * 
	 * @param top_num - n first primes to calculate
	 * @return ArrayList containing calcualated
	 * prime numbers
	 */
	
	public static ArrayList<Integer> printPrimes(int top_num){
		int number = 2;
		int counter = 0 ;
		ArrayList<Integer> primeNumbers = new ArrayList<Integer>();
		
		while(counter != top_num){
			boolean prime = true;	//assume true	
			
			for(int i=2; i< number; i++){
				if(number%i == 0){
					prime = false;
					break;
				}
			}
			
			if(prime){
				primeNumbers.add(number);
				counter++;
			}
			
			number++;
		}
		return primeNumbers;
	}

}
