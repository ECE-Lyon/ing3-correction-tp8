package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public void execute() throws IOException {
        Scanner scanner = new Scanner(System.in);

        Socket sock = new Socket("127.0.0.1", 1235);
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
            while (true) {
                bw.write(scanner.nextLine());
                bw.newLine();
                bw.flush();
            }
        }
    }
}
