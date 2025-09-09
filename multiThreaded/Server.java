import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    // first class citizen functional interface
    public Consumer<Socket> getConsumer() {

        return new Consumer<Socket>() {
            @Override
            public void accept(Socket clientSocket) {
                try {
                    PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                    toClient.println("Hello from the server" + clientSocket.getInetAddress());
                    toClient.flush();

                    toClient.close();
                    clientSocket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        // alternate way writing same thing
        /*
         * return (clientSocket) -> {
         * try {
         * PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
         * toClient.println("Hello from the server" + clientSocket.getInetAddress());
         * toClient.flush();
         * 
         * toClient.close();
         * clientSocket.close();
         * } catch (Exception ex) {
         * ex.printStackTrace();
         * }
         * };
         */
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);

            /*
             * alternate
             * Server server = new Server();
             * Consumer<Socket> socketHandler = server.getConsumer(); // Retrieve the
             * consumer then calll like
             * socketHandler.accept(acceptedSocket);
             * 
             */
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket acceptedSocket = socket.accept();
                System.out.println("Connection accepted for: " + acceptedSocket.getRemoteSocketAddress());
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                thread.start();
            }

        } catch (Exception ex) {
            ex.getStackTrace();
            System.out.println("Server Closed");
        }

    }
}
