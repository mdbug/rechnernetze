package echoserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerListener extends Thread {
    private Socket socket;
    private DataInputStream inFromServer;

    public ServerListener(Socket socket) throws IOException {
        this.socket = socket;
        DataInputStream inFromServer = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {

    }

    public void processReceivedMessage(String message) {

    }

    public String waitForMessage() throws IOException {
        return inFromServer.readUTF();
    }
}
