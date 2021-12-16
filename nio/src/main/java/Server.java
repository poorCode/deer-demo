import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author deer
 * @date 2021-08-01
 */
public class Server {
    private static int port = 7777;

    public static void main(String[] args) {

    }

    private Selector getSelector() throws IOException {
        // 实现类为 KQueueSelectorImpl
        Selector selector = Selector.open();

        // 返回 ServerSocketChannelImpl
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        InetSocketAddress socketAddress = new InetSocketAddress(port);
        socket.bind(socketAddress);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        return selector;
    }


}
