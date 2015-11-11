package se.kth.id2212.lecture5;

import java.nio.CharBuffer;

/**
 * Writes a reversed string to a buffer, reads that string from the buffer and prints it
 * to <code>System.out</code>.
 */
public class BufferTest {
    public static void main(String[] args) {
        String s = "Some String";
        CharBuffer buf = CharBuffer.allocate(s.length());

        // put reversed s in to buf
        for (int i = s.length() - 1; i >= 0; i--) {
            buf.put(s.charAt(i)); // relative put
        }
        buf.flip(); // limit = position; position = 0

        StringBuffer read = new StringBuffer();
        while (buf.hasRemaining()) {
            read.append(buf.get()); //relative get
        }
        System.out.println(read);
    }
}
