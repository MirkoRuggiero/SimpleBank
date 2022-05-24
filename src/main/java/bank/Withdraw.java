package bank;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

public class Withdraw implements Callable<Integer> {

    private final BankAccount account;
    private final String name;
    private final int amount;

    public Withdraw(BankAccount account, String name, int amount) {
        this.account = account;
        this.name = name;
        this.amount = amount;
    }

    @Override
    public Integer call() throws InterruptedException, TimeoutException {
        account.withdraw(name, amount);
        return account.getTotal();
    }
}
