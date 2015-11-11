package se.kth.id2212.lecture5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Reads an object from a file and deserializes it.
 */
public class ObjectInputStreamExample {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        String fileName = "list.ser";
        List<Integer> numbers;

        try (ObjectInputStream in
                = new ObjectInputStream(new FileInputStream(fileName))) {
            numbers = (List<Integer>) in.readObject();

            for (int number : numbers) {
                System.out.println("No " + number);
            }
        } catch (FileNotFoundException e) {
            System.err.
                    println("file " + fileName + " is mising");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
