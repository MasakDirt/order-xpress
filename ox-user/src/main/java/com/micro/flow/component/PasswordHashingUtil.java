package com.micro.flow.component;

import com.micro.flow.exception.HashingPasswordException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
@Component
public class PasswordHashingUtil {
    private static final int SALT_LENGTH = 16;
    private static final int HASH_ITERATIONS = 27500;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    public String getSecretData(String password) {
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        JSONObject secretData = new JSONObject();
        secretData.put("value", hashedPassword);
        secretData.put("salt", salt);
        secretData.put("additionalParameters", new Object());

        return secretData.toString();
    }

    private String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, HASH_ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Error hashing password: ", e);
            throw new HashingPasswordException("An error occurred while hashing the password. Please try again later.");
        }
    }

    public String getCredentialData() {
        JSONObject credentialData = new JSONObject();
        credentialData.put("hashIterations", HASH_ITERATIONS);
        credentialData.put("algorithm", "pbkdf2-sha256");
        credentialData.put("additionalParameters", new Object());

        return credentialData.toString();
    }
}
