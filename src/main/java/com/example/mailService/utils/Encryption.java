package com.example.mailService.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Encryption {

    @Value("${encryption.secret-key}")
    private String secretKey;
    private String ALGORITHM = "AES";

    private String getEncryptedSalt() {
        return "AES/" + secretKey.substring(0,4) + "/";
    }

    public String encryptAES256(String text) {
        try {
            if (text.startsWith(getEncryptedSalt())) {
                // 암호화된 데이터는 그대로 반환
                return text;
            } else {
                // 암호화 진행
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

                byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
                return getEncryptedSalt() + Base64.getEncoder().withoutPadding().encodeToString(encrypted);

            }
        } catch (Exception e) {
            log.error("Error while encrypting value: " + text);
            throw new RuntimeException(e);
        }
    }

    public String decryptAES256(String cipherTextWithSalt) {
        try {
            if (cipherTextWithSalt.startsWith(getEncryptedSalt())) {
                String cipherText = cipherTextWithSalt.substring(
                        cipherTextWithSalt.lastIndexOf("/") + 1);
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

                byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
                byte[] decrypted = cipher.doFinal(decodedBytes);
                return new String(decrypted, StandardCharsets.UTF_8);

            } else throw new IllegalArgumentException("Invalid cipherText: " + cipherTextWithSalt);
        } catch (Exception e) {
            log.error("Error while decrypting value: " + cipherTextWithSalt);
            throw new RuntimeException(e);
        }
    }

    public boolean isEncrypted(String cipherText) {
        return cipherText.startsWith("AES__");
    }
}
