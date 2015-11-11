package se.kth.id2212.lecture5;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Reads a number of <code>int</code> values from a file.
 */
public class DataInputStreamExample {
    public static void main(String[] args) {
        int no;
        String fileName = "numbers.dat";

        try (DataInputStream inData
                = new DataInputStream(new FileInputStream(fileName))) {
            while (true) {
                no = inData.readInt();
                System.out.println("No " + no);
            }
        } catch (EOFException done) {
        } catch (FileNotFoundException e) {
            System.err.println("file " + fileName + " is mising");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
