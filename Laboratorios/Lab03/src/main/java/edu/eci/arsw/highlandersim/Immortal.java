package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback = null;

    private int health;

    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());

    private boolean execution;

    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue,
            ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback = ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue = defaultDamageValue;
        this.execution = true;
    }

    public void run() {

        while (true) {
            synchronized (immortalsPopulation) {
                while (!execution) {
                    try {
                        immortalsPopulation.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Immortal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            Immortal im;

            int myIndex = immortalsPopulation.indexOf(this);

            int nextFighterIndex = r.nextInt(immortalsPopulation.size());

            // avoid self-fight
            if (nextFighterIndex == myIndex) {
                nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
            }

            im = immortalsPopulation.get(nextFighterIndex);

            this.fight(im);

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void fight(Immortal i2) {

        if (i2.getHealth() > 0) {
            Immortal fistLock = minIdInmortal(this, i2);
            Immortal secondLock = fistLock == this ? i2 : this;
            synchronized (fistLock) {
                try {
                    fistLock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Immortal.class.getName()).log(Level.SEVERE, null, ex);
                }
                synchronized (secondLock) {
                    try {
                        secondLock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Immortal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i2.changeHealth(i2.getHealth() - defaultDamageValue);
                    this.health += defaultDamageValue;
                    // VER COMO SE HACE EL NOTIFY PARA CADA UNO
                    // fistLock.notify();
                    // secondLock.notify();
                }
            }
            updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");
        } else {
            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
        }

    }

    public Immortal minIdInmortal(Immortal i1, Immortal i2) {
        Immortal minId = null;
        // TOMAR EL ULTIMO CARACTER, CONVERTIRLO A NUMERO Y COMPARARLOS
        if (Character.getNumericValue(i1.getName()[-1]) < Character.getNumericValue(i2.getName()[-1])) {
            minId = i1;
        } else {
            minId = i2;
        }
        return minId;
    }

    public void changeHealth(int v) {
        health = v;
    }

    public int getHealth() {
        return health;
    }

    public void setExecution(boolean execution) {
        this.execution = execution;
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

}
