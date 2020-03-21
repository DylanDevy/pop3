package security.service;

public interface EncryptorInterface {
    String encrypt(String value);
    boolean matches(String matchValue, String encryptedValue);
}
