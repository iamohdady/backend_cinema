package com.example.backend_cinema.utils.crypt;

import com.example.backend_cinema.utils.model.Constants;
import com.example.backend_cinema.utils.model.Pair;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Aes {

    public static Pair<String, String> encryptGcm(String raw, String key) throws Exception {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        Cipher cipher = Cipher.getInstance(Constants.ALGORITHM_AES_GCM);
        cipher.init(
            Cipher.ENCRYPT_MODE,
            new SecretKeySpec(CryptUtils.hashSha256(key), Constants.ALGORITHM_AES),
            new GCMParameterSpec(128, iv)
        );
        byte[] encrypted = cipher.doFinal(raw.getBytes());

        return new Pair<>(CryptUtils.base64Encode(iv), CryptUtils.base64Encode(encrypted));
    }

    public static String decryptGcm(String iv, String encrypted, String key) throws Exception {
        byte[] encryptedBytes = CryptUtils.base64Decode(encrypted);
        byte[] ivBytes = CryptUtils.base64Decode(iv);

        Cipher cipher = Cipher.getInstance(Constants.ALGORITHM_AES_GCM);
        cipher.init(
            Cipher.DECRYPT_MODE,
            new SecretKeySpec(CryptUtils.hashSha256(key), Constants.ALGORITHM_AES),
            new GCMParameterSpec(128, ivBytes)
        );
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}

