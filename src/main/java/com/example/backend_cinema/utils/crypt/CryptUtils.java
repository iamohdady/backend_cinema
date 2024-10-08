package com.example.backend_cinema.utils.crypt;

import com.example.backend_cinema.utils.model.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptUtils {

    public static byte[] hashMd5(String text) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(Constants.ALGORITHM_MD5).digest(text.getBytes());
    }

    public static byte[] hashSha256(String text) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(Constants.ALGORITHM_SHA256).digest(text.getBytes());
    }

    public static byte[] hashSha256(byte[] bytes) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(Constants.ALGORITHM_SHA256).digest(bytes);
    }

    public static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64Decode(String text) {
        return Base64.getDecoder().decode(text);
    }

    public static String byteArrayToHexString(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
