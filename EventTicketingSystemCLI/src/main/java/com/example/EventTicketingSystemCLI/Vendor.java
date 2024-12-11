package com.example.EventTicketingSystemCLI;

public class Vendor implements Runnable {

    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final String vendorName;


    public Vendor(TicketPool ticketPool, int ticketReleaseRate,  String vendorName) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;

        this.vendorName = vendorName;
    }

    @Override
    public void run() {
        while (ticketPool.hasTicketsLeft() && !Thread.currentThread().isInterrupted()) {
            if (!ticketPool.addTickets(ticketReleaseRate, vendorName)) {
                break;
            }
            try {
                Thread.sleep(5000); // Simulate delay between releases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!Thread.currentThread().isInterrupted())
            System.out.println("Vendor "+vendorName+ " finished releasing tickets.");
    }
}
