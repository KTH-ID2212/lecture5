package se.kth.id2212.lecture5;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Writes a number of <code>int</code> values to a file.
 */
public class DataOutputStreamExample {
    public static void main(String[] args) {
        int noOfNumbers = 1000;

        String fileName = "numbers.dat";
        try (DataOutputStream outData
                = new DataOutputStream(new FileOutputStream(fileName))) {
            for (int number = 0; number < noOfNumbers; number++) {
                outData.writeInt(number);
            }
            outData.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
