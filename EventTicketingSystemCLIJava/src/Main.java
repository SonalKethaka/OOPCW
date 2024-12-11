import com.example.EventTicketingSystemCLIJava.Configuration;
import com.example.EventTicketingSystemCLIJava.Customer;
import com.example.EventTicketingSystemCLIJava.TicketPool;
import com.example.EventTicketingSystemCLIJava.Vendor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String CONFIG_FILE = "config.json";
    private static final String TEXT_FILE_PATH = "config.txt";
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Configuration configuration = new Configuration();
        int vendorCount;
        int customerCount;

        configuration.setTotalTickets(promptForPositiveInt(scanner, "Enter Total Number of Tickets: "));
        configuration.setTicketReleaseRate(promptForPositiveInt(scanner, "Enter Ticket Release Rate: "));
        configuration.setCustomerRetrievalRate(promptForPositiveInt(scanner, "Enter Customer Retrieval Rate: "));
        configuration.setMaxTicketCapacity(promptForPositiveInt(scanner, "Enter Maximum Ticket Capacity: "));

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

        int startSystem = promptForPositiveInt(scanner, "Enter 1 If you want to start the System: ");

        if (startSystem == 1){
            vendorCount = promptForPositiveInt(scanner, "Enter Vendor Count: ");
            customerCount = promptForPositiveInt(scanner, "Enter Customer Count: ");

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
    public static void saveConfigurationAsJason(Configuration configurationForm, String filePath) throws IOException {
        // Manually construct a JSON string
        String json = "{\n" +
                "  \"TotalTickets\": " + configurationForm.getTotalTickets() + ",\n" +
                "  \"CustomerRetrievalRate\": " + configurationForm.getCustomerRetrievalRate() + ",\n" +
                "  \"TicketReleaseRate\": " + configurationForm.getTicketReleaseRate() + ",\n" +
                "  \"MaxTicketCapacity\": " + configurationForm.getMaxTicketCapacity() + "\n" +
                "}";

        // Write the JSON string to the file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(json);
        }
    }

    public static void saveConfigurationAsText(Configuration config) throws IOException {
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

    private static int promptForPositiveInt(Scanner scanner, String prompt) {
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