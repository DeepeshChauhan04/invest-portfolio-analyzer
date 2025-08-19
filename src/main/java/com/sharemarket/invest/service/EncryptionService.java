package com.sharemarket.invest.service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EncryptionService {

    private final StringEncryptor stringEncryptor;

    public String encrypt(String plainText) {
        if (plainText == null || plainText.trim().isEmpty()) {
            return null;
        }
//log.debug("Encrypting: {}", plainText);
//System.out.println("Encrypting: " + stringEncryptor);
        String encryptedText = stringEncryptor.encrypt(plainText);
//log.debug("Encrypted: {}", encryptedText);
        return encryptedText;
    }
    public String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.trim().isEmpty()) {
            return null;
        }
        log.debug("Decrypting: {}", encryptedText);
        String decryptedText = stringEncryptor.decrypt(encryptedText);
        log.debug("Decrypted: {}", decryptedText);
        return decryptedText;
    }
}
