package com.example.Event.Ticketing.System;

import com.example.Event.Ticketing.System.Unsure.TicketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class Customer implements Runnable {
//    private final TicketPool ticketPool;
//    private final int customerRetrievalRate;
//
//    public Customer(TicketPool ticketPool, int customerRetrievalRate) {
//        this.ticketPool = ticketPool;
//        this.customerRetrievalRate = customerRetrievalRate;
//    }
//
//    @Override
//    public void run() {
//        try {
//            ticketPool.removeTicket();  // Try to buy a ticket
//            Thread.sleep(customerRetrievalRate);  // Wait based on retrieval rate
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }

    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final SimpMessagingTemplate messagingTemplate;



    public Customer(TicketPool ticketPool, int customerRetrievalRate, SimpMessagingTemplate messagingTemplate) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.messagingTemplate = messagingTemplate;

    }

    @Override
    public void run() {
        while (ticketPool.hasTicketsLeft() && !Thread.currentThread().isInterrupted()) {
            if (!ticketPool.removeTicket(customerRetrievalRate)) {
                break;
            }
            try {
                Thread.sleep(5000); // Simulate delay between purchases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!Thread.currentThread().isInterrupted())
            messagingTemplate.convertAndSend("/topic/logs", "Customer finished buying tickets.");

        System.out.println("Customer finished buying tickets.");
    }
}
