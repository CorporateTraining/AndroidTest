package com.example.androidtestgit.repository.user.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private final static String MD5 = "MD5";

    public static String md5(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(message.getBytes());
            byte[] messageDigests = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte messageDigest : messageDigests) {
                StringBuilder hex = new StringBuilder(Integer.toHexString(0xFF & messageDigest));
                while (hex.length() < 2) {
                    hex.insert(0, "0");
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
