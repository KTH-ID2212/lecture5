package se.kth.id2212.lecture5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * This server reads a message and echoes it back to the sender. All socket operations (accept,
 * read, write) are non-blocking. This simple server has one single thread which performs all
 * operations. In reality, we would use a thread pool. All threads in the pool would pick a task
 * (accept, read, write), execute it, pick another task, etc.
 */
public class EchoServer {

    private static final int MAX_MSG_LENGTH = 1024;
    private ServerSocketChannel serverChannel;
    private Selector selector;

    /**
     * Creates a new instance, which will listen on the specified port.
     *
     * @param port The port to which this server will listen.
     * @throws IOException If unable to bind the listening socket.
     */
    public EchoServer(int port) throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * There are no command line arguments.
     */
    public static void main(String[] args) {
        int port = 4444;

        try {
            EchoServer server = new EchoServer(port);
            server.serve();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void serve() throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys()
                    .iterator();

            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isAcceptable()) {
                    acceptConnection(key);
                } else if (key.isReadable()) {
                    readFromClient(key);
                } else if (key.isWritable()) {
                    writeToClient(key);
                }
            }
        }
    }

    private void acceptConnection(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key
                .channel();
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ,
                ByteBuffer.allocate(MAX_MSG_LENGTH));

    }

    private void readFromClient(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        channel.read(buffer);
        key.interestOps(SelectionKey.OP_READ
                | SelectionKey.OP_WRITE);
    }

    private void writeToClient(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        channel.write(buffer);
        if (buffer.hasRemaining()) {
            buffer.compact();
        } else {
            buffer.clear();
        }
        key.interestOps(SelectionKey.OP_READ);
    }
}
