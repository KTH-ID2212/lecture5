package se.kth.id2212.lecture5;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class FileChannelTest {
    public static void main(String[] args) {
        String filename = (args.length > 0) ? args[0] : "test.txt";
        try {
            FileInputStream inf = new FileInputStream(filename);
            try (FileChannel channel = inf.getChannel()) {
                MappedByteBuffer buffer
                        = channel.map(FileChannel.MapMode.READ_ONLY,
                                      0, channel.size());
                WritableByteChannel out = Channels.newChannel(System.out);
                while (buffer.hasRemaining() && out.write(buffer) != -1) {
                    System.out.println("Writing the file " + filename);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
