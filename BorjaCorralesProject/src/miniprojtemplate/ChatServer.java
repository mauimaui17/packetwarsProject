package miniprojtemplate;
import java.io.*;
import java.net.*;

public class ChatServer {
    public static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Listening on port " + PORT);

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }
                System.out.println("Received message: " + message);
                out.println("Received message: " + message);
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}