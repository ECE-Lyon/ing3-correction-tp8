package exo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private int accountId;

    public Client(int accountId) {
        this.accountId = accountId;
    }

    public void execute() throws IOException {
        Socket socket = new Socket("127.0.0.1", 1235);
        new Thread(() -> {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }).start();

        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)) {
            printWriter.println(accountId);
            Scanner clavier = new Scanner(System.in);
            while (true) {
                String input = clavier.nextLine();
                printWriter.println(input);
            }
        }
    }
}
