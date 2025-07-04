package com.sebasth.alifood.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    // Method to hash a password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // Replace with actual hashing logic
    }

    // Method to verify a password against a hashed password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
