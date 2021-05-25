package exo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerClient implements Runnable {

    private BufferedReader bufferedReader;

    private PrintWriter printWriter;

    private BankAccount bankAccount;

    // On garde un lien vers le serveur pour pouvoir appeler ses méthodes si nécessaire
    private Server server;

    public ServerClient(Socket socket, Server server, BankAccount bankAccount) throws IOException {
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.printWriter = new PrintWriter(socket.getOutputStream(), true);
        this.bankAccount = bankAccount;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                // Par simplicité, on passe sur plusieurs vérifications sur l'input
                if (line.startsWith("ADD")) {
                    String[] parts = line.split(" ");
                    // On synchronise sur le compte bancaire, si jamais un client ajoute
                    // pendant qu'un client enlève de l'argent
                    synchronized (bankAccount) {
                        bankAccount.addAmount(Integer.parseInt(parts[1]));
                    }
                    // On demande au serveur d'envoyer le solde pour ce compte
                    // à tous les clients du même compte
                    server.sendBalance(bankAccount);
                }

                if (line.startsWith("SUB")) {
                    String[] parts = line.split(" ");
                    synchronized (bankAccount) {
                        bankAccount.substractAmount(Integer.parseInt(parts[1]));
                    }
                    server.sendBalance(bankAccount);
                }

                if (line.equals("BALANCE")) {
                    server.sendBalance(bankAccount);
                }
            }
            // Le client se déconnecte, on ferme toutes les ressources
            bufferedReader.close();
            printWriter.close();
        } catch (IOException e) {
            try {
                bufferedReader.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            printWriter.close();
        }
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void sendBalance() {
        printWriter.println("Account Balance " + bankAccount.getBalance());
    }
}
