package signature;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Created by Baha2r

class SHA_512 {

    SHA_512() {
    }

    private static String SHA512_hashing(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger bytes = new BigInteger(1, messageDigest);
            StringBuilder hashedText = new StringBuilder(bytes.toString(16));
            while (hashedText.length() < 32) {
                hashedText.insert(0, "0");
            }
            return hashedText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    String SHA512(String text) {
        return SHA512_hashing(text);
    }
}

