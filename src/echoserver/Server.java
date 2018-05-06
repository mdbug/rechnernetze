package echoserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
        connections = new ArrayList<>();
        running = true;
    }

    public void broadcast(String message) throws IOException {
        for (Connection connection : connections) {
            connection.sendToClient(message);
        }
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
        Map<String, Statistic> stats = new HashMap<>();
        for(Connection connection : connections) {
            String ip = connection.getClientIP();
            Statistic stat = connection.getStats();
            if (!stats.containsKey(ip)) {
                stats.put(ip, new Statistic(stat));
            }
            else {
                stats.get(ip).merge(stat);
            }
        }

        Map<String, String> statsStrings = new HashMap<>();

        for (String ip : stats.keySet()) {
            statsStrings.put(ip, stats.get(ip).toString());
        }

        return statsStrings;
    }
}
