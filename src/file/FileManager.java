package file;

/**
 * @author Baha2r
 **/

import java.io.*;
import java.util.Scanner;

public abstract class FileManager {
    private final String BinarySuffix = ".bin";
    private final String TXTSuffix = ".txt";

    protected FileManager() {
    }

    private File makeFile(File directory, String fileName) throws IOException {
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

    private File makeBinaryFile(File directory, String fileName) throws IOException {
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

    protected String readBinaryFile(File file) throws IOException {
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

    protected void deleteFile(File file) {
        if (!file.exists()) {
            System.err.println("There is not anything to delete!");
            return;
        }
        if (file.exists())
            file.delete();
    }

    protected String readFile(File file) throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        StringBuilder textBuilder = new StringBuilder();
        while (reader.hasNextLine()) {
            textBuilder.append(reader.nextLine());
        }
        return textBuilder.toString();
    }

    protected File writeBinaryFile(File directory, String fileName, String text) throws IOException {
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

    protected File writeFile(File directory, String fileName, String text) throws IOException {
        File file = makeFile(directory, fileName);
        assert file != null;
        FileWriter fw = new FileWriter(file);
        fw.write(text);
        fw.flush();
        fw.close();
        return file;
    }

    protected boolean isTextFile(File file) {
        return file.getName().contains(TXTSuffix);
    }

    protected boolean isBinaryFile(File file) {
        return file.getName().contains(BinarySuffix);
    }

}
