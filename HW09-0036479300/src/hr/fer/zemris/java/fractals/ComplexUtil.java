package hr.fer.zemris.java.fractals;

/**
 * Complex number util class used for various indirect operations over Complex numbers
 */
public class ComplexUtil {

    /**
     * Method used for converting the provided String value into a valid complex number. Null value is
     * returned if the String is not valid
     *
     * @param input String to be read
     * @return Comlex number read from the input
     *
     * @throws IllegalArgumentException if the string format is invalid
     */
    public static Complex readComplex(String input) throws NumberFormatException {

        Double real = null;
        Double imag = null;

        input = input.replaceAll(" ", "");


        if (input.contains("+") || input.contains("-")) {
            String[] split = input.split("((?=\\+)|(?=\\-))");


            for (String operator : split) {

                operator = operator.trim();

                if (operator.contains("i")) {

                    if (input.trim().length() == 1) {
                        operator = "1.0";
                    } else {
                        operator = operator.replaceAll("i", "");

                        if (operator.trim().length() == 1) {
                            operator = operator + "1";
                        }
                    }


                    if (imag != null)
                        throw new IllegalArgumentException("Illegal input format: " + input);

                    imag = Double.parseDouble(operator);

                } else {

                    if (real != null)
                        throw new IllegalArgumentException("Illegal input format: " + input);
                    real = Double.parseDouble(operator);
                }
            }
        } else {

            if (input.contains("i")) {
                if (input.trim().length() == 1) {
                    imag = 1.0;
                } else {

                    input = input.replaceAll("i", "");
                    imag = Double.parseDouble(input);
                }
            } else {
                real = Double.parseDouble(input);
            }
        }


        real = (real == null) ? 0 : real;
        imag = (imag == null) ? 0 : imag;

        return new Complex(real, imag);

    }
}
