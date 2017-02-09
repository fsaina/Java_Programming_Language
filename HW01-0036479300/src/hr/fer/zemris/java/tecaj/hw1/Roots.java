package hr.fer.zemris.java.tecaj.hw1;

/**
 * Roots application used for calculating the n-th root of a
 * complex number.
 * 
 * 
 * @author Filip Saina
 *
 */

public class Roots {
	
	/**
	 * Entry point of the application
	 * 
	 * @param args - reguires 3 numbers: the real paremeter,
	 * the imaginary and the n-th root to be calcualted.
	 */

	public static void main(String[] args) {
		if(args.length != 3){
			System.err.println("Invalid number of arguments given. "
					+ "%nreal part of complex number, "
					+ "%Nimaginary part of complex number, "
					+ "%nand required root to calculate");
			System.exit(1);
		}
		
		int x = Integer.parseInt(args[0]);		//real
		int y = Integer.parseInt(args[1]);		//imaginary
		int n = Integer.parseInt(args[2]);		//n-th root
		
		if(n < 1 || x < 1 || y < 1) {
			System.err.println("Value inputer is required to be a natural"
					+ " number greater than 1");
			System.exit(1);
		}
		
		System.out.println("You requested calculation of "+ n +
				". roots. Solutions are:");
	
		//Phy represents the angle, and r the radius
		double phi = Math.atan((double)y/x);
		double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		double rep = (double) 1/n;
		
		for(int k=0; k<n; k++){
			double real = Math.pow(r, rep) * Math.cos(rep * (phi + Math.PI * 2*k));
			double imag = Math.pow(r, rep) * Math.sin(rep * (phi + Math.PI * 2*k));
			
			System.out.println(k+1+ ") " + 
			Math.round(real) + (imag > 0 ? " + " : " - ") +
					Math.abs(Math.round(imag)) + "i");
		}
	}

}