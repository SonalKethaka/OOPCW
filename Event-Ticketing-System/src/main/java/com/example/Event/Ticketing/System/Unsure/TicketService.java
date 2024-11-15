package com.example.Event.Ticketing.System.Unsure;


import com.example.Event.Ticketing.System.Configuraion.ConfigService;
import com.example.Event.Ticketing.System.Configuraion.Configuration;
import com.example.Event.Ticketing.System.Customer;
import com.example.Event.Ticketing.System.TicketPool;
import com.example.Event.Ticketing.System.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class TicketService {

    private final TicketPool ticketPool;
    private final SimpMessagingTemplate messagingTemplate;

    List<Thread> vendors = new ArrayList<>();
    List<Thread> customers = new ArrayList<>();


    private boolean systemRunning = true;



    @Autowired
    public TicketService(TicketPool ticketPool, SimpMessagingTemplate messagingTemplate) throws IOException {
        this.ticketPool = ticketPool;
        this.messagingTemplate = messagingTemplate;
    }

    public void startSystem() throws IOException, InterruptedException {
//        if (systemRunning) {
//            return;  // If the system is already running, do nothing
//        }
//        systemRunning = true;
        // Start vendor threads
//        for (int i = 1; i <= vendorCount; i++) {
//            int vendorId = i;
//            new Thread(() -> {
//                while (ticketPool.getTicketsLeft() < ticketPool.getMaxTicketCapacity()) {
//                    ticketPool.addTickets(1);
//                    messagingTemplate.convertAndSend("/topic/logs", "Vendor " + vendorId + " added 1 ticket to the ticketPool");
//                    try {
//                        Thread.sleep(ticketsPerVendor);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//            }).start();
//        }
//
//        // Start customer threads
//        for (int i = 1; i <= customerCount; i++) {
//            int customerId = i;
//            new Thread(() -> {
//                while (ticketPool.getTicketsLeft() > 0) {
//                    try {
//                        ticketPool.removeTicket();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    messagingTemplate.convertAndSend("/topic/logs", "Customer " + customerId + " purchased 1 ticket from the ticketPool");
//                    try {
//                        Thread.sleep(ticketsPerCustomer);
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                }
//            }).start();
//        }


        Configuration config = ConfigService.loadConfigurationFromJson();
        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int maxTicketCapacity = config.getMaxTicketCapacity();




//        TicketPool ticketPool = new TicketPool(maxTicketCapacity);

        ticketPool.set(maxTicketCapacity, totalTickets, messagingTemplate);
        // Creating and starting vendor threads
//        List<Thread> vendorThreads = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {  // Example: 5 vendors
//            Vendor vendor = new Vendor(ticketPool, totalTickets / 5, ticketReleaseRate);
//            Thread vendorThread = new Thread(vendor, "Vendor-" + (i + 1));
//            vendorThreads.add(vendorThread);
//            vendorThread.start();
//        }
//
//        // Creating and starting customer threads
//        List<Thread> customerThreads = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {  // Example: 10 customers
//            Customer customer = new Customer(ticketPool,  customerRetrievalRate);
//            Thread customerThread = new Thread(customer, "Customer-" + (i + 1));
//            customerThreads.add(customerThread);
//            customerThread.start();
//        }
//
//        // Wait for all threads to complete (optional, can be omitted for a long-running system)
//        vendorThreads.forEach(vendorThread -> {
//            try {
//                vendorThread.join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        });
//
//        customerThreads.forEach(customerThread -> {
//            try {
//                customerThread.join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        });
//
//        System.out.println("All tickets have been processed.");
//    }


        for (int i = 0; i < 3; i++) {  // Create 3 vendor threads
            Thread vendorThread = new Thread(new Vendor(ticketPool, ticketReleaseRate,messagingTemplate));
//            messagingTemplate.convertAndSend("/topic/logs", "Vendor " + i + " added 1 ticket to the ticketPool");

            vendors.add(vendorThread);
            vendorThread.start();
//            Thread.sleep(1000);
        }

        for (int i = 0; i < 5; i++) {  // Create 5 customer threads
            Thread customerThread = new Thread(new Customer(ticketPool, customerRetrievalRate, messagingTemplate));
//            messagingTemplate.convertAndSend("/topic/logs", "Customer " + i + " purchased 1 ticket from the ticketPool");


            customers.add(customerThread);
            customerThread.start();
//            Thread.sleep(1000);

        }

        // Wait for all vendor and customer threads to finish
//        for (Thread vendor : vendors) {  //Gehiru
//            try {
//                vendor.join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//
//        for (Thread customer : customers) {
//            try {
//                customer.join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }

        System.out.println("Ticket sales completed.");
    }

    public void stopSystem(){
//        if (!systemRunning) {
//            return;  // If the system isn't running, do nothing
//        }
//        systemRunning = false;

        // Interrupt vendor threads
        for (Thread vendorThread : vendors) {
            if (vendorThread.isAlive()) {  // Check if the thread is active
                vendorThread.interrupt();
            }
        }


        // Interrupt customer threads
        for (Thread customerThread : customers) {
            if (customerThread.isAlive()) {  // Check if the thread is active
                customerThread.interrupt();
            }
        }

        // Reset ticket pool if needed
        ticketPool.reset();

        // Clear thread lists for vendors and customers
        vendors.clear();
        customers.clear();

          // Mark system as stopped
    }

    public synchronized boolean isSystemRunning() {
        return systemRunning;
    }

}
