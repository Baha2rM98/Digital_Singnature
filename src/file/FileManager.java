package file;

import java.io.*;
import java.util.Scanner;

public abstract class FileManager {
    private final String BinarySuffix = ".bin";
    private final String TXTSuffix = ".txt";
    private FileOutputStream fos;
    private ObjectOutputStream os;
    private FileInputStream fis;
    private ObjectInputStream ois;
    private Scanner reader;
    private FileWriter fw;

    protected FileManager() {
    }

    public File makeFile(File directory, String fileName) throws IOException {
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        if (!directory.exists())
            directory.mkdirs();
        fileName = fileName + TXTSuffix;
        File file = new File(directory, fileName);
        if (!file.exists())
            file.createNewFile();
        return file;
    }

    public File makeBinaryFile(File directory, String fileName) throws IOException {
        if (!directory.isDirectory()) {
            System.err.println("This is not a directory!");
            return null;
        }
        if (!directory.exists())
            directory.mkdirs();
        fileName = fileName + BinarySuffix;
        File file = new File(directory, fileName);
        if (!file.exists())
            file.createNewFile();
        return file;
    }

    public String readBinaryFile(File file) throws IOException {
        fis = new FileInputStream(file);
        ois = new ObjectInputStream(fis);
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

    public void deleteFile(File file) {
        if (!file.exists()) {
            System.err.println("There is not anything to delete!");
            return;
        }
        if (file.exists())
            file.delete();
    }

    public String readFile(File file) throws FileNotFoundException {
        reader = new Scanner(file);
        StringBuilder textBuilder = new StringBuilder();
        while (reader.hasNextLine()) {
            textBuilder.append(reader.nextLine());
        }
        return textBuilder.toString();
    }

    public File writeBinaryFile(File directory, String fileName, String text) throws IOException {
        File file = makeBinaryFile(directory, fileName);
        assert file != null;
        fos = new FileOutputStream(file);
        os = new ObjectOutputStream(fos);
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
        fw = new FileWriter(file);
        fw.write(text);
        fw.flush();
        fw.close();
        return file;
    }

    public boolean isBinaryFile(File file) throws FileNotFoundException {
        String text = readFile(file);
        return text.length() == 0 || file.getName().equals(BinarySuffix);
    }

    public boolean isTxtFile(File file) throws FileNotFoundException {
        String text = readFile(file);
        return text.length() != 0 || file.getName().equals(TXTSuffix);
    }
}