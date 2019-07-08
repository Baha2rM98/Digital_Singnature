import signature.DigitalSignature;

import java.io.File;
import java.io.IOException;

public class DSMain {
    public static void main(String[] args) throws IOException {
        File path = new File("C:\\Users\\Baha2r\\Desktop\\Texts");
        File file = new File(path, "1.txt");
        DigitalSignature ds = new DigitalSignature(file);
        File file2 = ds.writeBinaryFile(path, "2", "salam dada!");
        String s = ds.makeSignFromBinaryFile(file2);
        ds.isVerified(s);
    }
}