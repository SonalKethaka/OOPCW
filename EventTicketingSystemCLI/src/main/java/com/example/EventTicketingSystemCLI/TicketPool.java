package com.example.EventTicketingSystemCLI;

import org.springframework.boot.logging.java.SimpleFormatter;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class TicketPool {

    private final int maxCapacity;
    private int ticketsLeft;
    private int ticketPool;

    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.ticketsLeft = totalTickets;
//        this.currentTicketsInPool = 0;
        this.ticketPool = 0;
    }

    public synchronized boolean addTickets(int ticketsToAdd, String vendorName) {
        if (ticketsLeft <= 0 || ticketPool >= maxCapacity) {  //nimeshhhhhhhhhhhhhhhhh
            return false;
        }

        int ticketsToRelease = Math.min(ticketsToAdd, maxCapacity - ticketPool);
        ticketsToRelease = Math.min(ticketsToRelease, ticketsLeft);

        ticketPool += ticketsToRelease;
        ticketsLeft -= ticketsToRelease;
        System.out.println("Vendor "+vendorName+" added " + ticketsToRelease + " tickets. Tickets in pool: " + ticketPool);


        notifyAll();  // Notify customers that tickets are available
        return true;
    }

    public synchronized boolean removeTicket(int ticketsToRetrieve, String customerName) {
////        while (ticketPool < ticketsToRetrieve) {
////            try {
////                wait();
////            } catch (InterruptedException e) {
////                Thread.currentThread().interrupt();
////                return false;
////            }
////        }
//        while (ticketPool == 0) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                return false;
//            }
//        }
//        if (ticketPool < ticketsToRetrieve ){
//            System.out.println("Customer " + customerName +" bought " + ticketPool + " ticket(s). Tickets left in pool: " + 0);
//            ticketPool -= ticketPool;
//        }else {
//            ticketPool -= ticketsToRetrieve;
//            System.out.println("Customer " + customerName +" bought " + ticketsToRetrieve + " ticket(s). Tickets left in pool: " + ticketPool);
//        }
//
//        notifyAll();  // Notify vendors that space is available
//        return true;
        // Wait if there are no tickets available
        while (ticketPool == 0) {
            try {
                System.out.println("Waiting for tickets to become available...");
                wait(); // Wait until tickets are added to the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false; // Exit if the thread is interrupted
            }
        }

        // Adjust the number of tickets to retrieve based on availability
        int ticketsToRemove = Math.min(ticketsToRetrieve, ticketPool);

        // Update the pool by removing the tickets
        ticketPool -= ticketsToRemove;

        // Log the transaction
        System.out.println("Customer " + customerName + " bought " + ticketsToRemove + " ticket(s). Tickets left in pool: " + ticketPool);

        // Notify all waiting threads that space is now available in the pool
        notifyAll();
        return true;
    }

    public boolean hasTicketsLeft() {
        return ticketsLeft > 0 || ticketPool > 0;
    }
}