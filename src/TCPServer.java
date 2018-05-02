import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class TCPServer {
    public static void main(String argv[]) throws Exception {
        Config config = new Config("config.properties");
        int port = config.getPort();
        ServerSocket serverSocket = new ServerSocket(port);
        boolean flag = true;
        while(true) {
            Socket connectionSocket = serverSocket.accept();
            new Thread(new ServerThread(connectionSocket)).start();
        }
        serverSocket.close();
    }
}
