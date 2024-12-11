package com.example.EventTicketingSystemCLI;

public class Customer implements Runnable {

    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final String customerName;



    public Customer(TicketPool ticketPool, int customerRetrievalRate,  String customerName) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;

        this.customerName = customerName;
    }


    @Override
    public void run() {
        while (ticketPool.hasTicketsLeft() && !Thread.currentThread().isInterrupted()) {
            if (!ticketPool.removeTicket(customerRetrievalRate, customerName)) {
                break;
            }
            try {
                Thread.sleep(5000); // Simulate delay between purchases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!Thread.currentThread().isInterrupted())
            System.out.println("Customer " + customerName +" finished buying tickets.");
    }

}
