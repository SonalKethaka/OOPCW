package com.example.Event.Ticketing.System;

import com.example.Event.Ticketing.System.Configuraion.Configuration;
import com.example.Event.Ticketing.System.Configuraion.ConfigurationManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class EventTicketingSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {SpringApplication.run(EventTicketingSystemApplication.class, args);}

	@Override
	public void run(String... args) throws Exception{
		Scanner scanner = new Scanner(System.in);
		Configuration configuration = new Configuration();

//		try {
//			configuration = ConfigurationManager.loadConfigurationFromJson();
//		} catch (IOException e) {
//			System.out.println("Loading default configuration.");
//		}

		configuration.setTotalTickets(gettingInt(scanner, "Enter Total Number of Tickets: "));
		configuration.setTicketReleaseRate(gettingInt(scanner, "Enter Ticket Release Rate: "));
		configuration.setCustomerRetrievalRate(gettingInt(scanner, "Enter Customer Retrieval Rate: "));
		configuration.setMaxTicketCapacity(gettingInt(scanner, "Enter Maximum Ticket Capacity: "));

		try {
			// Save as JSON
			ConfigurationManager.saveConfigurationAsJson(configuration);
			System.out.println("Configuration saved as JSON.");

			// Save as plain text
			ConfigurationManager.saveConfigurationAsText(configuration);
			System.out.println("Configuration saved as text.");

			// Load from JSON
//			Configuration loadedConfigJson = ConfigurationManager.loadConfigurationFromJson();
//			System.out.println("Loaded JSON Configuration: " + loadedConfigJson.getTotalTickets());
//
//			// Load from plain text
//			Configuration loadedConfigText = ConfigurationManager.loadConfigurationFromText();
//			System.out.println("Loaded Text Configuration: " + loadedConfigText.getTotalTickets());

		} catch (IOException e) {
			e.printStackTrace();
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
