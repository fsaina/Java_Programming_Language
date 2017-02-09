package hr.fer.zemris.java.tecaj_13.util;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * WebApplication util class
 */
public class Util {

    /**
     * Email parameter math regex expression that will be matched
     */
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*\n"
            +
            "      @[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$;\n";

    /**
     * Method used for calculating the hash value from a given string object. Method uses the SHA-1
     * algorithm.
     *
     * @param password String password input to be hashed
     * @return String hash result
     */
    public static String calculateHashFromString(String password) {
        String passwordHash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] digest = md.digest(bytes);
            passwordHash = DatatypeConverter.printHexBinary(digest);
        } catch (NoSuchAlgorithmException exception) {
            //IGNORABLE
        }
        return passwordHash;
    }

}
