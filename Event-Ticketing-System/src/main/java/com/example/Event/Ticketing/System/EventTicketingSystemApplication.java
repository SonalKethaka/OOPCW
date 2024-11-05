package com.example.Event.Ticketing.System;

import com.example.Event.Ticketing.System.Configuraion.ConfigService;
import com.example.Event.Ticketing.System.Configuraion.Configuration;
//import com.example.Event.Ticketing.System.Configuraion.ConfigurationManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class EventTicketingSystemApplication {

	public static void main(String[] args) {SpringApplication.run(EventTicketingSystemApplication.class, args);}

//	@Override
//	public void run(String... args) throws Exception{
//		Scanner scanner = new Scanner(System.in);
//		Configuration configuration = new Configuration();
//
////		try {
////			configuration = ConfigurationManager.loadConfigurationFromJson();
////		} catch (IOException e) {
////			System.out.println("Loading default configuration.");
////		}
//
//		configuration.setTotalTickets(gettingInt(scanner, "Enter Total Number of Tickets: "));
//		configuration.setTicketReleaseRate(gettingInt(scanner, "Enter Ticket Release Rate: "));
//		configuration.setCustomerRetrievalRate(gettingInt(scanner, "Enter Customer Retrieval Rate: "));
//		configuration.setMaxTicketCapacity(gettingInt(scanner, "Enter Maximum Ticket Capacity: "));
//
//		try {
//			// Save as JSON
//			ConfigService.saveConfigurationAsJson(configuration);
//			System.out.println("Configuration saved as JSON.");
//
//			// Save as plain text
//			ConfigService.saveConfigurationAsText(configuration);
//			System.out.println("Configuration saved as text.");
//
//			// Load from JSON
////			Configuration loadedConfigJson = ConfigurationManager.loadConfigurationFromJson();
////			System.out.println("Loaded JSON Configuration: " + loadedConfigJson.getTotalTickets());
////
////			// Load from plain text
////			Configuration loadedConfigText = ConfigurationManager.loadConfigurationFromText();
////			System.out.println("Loaded Text Configuration: " + loadedConfigText.getTotalTickets());
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		//Not Sure about below code lines!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//
//		TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity());
//
//		// Start vendors
//		List<Thread> vendorThreads = new ArrayList<>();
//		for (int i = 0; i < configuration.getTicketReleaseRate(); i++) {
//			Thread vendorThread = new Thread(new Vendor(ticketPool, configuration.getTotalTickets() / configuration.getTicketReleaseRate()), "Vendor-" + (i + 1));
//			vendorThreads.add(vendorThread);
//			vendorThread.start();
//		}
//
//		// Start customers
//		List<Thread> customerThreads = new ArrayList<>();
//		for (int i = 0; i < configuration.getCustomerRetrievalRate(); i++) {
//			Thread customerThread = new Thread(new Customer(ticketPool, configuration.getTotalTickets() / configuration.getCustomerRetrievalRate()), "Customer-" + (i + 1));
//			customerThreads.add(customerThread);
//			customerThread.start();
//		}
//
//		// Wait for all vendor and customer threads to finish
//		vendorThreads.forEach(vendor -> {
//			try {
//				vendor.join();
//			} catch (InterruptedException e) {
//				System.out.println("Vendor thread interrupted.");
//			}
//		});
//
//		customerThreads.forEach(customer -> {
//			try {
//				customer.join();
//			} catch (InterruptedException e) {
//				System.out.println("Customer thread interrupted.");
//			}
//		});
//
//		System.out.println("Event ticketing simulation completed.");
//	}
//
//
//
//	private static int gettingInt(Scanner scanner, String prompt) {
//		int value;
//		do {
//			System.out.print(prompt);
//			while (!scanner.hasNextInt()) {
//				System.out.print("Invalid input. " + prompt);
//				scanner.next();
//			}
//			value = scanner.nextInt();
//			if (value <= 0) System.out.println("Please enter a positive number.");
//		} while (value <= 0);
//		return value;
//	}

}
