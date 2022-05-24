package bank;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankAccountMovements {

    private static final ListeningExecutorService exe =
            MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4));

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BankAccount bankAccount = new BankAccount();

        Callable<Integer> w1 = new Withdraw(bankAccount, "Mirko", 1000);
        Callable<Integer> w2 = new Withdraw(bankAccount, "Mirko", 50);
        Callable<Integer> w3 = new Withdraw(bankAccount, "Mirko", 10);
        Callable<Integer> w4 = new Withdraw(bankAccount, "Mirko", 20);
        Callable<Integer> w5 = new Withdraw(bankAccount, "Mirko", 30);

        Callable<Integer> d1 = new Deposit(bankAccount, "Mirko", 10);
        Callable<Integer> d2 = new Deposit(bankAccount, "Mirko", 10);
        Callable<Integer> d3 = new Deposit(bankAccount, "Mirko", 10);
        Callable<Integer> d4 = new Deposit(bankAccount, "Mirko", 10);
        Callable<Integer> d5 = new Deposit(bankAccount, "Mirko", 1000);

        List<Callable<Integer>> callableList = ImmutableList.of(d1, w3, d2, d3, d4, w1, w2, w4, w5, d5);
        List<ListenableFuture<Integer>> futList = new ArrayList<>(callableList.size());

        callableList.forEach(c -> futList.add(exe.submit(c)));

        Uninterruptibles.getUninterruptibly(Futures.allAsList(futList));
        exe.shutdown();
        exe.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}
