package com.example.mailService.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EncryptionTest {
    private final Encryption encryption;

    @Autowired
    public EncryptionTest(Encryption encryption) {
        this.encryption = encryption;
    }

    @Test
    public void Encryption__encryptAES256_plainText() throws Exception {
        // given
        final String validateText = "testText";

        // when
        String encryptAES256 = encryption.encryptAES256(validateText);

        // then
        assertThat(encryptAES256).startsWith("AES");
    }

    @Test
    public void Encryption__decryptAES256_cipherText() throws Exception {
        // given
        final String validateText = "testText";

        // when
        String encryptAES256 = encryption.encryptAES256(validateText);
        String plainText = encryption.decryptAES256(encryptAES256);

        // then
        assertThat(plainText).isEqualTo(validateText);
    }
}