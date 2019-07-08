package signature;

//Created by Baha2r

import file.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DigitalSignature extends FileManager {

    private static String sign;
    private static RSA rsa = new RSA();
    private static SHA_512 sha512 = new SHA_512();

    public DigitalSignature() {
        super();
    }

    public DigitalSignature(String text) {
        super();
        String hashedValue = sha512.SHA512(text);
        sign = rsa.encryption(hashedValue);
    }

    public DigitalSignature(File file) throws IOException {
        super();
        if (isBinaryFile(file)) {
            String text = readBinaryFile(file);
            String hashedValue = sha512.SHA512(text);
            sign = rsa.encryption(hashedValue);
        }
        if (isTextFile(file)) {
            String text = readFile(file);
            String hashedValue = sha512.SHA512(text);
            sign = rsa.encryption(hashedValue);
        }
    }

    public String makeSignFromText(String text) {
        String hashedValue = sha512.SHA512(text);
        return rsa.encryption(hashedValue);
    }

    public String makeSignFromFile(File file) throws FileNotFoundException {
        String text = readFile(file);
        String hashedValue = sha512.SHA512(text);
        return rsa.encryption(hashedValue);
    }

    public String makeSignFromBinaryFile(File file) throws IOException {
        String text = readBinaryFile(file);
        String hashedValue = sha512.SHA512(text);
        return rsa.encryption(hashedValue);
    }

    public void isVerified(String sign) {
        if (isSignVerified(sign))
            System.out.println("Your message is verified!");
        else
            System.out.println("Your message is not verified!");
    }

    public void isVerified(String sign_1, String sign_2) {
        if (isSignVerified(sign_1, sign_2))
            System.out.println("Your message is verified!");
        else
            System.out.println("Your message is not verified!");
    }

    private boolean isSignVerified(String sign) {
        String hash_1 = rsa.decryption(getSign());
        String hash_2 = rsa.decryption(sign);
        return hash_1.equals(hash_2);
    }

    private boolean isSignVerified(String sign_1, String sing_2) {
        String hash_1 = rsa.decryption(sign_1);
        String hash_2 = rsa.decryption(sing_2);
        return hash_1.equals(hash_2);
    }

    private String getSign() {
        return sign;
    }
}
