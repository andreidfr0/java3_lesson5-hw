package com.geekbrains;

import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {

    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    static final CountDownLatch START = new CountDownLatch(5);
    static final CountDownLatch STOP = new CountDownLatch(5);
    private Race race;
    private int speed;
    private String name;
    private int carNumber;
    private static boolean winner;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.carNumber = CARS_COUNT;
        this.name = "Участник #" + carNumber;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            for (int i = 0; i < race.getStages().size(); i++) {
                if (i == 0) {
                    START.countDown();
                    START.await();
                }
                race.getStages().get(i).go(this);
            }
            isWin(this); // find finished
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            STOP.countDown();
            try {
                STOP.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static synchronized void isWin(Car c) {
        if (!winner) {
            System.out.println(c.name + " - FINISHED");
            winner = true;
        }
    }

}
