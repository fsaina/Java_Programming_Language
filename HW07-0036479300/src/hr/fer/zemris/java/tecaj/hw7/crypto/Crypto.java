package hr.fer.zemris.java.tecaj.hw7.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Crypto class implementation used for encrypting and decrypting from and to cypher text in SHA-256 digest
 * algorithm
 *
 * @author Filip Saina
 */
public class Crypto {

    /**
     * Digest method algorithm currently used
     */
    private static final String DIGEST_ALGORITHM = "SHA-256";

    /**
     * Byte size of the digest algorithm
     */
    private static final int DIGEST_BYTE_SIZE = 64;

    /**
     * Secret key algorithm used
     */
    private static final String SECRET_KEY_ALGORITHM = "AES";

    /**
     * Method used for filling and removing the padding during encryption/decryption
     */
    private static final String CIPHER_PADDING_METHOD = "AES/CBC/PKCS5Padding";

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        if (args.length < 2 || args.length > 3) {
            System.err.println("Wrong number of arguments provided: " + args.length + ", 2 or 3 required");
            System.exit(-1);
        }

        String userInput = args[0];
        Path filePath = Paths.get(args[1]);
        Scanner input = new Scanner(System.in);

        if (args.length == 2) {
            if (userInput.toUpperCase().equals("CHECKSHA")) {

                if (filePath.toFile().exists()) {

                    checkShaDigest(
                            readInputWithMessage("Please provide expected " + DIGEST_ALGORITHM +
                                    " digest for " + filePath.getFileName(), input, DIGEST_BYTE_SIZE),
                            filePath);


                } else {
                    System.err.println("Un-existing file path provided as input: " + filePath.getFileName());
                    System.exit(-1);
                }
            } else {
                errorMessage("Unsupported operation requested: " + userInput);
            }

        } else {

            Path outputPath = Paths.get(args[2]);


            boolean encrypt = false;
            if (userInput.toUpperCase().equals("ENCRYPT")) {
                encrypt = true;
            } else if (userInput.toUpperCase().equals("DECRYPT")) {
                encrypt = false;
            } else {
                errorMessage("Unsupported operation requested: " + userInput);
            }

            cypherOperation(filePath,
                    outputPath,
                    encrypt,
                    readInputWithMessage("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):", input, 32),
                    readInputWithMessage("Please provide initialization vector as hex-encoded text (32 hex-digits):", input, 32));


            String operation = encrypt ? "Encryption" : "Decryption";
            System.out.println(operation + " completed. Generated file: " + outputPath.getFileName() + " based on file " + filePath.getFileName());

        }
    }

    /**
     * Method used for reading user input with a given message
     *
     * @param message      message to be shown
     * @param input        scanner object to read the input
     * @param expectedSize size expected of the key
     * @return String representing the read data
     */
    private static String readInputWithMessage(String message, Scanner input, int expectedSize) {

        String read = null;

        do {
            System.out.println(message);
            System.out.print("> ");

            read = input.next();

        } while (!isValidStringSize(read, expectedSize));

        return read;
    }

    /**
     * Method used for printing out the error message
     *
     * @param message String to be printed
     */
    private static void errorMessage(String message) {
        System.err.println(message);
        System.exit(-1);
    }

    /**
     * Method used for calculating and comparing the SHADigest
     *
     * @param input    String from the used obtained SHA
     * @param filePath path to the file used for calculating the SHA
     */
    private static void checkShaDigest(String input, Path filePath) {

        byte[] byteArray = hextobyte(input);
        byte[] calculatedByteArray = calculateDigest(filePath);

        boolean digestEquals = Arrays.equals(byteArray, calculatedByteArray);

        if (digestEquals) {
            System.out.println("Digesting completed. Digest of " + filePath.getFileName() + " matches expected digest.");
        } else {
            System.out.println("Digesting completed. Digest of " + filePath.getFileName() + " does not match the expected digest. Digest\n" +
                    "was: " + new String(calculatedByteArray));
        }

    }

    /**
     * Method for testing the validity of the string size
     *
     * @param s            String object to be tested
     * @param expectedSize size rexprected
     * @return true if lenght is as expected, false otherwise
     */
    private static boolean isValidStringSize(String s, int expectedSize) {
        if (s.length() != expectedSize) {
            System.out.println("Provided user input: " + s + " has not the valid length:" + expectedSize);
            return false;
        }
        return true;
    }

    /**
     * Method used for converting the hex char value to a byte value. Thus resulting in a output byte array.
     * Method takes two by two characters and converts them to a eight bit data by taking the first character,
     * shifting if to the left 4 times, and adding the bit value of the character of the second character.
     * Method works in hexadecimal values so they are converted before manipulation accordingly
     *
     * @param charText text to be converted
     * @return byte array representing the converted string array
     */
    public static byte[] hextobyte(String charText) {
        int textLenght = charText.length();
        byte[] data = new byte[textLenght / 2];

        for (int i = 0; i < textLenght - 1; i += 2) {

            data[i / 2] = (byte) (
                    (Character.digit(charText.charAt(i), 16) << 4) +
                            Character.digit(charText.charAt(i + 1), 16));

        }

        return data;

    }

    /**
     * Method used for calculating the digest of a file
     *
     * @param file file path to be calculated
     * @return byte array representing the digest data
     */
    private static byte[] calculateDigest(Path file) {

        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            errorMessage("Invalid algorithm provided to the MessageDigest class");
        }

        try (InputStream is = new BufferedInputStream(new FileInputStream(file.toFile()))) {

            byte[] buffer = new byte[1024];


            while (true) {

                int r = is.read(buffer);
                if (r < 1) break;

                digest.update(buffer, 0, r);

            }

        } catch (IOException e) {
            errorMessage("Error while reading the file: " + file.getFileName());
        }

        return digest.digest();

    }

    /**
     * Uniform method used for performing the encrypt or decrypt operation over a given input/output path.
     *
     * @param inputPath  file from which the data will be read
     * @param outputPath file to which the data will be written
     * @param cipher     Chiper object used for data manipulation
     */
    private static void performOperation(Path inputPath, Path outputPath, Cipher cipher) {


        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(inputPath.toFile()));
            os = new BufferedOutputStream(new FileOutputStream(outputPath.toFile()));

            byte[] buffer = new byte[16];


            while (true) {

                int r = is.read(buffer);
                if (r < 1) break;


                byte[] data = cipher.update(buffer);
                os.write(data);

            }


        } catch (IOException e) {
            errorMessage("Error while performing read/write operation on file: " + inputPath.getFileName());
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException ignorable) {
                }
            if (os != null)
                try {
                    os.close();
                } catch (IOException ignorable) {
                }
        }


    }

    /**
     * Method used for providing a cypher operation
     *
     * @param inputFile  file on which to perform the operation
     * @param outputFile output file on which to perform the operation
     * @param encrypt    true if encrypt operation is used, false otherwise
     * @param keyText    key of the cipher block
     * @param ivText     ivtext used by the cypher
     */
    private static void cypherOperation(Path inputFile, Path outputFile, boolean encrypt, String keyText, String ivText) {


        SecretKeySpec keySpec = new SecretKeySpec(hextobyte(keyText), SECRET_KEY_ALGORITHM);
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));

        String encryptString = encrypt ? "encrypting" : "decrypting";

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CIPHER_PADDING_METHOD);
            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

        } catch (InvalidKeyException e) {
            errorMessage("Key provided for " + encryptString + " is invalid");
        } catch (InvalidAlgorithmParameterException e) {
            errorMessage("Invalid AlgorithmParameter given");
        } catch (NoSuchAlgorithmException e) {
            errorMessage("Invalid algorithm provided to the 'Cipher' class");
        } catch (NoSuchPaddingException e) {
            errorMessage("Invalid padding preference provided to the 'Cipher' class");
        }


        performOperation(inputFile,
                outputFile,
                cipher);
    }

}
