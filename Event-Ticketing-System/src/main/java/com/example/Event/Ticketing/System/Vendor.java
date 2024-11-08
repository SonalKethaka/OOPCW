package com.example.Event.Ticketing.System;

import java.util.concurrent.atomic.AtomicBoolean;

public class Vendor implements Runnable{

    private final TicketPool ticketPool;
    private final int ticketsToAdd;
    //NOT SURE STUFF
    private final AtomicBoolean running = new AtomicBoolean(true);


    public Vendor(TicketPool ticketPool, int ticketsToAdd) {
        this.ticketPool = ticketPool;
        this.ticketsToAdd = ticketsToAdd;
    }

    @Override
    public void run() {
        for (int i = 1; i <= ticketsToAdd; i++) {
            String ticket = "Ticket-" + Thread.currentThread().getId() + "-" + i;
            ticketPool.addTicketForVendors(ticket);

            try {
                Thread.sleep(100); // Simulating delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Vendor interrupted.");
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
