package security.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class Encryptor implements EncryptorInterface {
    private final int iterations;
    private final int pbeKeyLength;
    private final int saltLength;
    private final String delimiter;

    private Encryptor(int iterations, int pbeKeyLength, int saltLength, String delimiter) {
        this.iterations = iterations;
        this.pbeKeyLength = pbeKeyLength;
        this.saltLength = saltLength;
        this.delimiter = delimiter;
    }

    public String encrypt(String value) {
        byte[] salt = getSecureRandomBytes(saltLength);

        PBEKeySpec pbeKeySpec = new PBEKeySpec(value.toCharArray(), salt, iterations, pbeKeyLength);

        return bytesToString(getEncodedHash(pbeKeySpec)) + delimiter + bytesToString(salt);
    }

    public boolean matches(String matchValue, String encryptedValue) {
        String[] hashSalt = encryptedValue.split(delimiter);
        byte[] hash = stringToBytes(hashSalt[0]);
        byte[] salt = stringToBytes(hashSalt[1]);

        PBEKeySpec pbeKeySpec = new PBEKeySpec(matchValue.toCharArray(), salt, iterations, pbeKeyLength);

        return byteEquals(hash, getEncodedHash(pbeKeySpec));
    }

    private boolean byteEquals(byte[] hash, byte[] matchHash) {
        int diff = hash.length ^ matchHash.length;
        for(int i = 0; i < hash.length && i < matchHash.length; i++) {
            diff |= hash[i] ^ matchHash[i];
        }

        return diff == 0;
    }

    private String bytesToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private byte[] stringToBytes(String string) {
        return Base64.getDecoder().decode(string);
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

    public static class Builder {
        private int iterations;
        private int pbeKeyLength;
        private int saltLength;
        private String delimiter;

        public Builder setIterations(int iterations) {
            this.iterations = iterations;

            return this;
        }

        public Builder setPbeKeyLength(int pbeKeyLength) {
            this.pbeKeyLength = pbeKeyLength;

            return this;
        }

        public Builder setSaltLength(int saltLength) {
            this.saltLength = saltLength;

            return this;
        }

        public Builder setDelimiter(String delimiter) {
            this.delimiter = delimiter;

            return this;
        }

        public Encryptor build() {
            return new Encryptor(iterations, pbeKeyLength, saltLength, delimiter);
        }
    }
}
