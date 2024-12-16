package com.example.Event.Ticketing.System.Configuraion;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ConfigService {
    private static final String CONFIG_FILE = "config.json";
    private static final String TEXT_FILE_PATH = "config.txt";
    private static final Gson gson = new Gson();


    public void saveConfigurationAsJson(Configuration config) throws IOException {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            gson.toJson(config, writer);
        }
    }

    public static Configuration loadConfigurationFromJson() throws IOException {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("Configuration file not found, creating a new one.");
            return new Configuration();
        }
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
}
