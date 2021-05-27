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

            new Thread(() -> {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    while (true) {
                        String line = br.readLine();
                        System.out.println(Thread.currentThread().getName() + ": " + line);

                        if (line.equals("quit")) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
