package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public void execute() throws IOException {
        Socket sock = new Socket("127.0.0.1", 1235);
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
            bw.write("hey !");
            bw.newLine();
            bw.flush();
        }
    }
}
