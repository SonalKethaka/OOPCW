package com.example.Event.Ticketing.System;

import org.springframework.boot.logging.java.SimpleFormatter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;



public class TicketPool {

    private int maxCapacity;
    private int ticketsLeft;
    private final List<Integer> ticketPool;  // This will hold the quantities of tickets in pool

    private SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    private final TicketRepository ticketRepository;

    public TicketPool(int maxCapacity, int totalTickets, SimpMessagingTemplate messagingTemplate, TicketRepository ticketRepository) {
        this.maxCapacity = maxCapacity;
        this.ticketsLeft = totalTickets;
        this.messagingTemplate = messagingTemplate;
        this.ticketPool = Collections.synchronizedList(new ArrayList<>(Collections.nCopies(totalTickets, 0)));
        this.ticketRepository = ticketRepository;
    }

    static {
        try {
            // Ensure the "logs" directory exists
            File logsDir = new File("logs");
            if (!logsDir.exists()) {
                logsDir.mkdir();  // Creates the directory if it does not exist
            }

            // Set up FileHandler for logging
            FileHandler fileHandler = new FileHandler("logs/application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            System.setProperty("java.util.logging.ConsoleHandler.level", "OFF");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean addTickets(int ticketsToAdd, String vendorName) {
        if (ticketsLeft <= 0 || ticketPool.size() >= maxCapacity) {  //nimeshhhhhhhhhhhhhhhhh
            return false;
        }

        int ticketsToRelease = Math.min(ticketsToAdd, maxCapacity - ticketPool.size());
        ticketsToRelease = Math.min(ticketsToRelease, ticketsLeft);

        for (int i = 0; i < ticketsToRelease; i++) {
            ticketPool.add(1);  // Each "1" represents one added ticket in the pool
        }
        ticketsLeft -= ticketsToRelease;

        // Save to database
        Ticket ticket = new Ticket();
        ticket.setVendorName(vendorName);
        ticket.setQuantity(ticketsToRelease);
        ticket.setOperationType("ADD");
        ticketRepository.save(ticket);

        logger.info("Vendor " + vendorName + " added " + ticketsToRelease + " tickets. Tickets in pool: " + ticketPool.size());

        messagingTemplate.convertAndSend("/topic/logs",  "Vendor "+vendorName+" added " + ticketsToRelease + " tickets. Tickets in pool: " + ticketPool.size());
        System.out.println("Vendor "+vendorName+" added " + ticketsToRelease + " tickets. Tickets in pool: " + ticketPool.size());

        notifyAll();  // Notify customers that tickets are available
        return true;
    }

    public synchronized boolean removeTicket(int ticketsToRetrieve, String customerName, boolean isVip) {


        while (ticketPool.isEmpty()) { // Wait if the ticket pool is empty
            try {
                System.out.println("Waiting for tickets to become available...");
                messagingTemplate.convertAndSend("/topic/logs", "Waiting for tickets to become available...");

                wait(); // Wait until tickets are added to the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        // Determine how many tickets can be retrieved
        int ticketsToRemove = Math.min(ticketsToRetrieve, ticketPool.size());

        for (int i = 0; i < ticketsToRemove; i++) {
            ticketPool.remove(0); // Simulate ticket purchase by removing from the pool
        }

        // Save the ticket transaction to the database
        Ticket ticket = new Ticket();
        ticket.setCustomerName(customerName);
        ticket.setQuantity(ticketsToRemove);
        ticket.setVip(isVip);
        ticket.setOperationType("PURCHASE");
        ticketRepository.save(ticket);

        logger.info((isVip ? "VIP" : "Regular") + " Customer " + customerName + " bought " + ticketsToRemove + " ticket(s). Tickets left in pool: " + ticketPool.size());
        messagingTemplate.convertAndSend("/topic/logs", (isVip ? "VIP" : "Regular") + " Customer " + customerName + " bought " + ticketsToRemove + " ticket(s). Tickets left in pool: " + ticketPool.size());
        System.out.println((isVip ? "VIP" : "Regular") + " Customer " + customerName + " bought " + ticketsToRemove + " ticket(s). Tickets left in pool: " + ticketPool.size());

        notifyAll(); // Notify all waiting threads that tickets may now be available
        return true;
    }

    public boolean hasTicketsLeft() {
        return ticketsLeft > 0 || !ticketPool.isEmpty();
    }

    public int getTicketsLeft() {
        return ticketsLeft;
    }

    public void set(int maxCapacity, int totalTickets, SimpMessagingTemplate messagingTemplate) {
        this.maxCapacity = maxCapacity;
        this.ticketsLeft = totalTickets;
        this.messagingTemplate = messagingTemplate;
    }

    public void reset() {
        ticketPool.clear();
        ticketsLeft = 0;
        messagingTemplate.convertAndSend("/topic/logs", "Ticket pool has been reset.");
        System.out.println("Ticket pool has been reset.");
    }

}