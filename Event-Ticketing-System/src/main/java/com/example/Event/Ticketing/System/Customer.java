package com.example.Event.Ticketing.System;

import com.example.Event.Ticketing.System.Unsure.TicketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class Customer implements Runnable, Comparable<Customer> {

    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final SimpMessagingTemplate messagingTemplate;
    private final String customerName;

    private final boolean isVip; // Flag to distinguish VIP customers
    private final int priority;  // Priority for VIP customers



    public Customer(TicketPool ticketPool, int customerRetrievalRate, SimpMessagingTemplate messagingTemplate, String customerName, boolean isVip) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.messagingTemplate = messagingTemplate;

        this.customerName = customerName;

        this.isVip = isVip;
        this.priority = isVip ? 1 : 2;  // VIP customers have higher priority (1)
    }

    @Override
    public int compareTo(Customer other) {
        return Integer.compare(this.priority, other.priority);  // VIP customers will be prioritized
    }

    @Override
    public void run() {
        while (ticketPool.hasTicketsLeft() && !Thread.currentThread().isInterrupted()) {
            if (!ticketPool.removeTicket(customerRetrievalRate, customerName, isVip)) {
                break;
            }
            try {
                Thread.sleep(5000); // Simulate delay between purchases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!Thread.currentThread().isInterrupted())
            messagingTemplate.convertAndSend("/topic/logs", (isVip ? "VIP" : "Regular")+" Customer " + customerName + " finished buying tickets.");

        System.out.println((isVip ? "VIP" : "Regular") + " Customer " + customerName +" finished buying tickets.");
    }

    public boolean isVip() {
        return isVip;
    }
}
