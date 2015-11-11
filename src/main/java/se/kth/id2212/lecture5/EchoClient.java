package se.kth.id2212.lecture5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Sends a message to a server and prints the response to <code>System.out</code>.
 */
public class EchoClient {
    
    /**
     * The command line arguments are server host, server port and 
     * message to send (in that order).
     */
    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String message = args[2];

        WritableByteChannel out = Channels.newChannel(System.out);
        try {
            ByteBuffer buf;
            try (SocketChannel channel = SocketChannel.open(new InetSocketAddress(
                    host, port))) {
                buf = ByteBuffer.wrap(message.getBytes());
                channel.write(buf);
                buf = ByteBuffer.allocate(1024);
                channel.read(buf);
            }
            buf.flip();
            out.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
