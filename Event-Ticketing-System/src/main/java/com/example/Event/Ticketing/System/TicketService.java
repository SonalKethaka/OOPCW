package com.example.Event.Ticketing.System;


import com.example.Event.Ticketing.System.*;
import com.example.Event.Ticketing.System.Configuraion.ConfigService;
import com.example.Event.Ticketing.System.Configuraion.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class TicketService {

    private final TicketPool ticketPool;
    private final SimpMessagingTemplate messagingTemplate;
    List<Thread> vendors = new ArrayList<>();
    List<Thread> customers = new ArrayList<>();
    private boolean systemRunning = true;

    private final TicketRepository ticketRepository;

    private final PriorityBlockingQueue<Customer> customerQueue = new PriorityBlockingQueue<>();
    private int totalCustomers = 0;
    private int totalVipCustomers = 0;

    private int totalVendors = 0;

    @Autowired
    public TicketService(TicketPool ticketPool, SimpMessagingTemplate messagingTemplate, TicketRepository ticketRepository) throws IOException {
        this.ticketPool = ticketPool;
        this.messagingTemplate = messagingTemplate;
        this.ticketRepository = ticketRepository;
    }

    public void startSystem() throws IOException, InterruptedException {

        Configuration config = ConfigService.loadConfigurationFromJson();
        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int maxTicketCapacity = config.getMaxTicketCapacity();

        ticketPool.set(maxTicketCapacity, totalTickets, messagingTemplate);
        // Creating and starting vendor threads

        for (int i = 0; i < totalVendors; i++) {  // Create 3 vendor threads
            String num = String.valueOf(i+1);
            Thread vendorThread = new Thread(new Vendor(ticketPool, ticketReleaseRate,messagingTemplate, num));

            vendors.add(vendorThread);
            vendorThread.start();
//            Thread.sleep(1000);
        }


        for (int i = 0; i < totalCustomers; i++) {
            String num = String.valueOf(i + 1);
            Customer customer = new Customer(ticketPool, customerRetrievalRate, messagingTemplate, num, false); // Regular customer
            customerQueue.add(customer);
            Thread customerThread = new Thread(customer);
            customers.add(customerThread);
            customerThread.start();
        }

        for (int i = 0; i < totalVipCustomers; i++) {
            String num = String.valueOf(i + 1 );
            Customer vipCustomer = new Customer(ticketPool, customerRetrievalRate, messagingTemplate, num, true); // VIP customer
            customerQueue.add(vipCustomer);
            Thread vipCustomerThread = new Thread(vipCustomer);
            customers.add(vipCustomerThread);
            vipCustomerThread.start();
        }

        System.out.println("Ticket sales completed.");
    }

    public void stopSystem(){
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

    }

    public synchronized boolean isSystemRunning() {
        return systemRunning;
    }

    public void adjustCustomers(int numCustomers, boolean isVip) {
        if (isVip) {
            totalVipCustomers = numCustomers;
        } else {
            totalCustomers = numCustomers;
        }

        // Adjust the queue based on the new count
        messagingTemplate.convertAndSend("/topic/logs", (isVip ? "VIP" : "Regular") + " customer count adjusted to " + numCustomers);
    }

    public void adjustVendors(int numVendors) {
        totalVendors = numVendors;

        // Adjust the queue based on the new count
        messagingTemplate.convertAndSend("/topic/logs", "Vendor count adjusted to " + numVendors);
    }


    public int getRegularCustomersCount() {
        return totalCustomers;
    }

    public int getVipCustomersCount() {
        return totalVipCustomers;
    }

    public int getTotalVendors() {
        return totalVendors;
    }


    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
