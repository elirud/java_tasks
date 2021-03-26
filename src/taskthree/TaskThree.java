package taskthree;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskThree {

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]); // Get port from arguments
        String rootFolder = args[1]; // Get filename
        Socket clientSocket = null;
        ServerSocket server = null;
        ExecutorService threadPool = Executors.newFixedThreadPool(10); // Create threadpool

        try {
            server = new ServerSocket(port); // Start server
            System.out.println("Opening server at port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        while (true) {
            try {
                clientSocket = server.accept(); // Listen to requests
            } catch (Exception e) {
                e.printStackTrace();
            }
            threadPool.execute(new HttpRunnable(clientSocket, rootFolder)); // Queue runnable for threadpool
        }
    }
}
