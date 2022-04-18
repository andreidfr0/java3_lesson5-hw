package com.geekbrains;

import static com.geekbrains.Car.START;
import static com.geekbrains.Car.STOP;

public class MainClass{
    public static final int CARS_COUNTS = 4;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNTS];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
            for (int i = 0; i < cars.length; i++) {
                new Thread(cars[i]).start();
            }
        while (START.getCount() > 1) {
            START.countDown();
            START.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");  //Проверяем, собрались ли все автомобили
        }
        while (STOP.getCount() > 1 ) {
            STOP.countDown();
            STOP.await();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!") ;
    }
}
