package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {

    int a, b, name;
    private Boolean stop = false;

    private List<Integer> primes = new LinkedList<Integer>();

    public PrimeFinderThread(int a, int b, int name, Boolean stop) {
        super();
        this.a = a;
        this.b = b;
        this.name = name;
        this.stop = stop;
    }

    public void run() {
        for (int i = a; i <= b; i++) {
            if (isPrime(i)) {
                primes.add(i);
                System.out.println("Thread " + name + " found prime: " + i);
                synchronized (stop) {
                    while (stop) {
                        try {
                            System.out.println("Thread " + name + " is stoping...");
                            stop.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    boolean isPrime(int n) {
        if (n % 2 == 0)
            return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public synchronized void stopExec() throws InterruptedException {
        stop = true;
    }

    public void resumeExec() {
        synchronized (stop) {
            stop = false;
            stop.notifyAll();
        }
    }

}
