package com.example.Event.Ticketing.System;


import com.example.Event.Ticketing.System.Unsure.TicketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Random;

public class Vendor implements Runnable {
//    private final TicketPool ticketPool;
//    private final int ticketsToAdd;
//    private final int ticketReleaseRate;
//
//    public Vendor(TicketPool ticketPool, int ticketsToAdd, int ticketReleaseRate) {
//        this.ticketPool = ticketPool;
//        this.ticketsToAdd = ticketsToAdd;
//        this.ticketReleaseRate = ticketReleaseRate;
//    }
//
//    @Override
//    public void run() {
//        try {
//            for (int i = 0; i < ticketsToAdd; i++) {
//                ticketPool.addTickets(1); // Add a ticket
//                System.out.println(Thread.currentThread().getName() + " added 1 ticket to the pool.");
//                Thread.sleep(3000/ticketReleaseRate);  // Wait based on release rate
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }

    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final SimpMessagingTemplate messagingTemplate;


    public Vendor(TicketPool ticketPool, int ticketReleaseRate, SimpMessagingTemplate messagingTemplate) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.messagingTemplate = messagingTemplate;

    }

    @Override
    public void run() {
        while (ticketPool.hasTicketsLeft() && !Thread.currentThread().isInterrupted()) {
            if (!ticketPool.addTickets(ticketReleaseRate)) {
                break;
            }
            try {
                Thread.sleep(5000); // Simulate delay between releases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!Thread.currentThread().isInterrupted()){
            messagingTemplate.convertAndSend("/topic/logs", "Vendor finished releasing tickets.");

        }

        System.out.println("Vendor finished releasing tickets.");
    }
}
