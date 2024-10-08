package com.example.backend_cinema.utils.crypt;


import com.example.backend_cinema.utils.model.Constants;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSha256 {

    public static boolean verify(String raw, String encrypted, String key) throws Exception {
        return encrypted.equals(encryptThenBase64Encode(raw, key));
    }

    public static String encryptThenBase64Encode(String raw, String key) throws Exception {
        Mac hmac = Mac.getInstance(Constants.ALGORITHM_HMAC_SHA256);
        hmac.init(new SecretKeySpec(key.getBytes(), Constants.ALGORITHM_HMAC_SHA256));
        return CryptUtils.base64Encode(hmac.doFinal(raw.getBytes()));
    }

    public static String encryptThenHexString(String raw, String key) throws Exception {
        Mac hmac = Mac.getInstance(Constants.ALGORITHM_HMAC_SHA256);
        hmac.init(new SecretKeySpec(key.getBytes(), Constants.ALGORITHM_HMAC_SHA256));
        return CryptUtils.byteArrayToHexString(hmac.doFinal(raw.getBytes()));
    }
}
