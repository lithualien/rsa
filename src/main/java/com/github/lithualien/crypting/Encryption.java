package com.github.lithualien.crypting;

import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Encryption {
    public static String encrypt(PrivateKey privateKey, String message) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA512withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(message.getBytes(UTF_8));
        byte[] signature = privateSignature.sign();
        return Base64.getEncoder().encodeToString(signature);
    }
}
