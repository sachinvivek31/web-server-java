import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;

    public Server(int pool) {
        if (pool <= 0) {
            throw new IllegalArgumentException("Pool size should be ore than 0");
        }
        this.threadPool = Executors.newFixedThreadPool(pool);

    }

    public void handleClient(Socket clienSocket) {
        try (PrintWriter toSocket = new PrintWriter(clienSocket.getOutputStream(), true)) {
            toSocket.println("Hello from server on port 8010");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10;
        Server server = new Server(poolSize);

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(50000);
            System.out.println("Server is listening on port :" + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connections: " + clientSocket.getRemoteSocketAddress());
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            server.threadPool.shutdown();
        }
    }
}
