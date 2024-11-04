package com.example.Event.Ticketing.System;
import java.util.Vector;

public class TicketPool {
    private final Vector<String> tickets;

    public TicketPool() {
        tickets = new Vector<>();
    }

    public synchronized void addTicketForVendors(String ticket) {
        tickets.add(ticket);
        System.out.println(Thread.currentThread().getName() + " added: " + ticket);
    }

    public synchronized String removeTicketForCustomers() {
        if (tickets.isEmpty()) {
            System.out.println(Thread.currentThread().getName() + " attempted to purchase, but no tickets available.");
            return null;
        }
        String ticket = tickets.remove(0);
        System.out.println(Thread.currentThread().getName() + " purchased: " + ticket);
        return ticket;
    }
}
