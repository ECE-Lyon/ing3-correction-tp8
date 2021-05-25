package exo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    private ServerSocket serverSocket;

    // On stocke les comptes dans une map par numéro de compte pour aller plus
    // rapidement dans la recherche
    private Map<Integer, BankAccount> accountsById;

    private List<ServerClient> serverClients;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(1235);
        this.accountsById = new HashMap<>();
        accountsById.put(1, new BankAccount(1, 0));
        accountsById.put(2, new BankAccount(2, 0));
        this.serverClients = new ArrayList<>();
    }

    public void execute() throws IOException {
        while (true) {
            System.out.println("En attente de connection");
            Socket socket = serverSocket.accept();
            System.out.println("Connecté à " + socket.getInetAddress().getHostName());
            // Lecture du numéro de compte qui se connecte (première ligne envoyée par le client)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int accountNumber = Integer.parseInt(bufferedReader.readLine());
            // On récupère le compte correpondant
            // On lance un nouveau server client (thread qui gère un client côté serveur)
            // On l'ajoute à la liste des clients pour pouvoir envoyer des messages par la suite
            ServerClient sc = new ServerClient(socket, this, accountsById.get(accountNumber));
            new Thread(sc).start();
            serverClients.add(sc);
        }
    }
    
    public synchronized void sendBalance(BankAccount bankAccount) {
        serverClients.stream()
            .filter(sc -> sc.getBankAccount().getId() == bankAccount.getId())
            .forEach(sc -> {
                sc.sendBalance();
            });
    }
}
