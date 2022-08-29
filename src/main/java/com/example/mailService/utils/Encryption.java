package com.example.mailService.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Component
public class Encryption {

    @Value("${encryption.secret-key}")
    private String secretKey;

    public static String algorithm = "AES/CBC/PKCS5Padding";
    private final static String initialVector = "mailEncrypt12345";

    public String encryptAES256(String text) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("Error while encrypting value: " + text);
            throw new RuntimeException(e);
        }
    }

    public String decryptAES256(String cipherText) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Error while decrypting value: " + cipherText);
            throw new RuntimeException(e);
        }
    }
}
