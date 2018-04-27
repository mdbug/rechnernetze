import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class TCPClient {
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence = "";
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Config config = new Config("config.properties");
        int port = config.getPort();
        Socket clientSocket = new Socket("localhost", port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
        while(!modifiedSentence.contains("\\EXIT")) {
            sentence = inFromUser.readLine();
            outToServer.writeUTF(sentence + '\n');
            modifiedSentence = inFromServer.readUTF();
            System.out.println("FROM SERVER: " + modifiedSentence);
        }
        clientSocket.close();
    }
}
