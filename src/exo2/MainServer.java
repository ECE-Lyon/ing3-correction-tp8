package exo2;

import java.io.IOException;

public class MainServer {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.execute();
    }
}