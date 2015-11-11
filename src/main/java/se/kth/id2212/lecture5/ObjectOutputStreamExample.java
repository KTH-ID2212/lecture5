package se.kth.id2212.lecture5;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializes an object and writes it to a file.
 */
public class ObjectOutputStreamExample {
    public static void main(String[] args) {
        int noOfNumbers = 10;
        List<Integer> numbers = new ArrayList<>();
        String fileName = "list.ser";

        for (int number = 0; number < noOfNumbers; number++) {
            numbers.add(number);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            out.writeObject(numbers);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
