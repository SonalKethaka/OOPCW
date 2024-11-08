package com.example.Event.Ticketing.System;

import java.util.concurrent.atomic.AtomicBoolean;

public class Customer implements Runnable{

    private TicketPool ticketPool;  //final removed
    private int ticketsToPurchase;  //final removed
    //NOT SURE STUFF
    private final AtomicBoolean running = new AtomicBoolean(true);


    public Customer(TicketPool ticketPool, int ticketsToPurchase) {
        this.ticketPool = ticketPool;
        this.ticketsToPurchase = ticketsToPurchase;
    }

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }



    @Override
    public void run() {
        for (int i = 0; i < ticketsToPurchase; i++) {
            ticketPool.removeTicketForCustomers();

            try {
                Thread.sleep(200); // Simulating delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Customer interrupted.");
            }
        }
    }

    //NOT SURE STUFF
    public void stop() {
        running.set(false);
    }
    public void start() {
        running.set(true);
    }
}
