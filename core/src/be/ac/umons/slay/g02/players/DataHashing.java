package be.ac.umons.slay.g02.players;

import java.security.MessageDigest;

/**
 * Class containing the methods to hash user passwords
 * <p>
 * Source: https://dzone.com/articles/storing-passwords-java-web
 */
public class DataHashing {

    public static final String SALT = "chaussette";

    /**
     * Hashes a password
     *
     * @param password the password to hash
     */
    public static String hash(String password) {
        String saltedPassword = SALT + password;
        String hashedPassword = generateHash(saltedPassword);

        return hashedPassword;
    }

    /**
     * Generates a hash from the input
     *
     * @param input the text to hash
     * @return the generated hash
     */
    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (Exception e) {
            new Exception("Hashing error");
        }
        return hash.toString();
    }
}