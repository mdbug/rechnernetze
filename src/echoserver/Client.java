package echoserver;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private ServerListener serverListener;
    private DataOutputStream outToServer;
    private boolean running;
    private Socket socket;

    public Client() throws IOException {
        Config config = new Config("config.properties");
        int port = config.getPort();
        socket = new Socket("localhost", port);
        serverListener = new ServerListener(socket);
        serverListener.start();
        outToServer = new DataOutputStream(socket.getOutputStream());
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();

        String response = "";
        Scanner scanner = new Scanner(System.in);
        while(!response.contains("\\EXIT")) {
            if (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                client.sendToServer(message);
            }
            //response = client.serverListener.waitForMessage();
            //System.out.println("FROM SERVER: " + response);
        }
        client.stop();
    }

    public void sendToServer(String message) throws IOException {
        outToServer.writeUTF(message + '\n');
    }

    public void stop() throws IOException {
        socket.close();
    }

    public boolean isRunning() {
        return running;
    }
}
