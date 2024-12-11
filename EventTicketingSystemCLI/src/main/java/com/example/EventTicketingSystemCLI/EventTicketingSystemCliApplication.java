package com.example.EventTicketingSystemCLI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class EventTicketingSystemCliApplication implements Runnable {
	private static final String CONFIG_FILE = "config.json";
	private static final String TEXT_FILE_PATH = "config.txt";

	public static void main(String[] args) {
		SpringApplication.run(EventTicketingSystemCliApplication.class, args);

		EventTicketingSystemCliApplication runnableApp = new EventTicketingSystemCliApplication();
		Thread thread = new Thread(runnableApp);

		try {
			System.out.println("Main thread tasks completed. Starting run method...");
			thread.start();  // Start the run method in a new thread
			thread.join();   // Wait for the thread to complete execution
		} catch (InterruptedException e) {
			System.err.println("Thread execution interrupted: " + e.getMessage());
		}
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		Configuration configuration = new Configuration();
		int vendorCount;
		int customerCount;

		configuration.setTotalTickets(gettingInt(scanner, "Enter Total Number of Tickets: "));
		configuration.setTicketReleaseRate(gettingInt(scanner, "Enter Ticket Release Rate: "));
		configuration.setCustomerRetrievalRate(gettingInt(scanner, "Enter Customer Retrieval Rate: "));
		configuration.setMaxTicketCapacity(gettingInt(scanner, "Enter Maximum Ticket Capacity: "));

		try {
			// Save as JSON
			saveConfigurationAsJason(configuration,CONFIG_FILE);
			System.out.println("Configuration saved as JSON.");

			// Save as plain text
			saveConfigurationAsText(configuration);
			System.out.println("Configuration saved as text.");

		} catch (IOException e) {
			e.printStackTrace();
		}

		int startSystem = gettingInt(scanner, "Enter 1 If you want to start the System: ");

		if (startSystem == 1){
			vendorCount = gettingInt(scanner, "Enter Vendor Count: ");
			customerCount = gettingInt(scanner, "Enter Customer Count: ");




			//Not Sure about below code lines!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

			TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity(), configuration.getTotalTickets() );

			// Start vendors
			List<Thread> vendorThreads = new ArrayList<>();
			for (int i = 0; i < vendorCount; i++) {
				Thread vendorThread = new Thread(new Vendor(ticketPool, configuration.getTicketReleaseRate(), "Vendor-" + (i + 1)));
				vendorThreads.add(vendorThread);
				vendorThread.start();
			}

			// Start customers
			List<Thread> customerThreads = new ArrayList<>();
			for (int i = 0; i < customerCount; i++) {
				Thread customerThread = new Thread(new Customer(ticketPool, configuration.getCustomerRetrievalRate(), "Customer-" + (i + 1)));
				customerThreads.add(customerThread);
				customerThread.start();
			}

			// Wait for all vendor and customer threads to finish
			vendorThreads.forEach(vendor -> {
				try {
					vendor.join();
				} catch (InterruptedException e) {
					System.out.println("Vendor thread interrupted.");
				}
			});

			customerThreads.forEach(customer -> {
				try {
					customer.join();
				} catch (InterruptedException e) {
					System.out.println("Customer thread interrupted.");
				}
			});

			System.out.println("Event ticketing simulation completed.");
		}else {
			System.out.println("System Stopped.");
		}


	}



	// Save configuration to JSON file
	private static void saveConfigurationAsJason(Configuration config, String fileName) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File(fileName), config);
	}

	public void saveConfigurationAsText(Configuration config) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEXT_FILE_PATH))) {
			writer.write("Total Tickets: " + config.getTotalTickets());
			writer.newLine();
			writer.write("Ticket Release Rate: " + config.getTicketReleaseRate());
			writer.newLine();
			writer.write("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
			writer.newLine();
			writer.write("Max Ticket Capacity: " + config.getMaxTicketCapacity());
			writer.newLine();
		}
	}

	private static int gettingInt(Scanner scanner, String prompt) {
		int value;
		do {
			System.out.print(prompt);
			while (!scanner.hasNextInt()) {
				System.out.print("Invalid input. " + prompt);
				scanner.next();
			}
			value = scanner.nextInt();
			if (value <= 0) System.out.println("Please enter a positive number.");
		} while (value <= 0);
		return value;
	}
}
