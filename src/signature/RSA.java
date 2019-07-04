package signature;

import java.math.BigInteger;
import java.security.SecureRandom;

//Created by Baha2r

class RSA {
    private static BigInteger n;
    private static BigInteger d;
    private static BigInteger e;
    private static String SDT;
    private static String SET;

    RSA() {
        try {
            final int N = 512;
            final int C = 2;
            final BigInteger ONE = BigInteger.ONE;
            SecureRandom secureRandom = new SecureRandom();
            BigInteger p = BigInteger.probablePrime(N, secureRandom);
            BigInteger q = BigInteger.probablePrime(N, secureRandom);
            n = p.multiply(q);
            BigInteger Phi = p.subtract(ONE).multiply(q.subtract(ONE));
            e = BigInteger.probablePrime((N / C), secureRandom);
            while (Phi.gcd(e).intValue() != 1) {
                e = e.add(ONE);
            }
            d = e.modInverse(Phi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String encryption(String Message) {
        try {
            SET = new BigInteger(Message.getBytes()).modPow(d, n).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SET;
    }

    String decryption(String encryptedMessage) {
        try {
            SDT = new String(new BigInteger(encryptedMessage).modPow(e, n).toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SDT;
    }
}