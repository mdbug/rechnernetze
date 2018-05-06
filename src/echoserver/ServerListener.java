package echoserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerListener extends Thread {
    private Socket socket;
    private DataInputStream inFromServer;

    public ServerListener(Socket socket) throws IOException {
        this.socket = socket;
        inFromServer = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while(true) {
            try {
                String message = waitForMessage();
                System.out.println("FROM SERVER:\n" + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String waitForMessage() throws IOException {
        return inFromServer.readUTF();
    }
}
