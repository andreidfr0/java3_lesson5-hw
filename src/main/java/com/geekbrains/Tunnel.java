package com.geekbrains;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private static final int CAR_QUEUE = MainClass.CARS_COUNTS / 2;  // По тоннелю могут одновременно ехать только двое
    private static final boolean[] GATE_PLACES = new boolean[CAR_QUEUE];
    private static final Semaphore SEMAPHORE = new Semaphore(CAR_QUEUE, false);

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(Car c) {
        try {
            try {
                SEMAPHORE.acquire();
                int gateNumber = -1;
                synchronized (GATE_PLACES) {  //Ищем свободное место
                    for (int j = 0; j < CAR_QUEUE; j++)
                        if (!GATE_PLACES[j]) {      //Если место свободно
                            GATE_PLACES[j] = true;  //занимаем его
                            gateNumber = j;         //Наличие свободного места, гарантирует семафор
                            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                            break;
                        }
                }
                synchronized (GATE_PLACES) {
                    GATE_PLACES[gateNumber] = false;//Освобождаем место
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                SEMAPHORE.release();
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
