package com.DanielNaiff.encryption;

import com.DanielNaiff.encryption.security.converter.AESConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AESConverterTest {

    private AESConverter converter;

    @BeforeEach
    public void setup() {
        System.setProperty("APP_KEY", "1234567890123456"); // chave 16 chars (128 bits)
        converter = new AESConverter();
    }

    @Test
    public void testEncryptionDecryption() {
        String original = "Texto sensível";

        // Criptografa
        String encrypted = converter.convertToDatabaseColumn(original);
        assertNotNull(encrypted);
        assertNotEquals(original, encrypted, "Texto criptografado deve ser diferente do original");

        // Descriptografa
        String decrypted = converter.convertToEntityAttribute(encrypted);
        assertEquals(original, decrypted, "Texto descriptografado deve ser igual ao original");
    }

    @Test
    public void testNullHandling() {
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }
}
