package echoserver;

import com.sun.org.glassfish.external.statistics.Statistic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
    private Socket clientSocket;
    private boolean running;
    private Statistic stats;

    public Connection(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void sendToClient(String message) {

    }

    @Override
    public void run() {
        while(true) {
            try {
                DataInputStream inFromClient = null;
                inFromClient = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
                String clientSentence = inFromClient.readUTF();
                String capitalizedSentence = clientSentence.toUpperCase();
                outToClient.writeUTF(capitalizedSentence);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Statistic getStats() {
        return stats;
    }
}
