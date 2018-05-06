package echoserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class Connection extends Thread {
    private Socket clientSocket;
    private boolean running;
    private Statistic stats;
    private DataOutputStream outToClient;
    private DataInputStream inFromClient;

    public Connection(Socket clientSocket) throws IOException {
        this.stats = new Statistic();
        this.clientSocket = clientSocket;
        this.running = true;
        outToClient = new DataOutputStream(clientSocket.getOutputStream());
        inFromClient = new DataInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        assert inFromClient != null;

        while(running) {
            try {
                String clientSentence = inFromClient.readUTF();
                stats.add(clientSentence.length());

                if (clientSentence.contains("\\exit")) {
                    running = false;
                    sendToClient(clientSentence.toUpperCase());
                } else if (clientSentence.contains("\\showstat")) {
                    sendToClient(getStats().toString());
                } else if (clientSentence.contains("\\showallstat")) {
                    StringBuilder response = new StringBuilder();
                    Map<String, String> allStats = Server.getInstance().getAllStats();
                    for (String ip : allStats.keySet()) {
                        response.append(ip).append(":\n");
                        response.append(allStats.get(ip));
                        response.append("\n");
                    }
                    sendToClient(response.toString());
                } else if (clientSentence.startsWith("\\broadc")) {
                    Server.getInstance().broadcast(clientSentence.substring("\\broadc ".length()));
                } else {
                    sendToClient(clientSentence.toUpperCase());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Statistic getStats() {
        return stats;
    }

    public String getClientIP() {
        return clientSocket.getInetAddress().toString();
    }

    public void sendToClient(String message) throws IOException {
        if (running) {
            assert outToClient != null;
            outToClient.writeUTF(message);
        }
    }
}
