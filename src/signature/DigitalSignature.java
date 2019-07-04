package signature;

//Created by Baha2r

import java.io.*;
import java.util.Scanner;

public class DigitalSignature {

    private static String sign;
    private static RSA rsa = new RSA();
    private static SHA_512 sha512 = new SHA_512();
    private final String BinaryPostFix = ".bin";
    private final String TXTPostFix = ".txt";

    public DigitalSignature() {
    }

    public DigitalSignature(String text) {
        String hashedValue = sha512.SHA512(text);
        sign = rsa.encryption(hashedValue);
    }

    public DigitalSignature(File file) throws IOException {
        if (file.getName().contains(BinaryPostFix)) {
            String text = readBinaryFile(file);
            String hashedValue = sha512.SHA512(text);
            sign = rsa.encryption(hashedValue);
        }
        if (file.getName().contains(TXTPostFix)) {
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

    public File writeBinaryFile(File directory, String fileName, String text) throws IOException {
        File file = makeBinaryFile(directory, fileName);
        assert file != null;
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.write(CharacterHider.hide(text));
        os.flush();
        os.close();
        fos.flush();
        fos.close();
        return file;
    }

    public File writeFile(File directory, String fileName, String text) throws IOException {
        File file = makeFile(directory, fileName);
        assert file != null;
        FileWriter fw = new FileWriter(file);
        fw.write(text);
        fw.flush();
        fw.close();
        return file;
    }

    public void deleteFile(File file) {
        if (!file.exists()) {
            System.err.println("There is not anything to delete!");
            return;
        }
        if (file.exists())
            file.delete();
    }

    public String readFile(File file) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        StringBuilder textBuilder = new StringBuilder();
        while (reader.hasNextLine()) {
            textBuilder.append(reader.nextLine());
        }
        return textBuilder.toString();
    }

    public String readBinaryFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        final int size = ois.available();
        byte[] bytes = new byte[size];
        for (int i = 0; i < size; i++) {
            bytes[i] = ois.readByte();
        }
        byte[] buf = CharacterHider.show(bytes);
        ois.close();
        fis.close();
        return new String(buf);
    }

    private File makeBinaryFile(File directory, String fileName) throws IOException {
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        if (!directory.exists())
            directory.mkdirs();
        fileName = fileName + BinaryPostFix;
        File file = new File(directory, fileName);
        if (!file.exists())
            file.createNewFile();
        return file;
    }

    private File makeFile(File directory, String fileName) throws IOException {
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        if (!directory.exists())
            directory.mkdirs();
        fileName = fileName + TXTPostFix;
        File file = new File(directory, fileName);
        if (!file.exists())
            file.createNewFile();
        return file;
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
