import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server {
    public void run() throws IOException, UnknownHostException, IOException, SocketException {
        int port = 8010;
        ServerSocket socket = null;
        // socket.setSoTimeout(100000);

        try {

            socket = new ServerSocket(port);
            System.out.println("Server is listening on port: " + port);

            while (true) {
                try (Socket acceptedConnection = socket.accept()) {
                    System.out.println("Accepted connections" + acceptedConnection.getRemoteSocketAddress());

                    handleSocket(acceptedConnection);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (socket != null)
                socket.close();
            System.out.println("Server is shutting down");
        }
    }

    private void handleSocket(Socket clientSocket) throws IOException {
        try (PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Read the message from the client
            String line = fromClient.readLine();
            if (line != null) {
                System.out.println("Received from client: " + line);
                toClient.println("Message received: " + line);
            } else {
                toClient.println("No data received.");
            }
        }
    }

    public void demoCode(Socket acceptedConnection) throws IOException {
        PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(), true);
        BufferedReader fromClient = new BufferedReader(
                new InputStreamReader(acceptedConnection.getInputStream())); // cause it takes inputstream

        toClient.println("Hello from the server");
        toClient.flush();

        String line = fromClient.readLine();
        if (line != null) {
            System.out.println("Received from the client: " + line);
            toClient.println("Message received: " + line);
        } else {
            toClient.println("No data received.");
        }

        /*
         * not important as the socket will be closed when the connection is closed
         * toClient.close();
         * fromClient.close();
         */
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace(); // kya kya kha se ho chuka bo print kr dea
        }
    }
}