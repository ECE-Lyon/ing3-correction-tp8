package exo2;

public class BankAccount {

    private int id;

    private int balance;

    public BankAccount(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public synchronized void addAmount(int amount) {
        this.balance += amount;
    }

    public synchronized void substractAmount(int amount) {
        this.balance -= amount;
    }
}
