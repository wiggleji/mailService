package com.example.mailService.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@UtilityClass
public class Encryption {

    private final static String secretKey = "RB15AW0JUI7ZL8BX";
    public static String ALGORITHM = "AES";

    private static final String encryptedSalt = "AES/" + secretKey.substring(0,4) + "/";


    public String encryptAES256(String text) {
        try {
            if (text.startsWith(encryptedSalt)) {
                // 암호화된 데이터는 그대로 반환
                return text;
            } else {
                // 암호화 진행
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

                byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
                return encryptedSalt + Base64.getEncoder().withoutPadding().encodeToString(encrypted);

            }
        } catch (Exception e) {
            log.error("Error while encrypting value: " + text);
            throw new RuntimeException(e);
        }
    }

    public String decryptAES256(String cipherTextWithIdentifier) {
        try {
            if (cipherTextWithIdentifier.startsWith(encryptedSalt)) {
                String cipherText = cipherTextWithIdentifier.substring(
                        cipherTextWithIdentifier.lastIndexOf('/') + 1);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

                byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
                byte[] decrypted = cipher.doFinal(decodedBytes);
                return new String(decrypted, StandardCharsets.UTF_8);

            } else throw new IllegalArgumentException("Invalid cipherText: " + cipherTextWithIdentifier);
        } catch (Exception e) {
            log.error("Error while decrypting value: " + cipherTextWithIdentifier);
            throw new RuntimeException(e);
        }
    }

    public boolean isEncrypted(String cipherText) {
        return cipherText.startsWith("AES__");
    }
}
