import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Socket connectionSocket;
    String clientSentence;
    String capitalizedSentence;

    public ServerThread(Socket connection) {
        this.connectionSocket = connection;
    }

    @Override
    public void run() {
        while(true) {
            try {
                DataInputStream inFromClient = null;
                inFromClient = new DataInputStream(connectionSocket.getInputStream());
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                String clientSentence = inFromClient.readUTF();
                String capitalizedSentence = clientSentence.toUpperCase();
                outToClient.writeUTF(capitalizedSentence);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
