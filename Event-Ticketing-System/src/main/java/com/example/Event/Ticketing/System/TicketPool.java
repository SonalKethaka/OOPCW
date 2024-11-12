package com.example.Event.Ticketing.System;

import com.example.Event.Ticketing.System.Configuraion.ConfigService;
import com.example.Event.Ticketing.System.Configuraion.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;



public class TicketPool {
//    private final List<Integer> tickets = Collections.synchronizedList(new ArrayList<>());; // List to hold tickets
//    private final int maxTicketCapacity;
//
//
//    public TicketPool(int maxTicketCapacity){
//        this.maxTicketCapacity = maxTicketCapacity;
//    }
//
//    // Vendor adds tickets
//    public synchronized void addTickets(int ticket) throws InterruptedException {
//        // Prevent adding more tickets than max capacity
////        if (ticketPool.size() + ticketsToAdd <= maxTicketCapacity) {
////            for (int i = 0; i < ticketsToAdd; i++) {
////                ticketPool.add(1); // Add a ticket
////            }
////            System.out.println("Tickets added. Total tickets: " + ticketPool.size());
////        } else {
////            System.out.println("Cannot add tickets. Max capacity reached.");
////        }
//
//        while (tickets.size() >= maxTicketCapacity) {
//            wait();  // Wait if ticket pool is full
//        }
//        tickets.add(ticket);
//        notifyAll();
//    }
//
//    // Customer removes a ticket
//    public synchronized int removeTicket() throws InterruptedException {
////        if (!ticketPool.isEmpty()) {
////            ticketPool.remove(0); // Remove the first ticket
////            System.out.println("Ticket purchased. Remaining tickets: " + ticketPool.size());
////        } else {
////            System.out.println("No tickets available.");
////        }
//
//        while (tickets.isEmpty()) {
//            wait();  // Wait if no tickets are available
//        }
//        int ticket = tickets.removeLast();
//        notifyAll();  // Notify vendors that space is available
//        return ticket;
//    }
//
//    // Get total tickets left
//    public synchronized int getTicketsLeft() {
//        return  tickets.size();
//    }
//
//    public synchronized int getMaxTicketCapacity(){return this.maxTicketCapacity;}


    private final int maxCapacity;
    private final AtomicInteger ticketsLeft;
    private int currentTicketsInPool;
    private final SimpMessagingTemplate messagingTemplate;




    public TicketPool(int maxCapacity, int totalTickets, SimpMessagingTemplate messagingTemplate) {
        this.maxCapacity = maxCapacity;
        this.ticketsLeft = new AtomicInteger(totalTickets);
        this.messagingTemplate = messagingTemplate;
        this.currentTicketsInPool = 0;
    }

    public synchronized boolean addTickets(int ticketsToAdd) {
        if (ticketsLeft.get() <= 0 || currentTicketsInPool >= maxCapacity) {
            return false;
        }

        int ticketsToRelease = Math.min(ticketsToAdd, maxCapacity - currentTicketsInPool);
        ticketsToRelease = Math.min(ticketsToRelease, ticketsLeft.get());

        currentTicketsInPool += ticketsToRelease;
        this.ticketsLeft.addAndGet(-ticketsToRelease);

        messagingTemplate.convertAndSend("/topic/logs", "Vendor added " + ticketsToRelease + " tickets. Tickets in pool: " + currentTicketsInPool);
        System.out.println("Vendor added " + ticketsToRelease + " tickets. Tickets in pool: " + currentTicketsInPool);

//        try {
//            Thread.sleep(1000);  // Adjust time as needed
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }

        notifyAll();  // Notify customers that tickets are available
        return true;
    }

    public synchronized boolean removeTicket(int ticketsToRetrieve) {
        while (currentTicketsInPool < ticketsToRetrieve) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        currentTicketsInPool -= ticketsToRetrieve;
        messagingTemplate.convertAndSend("/topic/logs", "Customer bought " + ticketsToRetrieve + " ticket(s). Tickets left in pool: " + currentTicketsInPool);
        System.out.println("Customer bought " + ticketsToRetrieve + " ticket(s). Tickets left in pool: " + currentTicketsInPool);
        notifyAll();  // Notify vendors that space is available
        return true;
    }

    public boolean hasTicketsLeft() {
        return ticketsLeft.get() > 0 || currentTicketsInPool > 0;
    }

    public AtomicInteger getTicketsLeft() {
        return ticketsLeft;
    }

    public void reset() {
        // Reset the pool state, clear any remaining tickets, etc.
        ticketsLeft.set(0);
        currentTicketsInPool = 0;
        messagingTemplate.convertAndSend("/topic/logs", "Ticket pool has been reset.");
        System.out.println("Ticket pool has been reset.");
    }

}