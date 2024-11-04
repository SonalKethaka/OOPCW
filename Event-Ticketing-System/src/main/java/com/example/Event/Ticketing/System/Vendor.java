package com.example.Event.Ticketing.System;

public class Vendor implements Runnable{

    private final TicketPool ticketPool;
    private final int ticketsToAdd;

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
}
