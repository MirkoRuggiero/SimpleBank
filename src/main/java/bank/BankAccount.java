package bank;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private static final ReentrantLock lock = new ReentrantLock();
    private Integer total = 100;

    void log(String template, String... args) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + String.format(template, args));
    }

    void withdraw(String name, int amount) throws InterruptedException, TimeoutException {
        if (!lock.tryLock(10, TimeUnit.SECONDS)) {
            throw new TimeoutException(Thread.currentThread().getName());
        }
        try {
            if (total >= amount) {
                log("%s withdraws %s", name, Integer.toString(amount));
                total = total - amount;
                log("Balance after withdrawal %s", Integer.toString(total));
            } else {
                log("%s cannot withdraw %s", name, Integer.toString(amount));
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    void deposit(String name, int deposit) throws InterruptedException, TimeoutException {
        if (!lock.tryLock(10, TimeUnit.SECONDS)) {
            throw new TimeoutException(Thread.currentThread().getName());
        }
        try {
            log("%s deposited %s", name, Integer.toString(deposit));
            total = total + deposit;
            log("Balance after deposit %s", Integer.toString(total));
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public Integer getTotal() {
        return total;
    }
}
