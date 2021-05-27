package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void execute() throws IOException {
        ServerSocket ssock = new ServerSocket(1235);

        while (true) {
            Socket socket = ssock.accept();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                while (true) {
                    String line = br.readLine();
                    System.out.println(line);

                    if (line.equals("quit")) {
                        break;
                    }
                }
            }
        }
    }
}
