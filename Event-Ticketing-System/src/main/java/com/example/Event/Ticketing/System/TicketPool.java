package com.example.Event.Ticketing.System;
import com.example.Event.Ticketing.System.Configuraion.Configuration;

import java.util.Vector;

public class TicketPool {
    private final Vector<String> tickets;
    private  int totalTicketPoolTickets;


    public TicketPool(int totalTicketPoolTickets) {
        tickets = new Vector<>(totalTicketPoolTickets);
        this.totalTicketPoolTickets = totalTicketPoolTickets;
    }

    public synchronized void addTicketForVendors(String ticket) {
        if (ticket.length() >= totalTicketPoolTickets){
            tickets.add(ticket);
            System.out.println(Thread.currentThread().getName() + " added: " + ticket);
        }else {
            System.out.println("Can't add anymore Tickets to the TicketsPool.");
        }

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

    //NOT SURE STUFF
    public synchronized int getTotalTickets() {
        return tickets.size();
    }
}
