package taskthree;

import java.net.Socket;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpRunnable implements Runnable {
    private static final int BUFFER_SIZE = 4096;
    private byte[] buffer = new byte[BUFFER_SIZE];
    private OutputStream out;
    private InputStream is;
    private Socket clientSocket;
    private String rootFolder = "";

    public HttpRunnable(Socket clientSocket, String rootFolder) {
        this.clientSocket = clientSocket;
        this.rootFolder = rootFolder;
    }

    @Override
    public void run() {
        int numberRead = 0;

        try {
            out = this.clientSocket.getOutputStream();
            is = this.clientSocket.getInputStream();
            numberRead = is.read(buffer, 0, BUFFER_SIZE);

            byte[] readBuf = new byte[numberRead];
            System.arraycopy(buffer, 0, readBuf, 0, numberRead);

            // Check GET request for file name
            String header = new String(readBuf);
            System.out.println(header);
            String fileName = header.split("\r\n")[0].split(" ")[1].substring(1);
            System.out.println("Asked for file: " + fileName);

            File file = new File(this.rootFolder + fileName);

            // Stream our response
            out.write("HTTP/1.1 200 OK\r\n".getBytes());
            out.write(("Content-Length: " + file.length() + "\r\n").getBytes());
            out.write("Content-Type: application/octet-stream\r\n".getBytes());
            out.write(("Content-Disposition: attachment; filename=\"" + fileName + "\"\r\n").getBytes());
            out.write("\r\n".getBytes());
            Files.copy(Paths.get(file.getAbsolutePath()), out); // Stream file data
            out.close();
            this.clientSocket.close();
        } catch (SocketException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
