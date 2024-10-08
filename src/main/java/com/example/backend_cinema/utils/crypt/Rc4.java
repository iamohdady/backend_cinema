package com.example.backend_cinema.utils.crypt;

import com.example.backend_cinema.utils.model.Constants;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Rc4 {

    public static String decryptMd5(String encrypted, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(Constants.ALGORITHM_RC4);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(CryptUtils.hashMd5(key), Constants.ALGORITHM_RC4));
        return new String(cipher.doFinal(CryptUtils.base64Decode(encrypted)));
    }

    public static String decryptEnc(String encrypted, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(Constants.ALGORITHM_RC4);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), Constants.ALGORITHM_RC4));
        return new String(cipher.doFinal(CryptUtils.hexStringToByteArray(encrypted)));
    }

    public static String encrypt(String raw, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(Constants.ALGORITHM_RC4);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(CryptUtils.hashMd5(key), Constants.ALGORITHM_RC4));
        return CryptUtils.base64Encode(cipher.doFinal(raw.getBytes()));
    }
}
