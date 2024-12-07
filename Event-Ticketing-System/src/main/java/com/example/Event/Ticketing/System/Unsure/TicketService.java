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
import java.util.concurrent.PriorityBlockingQueue;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Service
public class TicketService {

    private final TicketPool ticketPool;
    private final SimpMessagingTemplate messagingTemplate;
    List<Thread> vendors = new ArrayList<>();
    List<Thread> customers = new ArrayList<>();
    private boolean systemRunning = true;

    private final PriorityBlockingQueue<Customer> customerQueue = new PriorityBlockingQueue<>();
    private int totalCustomers = 0;
    private int totalVipCustomers = 0;

    @Autowired
    public TicketService(TicketPool ticketPool, SimpMessagingTemplate messagingTemplate) throws IOException {
        this.ticketPool = ticketPool;
        this.messagingTemplate = messagingTemplate;
    }

    public void startSystem() throws IOException, InterruptedException {

        Configuration config = ConfigService.loadConfigurationFromJson();
        int totalTickets = config.getTotalTickets();
        int ticketReleaseRate = config.getTicketReleaseRate();
        int customerRetrievalRate = config.getCustomerRetrievalRate();
        int maxTicketCapacity = config.getMaxTicketCapacity();

        ticketPool.set(maxTicketCapacity, totalTickets, messagingTemplate);
        // Creating and starting vendor threads

        for (int i = 0; i < 3; i++) {  // Create 3 vendor threads
            String num = String.valueOf(i+1);
            Thread vendorThread = new Thread(new Vendor(ticketPool, ticketReleaseRate,messagingTemplate, num));

            vendors.add(vendorThread);
            vendorThread.start();
//            Thread.sleep(1000);
        }

//        for (int i = 0; i < 5; i++) {  // Create 5 customer threads
//            String num = String.valueOf(i+1);
//
//            Thread customerThread = new Thread(new Customer(ticketPool, customerRetrievalRate, messagingTemplate, num));
//
//
//            customers.add(customerThread);
//            customerThread.start();
////            Thread.sleep(1000);
//
//        }   //CHATGPTTTTTTTTTTTTTTTTT
        for (int i = 0; i < totalCustomers; i++) {
            String num = String.valueOf(i + 1);
            Customer customer = new Customer(ticketPool, customerRetrievalRate, messagingTemplate, num, false); // Regular customer
            customerQueue.add(customer);
            Thread customerThread = new Thread(customer);
            customers.add(customerThread);
            customerThread.start();
        }

        for (int i = totalCustomers; i <= totalCustomers + totalVipCustomers; i++) {
            String num = String.valueOf(i );
            Customer vipCustomer = new Customer(ticketPool, customerRetrievalRate, messagingTemplate, num, true); // VIP customer
            customerQueue.add(vipCustomer);
            Thread vipCustomerThread = new Thread(vipCustomer);
            customers.add(vipCustomerThread);
            vipCustomerThread.start();
        }


//         Wait for all vendor and customer threads to finish
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

    public void adjustCustomers(int numCustomers, boolean isVip) {
        if (isVip) {
            totalVipCustomers = numCustomers;
        } else {
            totalCustomers = numCustomers;
        }

        // Adjust the queue based on the new count
        messagingTemplate.convertAndSend("/topic/logs", (isVip ? "VIP" : "Regular") + " customer count adjusted to " + numCustomers);
    }


    public int getRegularCustomersCount() {
        return totalCustomers;
    }

    public int getVipCustomersCount() {
        return totalVipCustomers;
    }
}
