package com.example.Event.Ticketing.System;

import com.example.Event.Ticketing.System.Configuraion.ConfigService;
import com.example.Event.Ticketing.System.Configuraion.Configuration;
import org.springframework.boot.logging.java.SimpleFormatter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Logger;



public class TicketPool {

    private int maxCapacity;
    private int ticketsLeft;
    private int currentTicketsInPool;
    private SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = Logger.getLogger(TicketPool.class.getName());

    private final TicketRepository ticketRepository;

    public TicketPool(int maxCapacity, int totalTickets, SimpMessagingTemplate messagingTemplate, TicketRepository ticketRepository) {
        this.maxCapacity = maxCapacity;
        this.ticketsLeft = totalTickets;
        this.messagingTemplate = messagingTemplate;
        this.currentTicketsInPool = 0;

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

            // Disable console logging (no ConsoleHandler)
            System.setProperty("java.util.logging.ConsoleHandler.level", "OFF");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean addTickets(int ticketsToAdd, String vendorName) {
        if (ticketsLeft <= 0 || currentTicketsInPool >= maxCapacity) {  //nimeshhhhhhhhhhhhhhhhh
            return false;
        }

        int ticketsToRelease = Math.min(ticketsToAdd, maxCapacity - currentTicketsInPool);
        ticketsToRelease = Math.min(ticketsToRelease, ticketsLeft);

        currentTicketsInPool += ticketsToRelease;
        ticketsLeft -= ticketsToRelease;

        // Save to database
        Ticket ticket = new Ticket();
        ticket.setVendorName(vendorName);
        ticket.setQuantity(ticketsToRelease);
        ticket.setOperationType("ADD");
        ticketRepository.save(ticket);

        logger.info("Vendor " + vendorName + " added " + ticketsToRelease + " tickets. Tickets in pool: " + currentTicketsInPool);

        messagingTemplate.convertAndSend("/topic/logs",  "Vendor "+vendorName+" added " + ticketsToRelease + " tickets. Tickets in pool: " + currentTicketsInPool);
        System.out.println("Vendor "+vendorName+" added " + ticketsToRelease + " tickets. Tickets in pool: " + currentTicketsInPool);



//        try {
//            Thread.sleep(1000);  // Adjust time as needed
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }

        notifyAll();  // Notify customers that tickets are available
        return true;
    }

    public synchronized boolean removeTicket(int ticketsToRetrieve, String customerName, boolean isVip) {
        while (currentTicketsInPool < ticketsToRetrieve) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        currentTicketsInPool -= ticketsToRetrieve;

        // Save to database
        Ticket ticket = new Ticket();
        ticket.setCustomerName(customerName);
        ticket.setQuantity(ticketsToRetrieve);
        ticket.setVip(isVip);
        ticket.setOperationType("PURCHASE");
        ticketRepository.save(ticket);

        logger.info((isVip ? "VIP" : "Regular") + " Customer " + customerName + " bought " + ticketsToRetrieve + " ticket(s). Tickets left in pool: " + currentTicketsInPool);

        messagingTemplate.convertAndSend("/topic/logs", (isVip ? "VIP" : "Regular") + " Customer " + customerName +" bought " + ticketsToRetrieve + " ticket(s). Tickets left in pool: " + currentTicketsInPool);
        System.out.println((isVip ? "VIP" : "Regular") + " Customer " + customerName +" bought " + ticketsToRetrieve + " ticket(s). Tickets left in pool: " + currentTicketsInPool);

        notifyAll();  // Notify vendors that space is available
        return true;
    }

    public boolean hasTicketsLeft() {
        return ticketsLeft > 0 || currentTicketsInPool > 0;
    }

    public int getTicketsLeft() {
//        System.out.println(ticketsLeft);
        return ticketsLeft;
    }

    public void set(int maxCapacity, int totalTickets, SimpMessagingTemplate messagingTemplate) {
        this.maxCapacity = maxCapacity;
        this.ticketsLeft = totalTickets;
        this.messagingTemplate = messagingTemplate;
    }

    public void reset() {
        // Reset the pool state, clear any remaining tickets, etc.
        ticketsLeft = 0;
        currentTicketsInPool = 0;
        messagingTemplate.convertAndSend("/topic/logs", "Ticket pool has been reset.");
        System.out.println("Ticket pool has been reset.");
    }

}