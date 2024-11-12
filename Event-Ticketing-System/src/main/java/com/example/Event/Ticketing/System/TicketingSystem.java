//package com.example.Event.Ticketing.System;
//
//
//
//import com.example.Event.Ticketing.System.Configuraion.Configuration;
//import com.example.Event.Ticketing.System.Configuraion.ConfigService;
//
//import java.io.IOException;
//
//public class TicketingSystem {
//    public static void main(String[] args) throws IOException {
//        // Assuming we get configuration from a JSON file or database
//        Configuration config = new ConfigService().loadConfigurationFromJson();
//
//        // Initialize the ticket pool with the config values
//        TicketPool ticketPool = new TicketPool(config.getTotalTickets(), config.getMaxTicketCapacity());
//
//        // Create and start vendor threads
//        for (int i = 0; i < 5; i++) {  // 5 vendors
//            Vendor vendor = new Vendor(ticketPool, 10, config.getTicketReleaseRate());
//            new Thread(vendor).start();
//        }
//
//        // Create and start customer threads
//        for (int i = 0; i < 20; i++) {  // 20 customers
//            Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate());
//            new Thread(customer).start();
//        }
//    }
//}
