package ru.tigran.urlshortener.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PasswordEncoder {
    private static final Random random = new SecureRandom();
    private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int iterations = 10000;
    private static final int keyLength = 256;

    public static String generateSalt(int length)
    {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++)
        {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    public static byte[] hash(char[] password, byte[] salt)
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        Arrays.fill(password, Character.MIN_VALUE);
        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
        finally
        {
            spec.clearPassword();
        }
    }

    public static String encode(String password, String salt)
    {
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    public static boolean verifyPassword(String providedPassword, String securedPassword, String salt)
    {
        String newSecurePassword = encode(providedPassword, salt);
        return newSecurePassword.equalsIgnoreCase(securedPassword);
    }
}
