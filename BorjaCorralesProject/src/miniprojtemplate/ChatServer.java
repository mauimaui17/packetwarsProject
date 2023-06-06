package miniprojtemplate;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    public static final int PORT = 6000;
    public static final String IP_ADDRESS = "0.0.0.0"; // Replace with the desired IP address

    private static List<Socket> connectedClients = new ArrayList<>();
    private static List<PrintWriter> clientWriters = new ArrayList<>(); // Stores the PrintWriter for each connected client

    public static void main(String[] args) {
        try {
            InetAddress ipAddress = InetAddress.getByName(IP_ADDRESS);
            ServerSocket serverSocket = new ServerSocket(PORT, 0, ipAddress);
            System.out.println("Server started. Listening on " + IP_ADDRESS + ":" + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                // Add the client socket to the list of connected clients
                connectedClients.add(clientSocket);

                // Create a new PrintWriter for the client
                PrintWriter clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(clientWriter);

                // Create a new thread for the client
                Thread clientThread = new Thread(new ClientHandler(clientSocket, clientWriter));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread class to handle communication with a client
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter clientWriter;

        public ClientHandler(Socket clientSocket, PrintWriter clientWriter) {
            this.clientSocket = clientSocket;
            this.clientWriter = clientWriter;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (true) {
                    String message = in.readLine();
                    if (message == null) {
                        break;
                    }
                    System.out.println("Received message from client: " + message);
                    clientWriter.println("Received message: " + message);

                    // Send the received message back to all connected clients
                    for (PrintWriter writer : clientWriters) {
                        writer.println(message);
                    }
                }

                // Remove the client socket and writer from the lists
                connectedClients.remove(clientSocket);
                clientWriters.remove(clientWriter);

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}