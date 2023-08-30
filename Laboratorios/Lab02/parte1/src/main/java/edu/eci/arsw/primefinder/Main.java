package edu.eci.arsw.primefinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<PrimeFinderThread> threads = new ArrayList<>();

        threads.add(new PrimeFinderThread(0, 10000000, 1));
        threads.add(new PrimeFinderThread(10000001, 20000000, 2));
        threads.add(new PrimeFinderThread(20000001, 30000000, 3));

        for (PrimeFinderThread thread : threads) {
            thread.start();
        }

        try {
            Thread.sleep(5000);
            for (PrimeFinderThread thread : threads) {
                thread.stopExec();
            }
            Thread.sleep(500);

            System.out.println("==================== Stopping threads... ====================");
            int totalPrimes = 0;
            for (PrimeFinderThread thread : threads) {
                totalPrimes += thread.getPrimes().size();
            }
            System.out.println("Total of found primes: " + totalPrimes);

            System.out.println("Press ENTER to continue...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            sc.close();

            System.out.println("==================== Resuming threads... ====================");
            
            for (PrimeFinderThread thread : threads) {
                thread.resumeExec();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
