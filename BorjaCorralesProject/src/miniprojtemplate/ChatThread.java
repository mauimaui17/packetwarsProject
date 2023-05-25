package miniprojtemplate;
import java.io.*;
import java.net.*;
public class ChatThread {
    private final int port;

    public ChatThread(int port) {
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        while (true) {
            String message = in.readLine();
            if (message == null) {
                break;
            }
            System.out.println("Received message: " + message);

            // Process the message as needed
            // For example, you can send it to other connected clients

            // Send a response back to the client
            out.println("Received message: " + message);
        }

        clientSocket.close();
    }
}
