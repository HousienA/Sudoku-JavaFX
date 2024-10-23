package housienariel.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SudokuFileHandler {
    public static void saveToFile(SudokuModel model, File file) throws IOException {
        if (file == null) throw new IllegalArgumentException("File cannot be null.");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(model);
        }
    }

    public static SudokuModel loadFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (SudokuModel) ois.readObject();
        }
    }
}