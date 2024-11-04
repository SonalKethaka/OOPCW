package com.example.Event.Ticketing.System;

public class Customer implements Runnable{

    private final TicketPool ticketPool;
    private final int ticketsToPurchase;

    public Customer(TicketPool ticketPool, int ticketsToPurchase) {
        this.ticketPool = ticketPool;
        this.ticketsToPurchase = ticketsToPurchase;
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
}
