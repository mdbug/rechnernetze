package echoserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Server {
    private int port;
    private List<Connection> connections;
    private boolean running;
    private static Server instance;
    private ServerSocket serverSocket;

    public static Server getInstance() throws IOException {
        if (Server.instance == null) {
            Server.instance = new Server();
        }

        return Server.instance;
    }

    private Server() throws IOException {
        Config config = new Config("config.properties");
        port = config.getPort();
        serverSocket = new ServerSocket(port);
        running = true;
    }

    public void broadcast(String message) {

    }

    public static void main(String[] args) throws IOException {
        Server server = Server.getInstance();

        while(server.running) {
            Socket connectionSocket = server.serverSocket.accept();
            Connection connection = new Connection(connectionSocket);
            server.connections.add(connection);
            connection.start();
        }
    }

    public Map<String, String> getAllStats() {
        return null;
    }

}
