package utils;

import java.security.MessageDigest;

/**
 * Hash functions utility class.
 * @author www.codejava.net
 *
 */
public class HashGeneratorUtils {
    private HashGeneratorUtils() {

    }

    public static String generateSHA1(String message) {
        return hashString(message, "SHA-1");
    }

    public static String generateSHA256(String message) {
        return hashString(message, "SHA-256");
    }

    private static String hashString(String message, String algorithm) {

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

            return convertByteArrayToHexString(hashedBytes);
        } catch (Exception e) {
            System.err.println("ERROR: Hashing string failed!");
        }
        return "hashingFailed";
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
}
