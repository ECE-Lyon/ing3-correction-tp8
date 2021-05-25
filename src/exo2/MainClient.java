package exo2;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) throws IOException {
        Client client = new Client(1);
        client.execute();
    }
}
