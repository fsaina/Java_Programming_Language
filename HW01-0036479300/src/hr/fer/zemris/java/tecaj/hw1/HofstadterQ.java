package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program calculates the i-th number of Hofstadter's Q sequence.
 * 
 * @author Filip Saina
 * @version 1.0
 */

public class HofstadterQ {
	
	/**
	 * Entry point of the application. 
	 * Required one argument as a parameter
	 * 
	 * @param args - the i-th number in Hofstadter's Q sequence
	 */
	
	public static void main(String args[]){
		
		if(args.length != 1){
			System.err.println("	One argument required, the i-th number");
			System.exit(1);
		}
		
		Integer i = Integer.parseInt(args[0]);
		
		if(i < 0 || i == 0) {
			System.err.println("Argument must be a positive value");
			System.exit(1);
		}
		
		long rezult = Q(i);

		System.out.format("You requested calculation of %d. "
				+ "number of Hofstadter's Q-sequence. "
				+ "The requested number is %d.", i, rezult);
	}
	
	/*
	 * Method for calculating the Hofstadter's Q sequence.
	 * Input requires the i-th number as int.
	 * Code implemented as stated here:
	 * https://rosettacode.org/wiki/Hofstadter_Q_sequence
	 * 
	 * Return long value.
	 */
	public static long Q(long i){
		if(i == 1 || i == 2) return 1;
		return Q(i - Q(i-1)) + Q(i - Q(i-2));
	}

}
