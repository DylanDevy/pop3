package security.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class Encryptor implements EncryptorInterface {
    private final int ITERATIONS = 10000;
    private final int PBE_KEY_LENGTH = 256;
    private final int SALT_LENGTH = 32;
    private final String DELIMITER = ":";

    public String encrypt(String password) {
        byte[] salt = getSecureRandomBytes(this.SALT_LENGTH);

        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, this.ITERATIONS, this.PBE_KEY_LENGTH);

        return DatatypeConverter.printHexBinary(getEncodedHash(pbeKeySpec)) + this.DELIMITER + DatatypeConverter.printHexBinary(salt);
    }

    public boolean matches(String matchPassword, String storedPassword) {
        String[] hashSalt = storedPassword.split(this.DELIMITER);
        byte[] hash = DatatypeConverter.parseHexBinary(hashSalt[0]);
        byte[] salt = DatatypeConverter.parseHexBinary(hashSalt[1]);

        PBEKeySpec pbeKeySpec = new PBEKeySpec(matchPassword.toCharArray(), salt, this.ITERATIONS, this.PBE_KEY_LENGTH);

        return byteEquals(hash, getEncodedHash(pbeKeySpec));
    }

    private boolean byteEquals(byte[] hash, byte[] matchHash) {
        int diff = hash.length ^ matchHash.length;
        for(int i = 0; i < hash.length && i < matchHash.length; i++) {
            diff |= hash[i] ^ matchHash[i];
        }

        return diff == 0;
    }

    private byte[] getEncodedHash(PBEKeySpec pbeKeySpec) {
        byte[] hash = new byte[0];

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            try {
                assert secretKeyFactory != null;
                hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash;
    }

    private byte[] getSecureRandomBytes(int length) {
        byte[] bytes = new byte[length];
        try {
            SecureRandom.getInstanceStrong().nextBytes(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return bytes;
    }
}
