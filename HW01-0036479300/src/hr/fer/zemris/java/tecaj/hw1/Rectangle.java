package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Rectangle application provides basic arithmetic functions
 * and calculations over a set of given points thus calculating
 * properties like area and perimeter
 * 
 * @author Filip Saina
 * @version 1.0
 */

public class Rectangle {
	
	/**
	 * Starting point of the application
	 * @param args - if given, expected input of two values describing
	 * the width and height of a rectangle
	 */

	public static void main(String[] args) throws IOException {
		
		 double width = 0.0;
		 double height = 0.0;
		 
		 if(args.length != 2 && args.length != 0){
			 System.err.println("Invalid number of arguments was provided.");
			 System.exit(1);  //1 represents code value for error of this kind
		 }
		 
		 if(args.length == 0){
			 
			 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			 
			 width = askUser(reader, "width");
			 height = askUser(reader, "height");
			 
		 } else {
			 width = Double.parseDouble(args[0]);
			 height = Double.parseDouble(args[1]);
		 }
		 
		 double area = width * height;
		 double perimeter = 2 * width + 2 * height;
		 
		 System.out.println("You have specified a retangular"
		 		+ " with width " + width + " and height: " + height +
		 		". Its area is " + area + " and its perimeter is " + perimeter + ".");

	}
	
	/*
	 * Method handling the user input and the correct input value
	 * expected - positive non zero numerical value.
	 * Method requires a BufferedReader object and a String required
	 * to be displayed to the end user.
	 */
	
	private static double askUser(BufferedReader reader, String message) throws IOException{
		double num = 0.0;
		
		System.out.println("Please provide " + message + ": ");
		
		while(true){
			String value = reader.readLine();
			if(value == null) break;  //EOF reached
			
			value = value.trim();
			
			if(value.isEmpty()) {
				System.out.println("Nothing was given");
				continue;
			}
			
			num = Double.parseDouble(value);
			
			if(num <= 0) {
				//Uppercase the leading letter
				String element = message.substring(0, 1).toUpperCase() +
								message.substring(1);
				//choose the right message
				String msg = num == 0 ? " is zero" : " is negative";
				System.out.println(element + msg);
				System.out.println("Please provide " + message);
				continue;
			}
			break;
		}
		return num;
	}

}
























