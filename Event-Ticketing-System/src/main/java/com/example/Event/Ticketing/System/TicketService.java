//package com.example.Event.Ticketing.System;
//
//import com.example.Event.Ticketing.System.Configuraion.Configuration;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class TicketService {
////    private final TicketPool ticketPool = new TicketPool();
////    private List<Thread> vendors = new ArrayList<>();
////    private List<Thread> customers = new ArrayList<>();
////    private boolean isSystemRunning = false;
//
//    private Configuration configuration;
//    private TicketPool ticketPool;
//    private List<Thread> vendorThreads = new ArrayList<>();
//    private List<Thread> customerThreads = new ArrayList<>();
//    private boolean isSystemRunning = false;
//
//    public TicketService() {
//        // Load default configuration or any configuration logic
//        this.configuration = new Configuration();
//        this.ticketPool = new TicketPool(configuration.getMaxTicketCapacity());
//    }
//
//
//    int totalTickets = configuration.getTotalTickets();
//    public synchronized void startSystem() {
//        if (isSystemRunning) return;
//
//        do {
//            if (totalTickets - configuration.getMaxTicketCapacity() >= 0){
//                addingTickets();
//                totalTickets -= configuration.getMaxTicketCapacity();
//            } else {
//                vendorThreads.clear();
//                customerThreads.clear();
//
//                for (int i = 0; i < configuration.getTicketReleaseRate(); i++) {
//                    Thread vendorThread = new Thread(new Vendor(ticketPool, configuration.getTotalTickets() / configuration.getTicketReleaseRate()), "Vendor-" + (i + 1));
//                    vendorThreads.add(vendorThread);
//                    vendorThread.start();
//                }
//
//                // Start customers based on configuration
//                for (int i = 0; i < configuration.getCustomerRetrievalRate(); i++) {
//                    Thread customerThread = new Thread(new Customer(ticketPool, configuration.getTotalTickets() / configuration.getCustomerRetrievalRate()), "Customer-" + (i + 1));
//                    customerThreads.add(customerThread);
//                    customerThread.start();
//                }
//            }
//        }while (totalTickets <= 0);
//
//        isSystemRunning = true;
//    }
//
//    public synchronized void stopSystem() {
//        vendorThreads.forEach(Thread::interrupt);
//        customerThreads.forEach(Thread::interrupt);
////        vendors.clear();
////        customers.clear();
//        isSystemRunning = false;
//    }
//
//    public int getAvailableTickets() {
//        return ticketPool.getAvailableTickets();
//    }
//
//    public void addingTickets(){
//        vendorThreads.clear();
//        customerThreads.clear();
//
//        for (int i = 0; i < configuration.getTicketReleaseRate(); i++) {
//            Thread vendorThread = new Thread(new Vendor(ticketPool, configuration.getTotalTickets() / configuration.getTicketReleaseRate()), "Vendor-" + (i + 1));
//            vendorThreads.add(vendorThread);
//            vendorThread.start();
//        }
//
//        // Start customers based on configuration
//        for (int i = 0; i < configuration.getCustomerRetrievalRate(); i++) {
//            Thread customerThread = new Thread(new Customer(ticketPool, configuration.getTotalTickets() / configuration.getCustomerRetrievalRate()), "Customer-" + (i + 1));
//            customerThreads.add(customerThread);
//            customerThread.start();
//        }
//    }
//}



package com.example.Event.Ticketing.System;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {
    private TicketPool ticketPool;
    private List<Thread> vendorThreads = new ArrayList<>();
    private List<Thread> customerThreads = new ArrayList<>();
    private boolean systemRunning = false;

    public void initializeTicketPool(int maxTicketCapacity) {
        ticketPool = new TicketPool(maxTicketCapacity);
    }

    public void startSystem(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int numVendors, int numCustomers) {
        if (systemRunning) return;

        systemRunning = true;
        initializeTicketPool(totalTickets);

        for (int i = 0; i < numVendors; i++) {
            Vendor vendor = new Vendor(ticketPool, ticketReleaseRate);
            Thread vendorThread = new Thread(vendor);
            vendor.start();
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        for (int i = 0; i < numCustomers; i++) {
            Customer customer = new Customer(ticketPool, customerRetrievalRate);
            Thread customerThread = new Thread(customer);
            customer.start();
            customerThreads.add(customerThread);
            customerThread.start();
        }
    }

    public void stopSystem() {
        if (!systemRunning) return;

        systemRunning = false;
        vendorThreads.forEach(Thread::interrupt);
        customerThreads.forEach(Thread::interrupt);

        vendorThreads.clear();
        customerThreads.clear();
    }

    public int getTotalTickets() {
        return ticketPool.getTotalTickets();
    }
}