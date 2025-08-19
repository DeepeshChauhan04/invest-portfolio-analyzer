package com.sharemarket.invest.converter;


import com.sharemarket.invest.service.EncryptionService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@Converter
public class SensitiveDataConverter implements AttributeConverter<String, String> {

    @Autowired
    private EncryptionService encryptionService;

    @Override
    public String convertToDatabaseColumn(String sensitiveInfo) {

        if (sensitiveInfo == null || sensitiveInfo.trim().isEmpty()) {
            return null;
        }
        if (encryptionService == null) {
            log.warn("EncryptionService not yet initialized in SensitiveDataConverter. Skipping encryption for: {}", sensitiveInfo);
            return sensitiveInfo;
        }
        return encryptionService.encrypt(sensitiveInfo);
    }

    @Override
    public String convertToEntityAttribute(String encryptedInfo) {
        if (encryptedInfo == null || encryptedInfo.trim().isEmpty()) {
            return null;
        }
        if (encryptionService == null) {
            log.warn("EncryptionService not yet initialized in SensitiveDataConverter. Skipping decryption for: {}", encryptedInfo);
            return encryptedInfo;
        }
        return encryptionService.decrypt(encryptedInfo);
    }
}

