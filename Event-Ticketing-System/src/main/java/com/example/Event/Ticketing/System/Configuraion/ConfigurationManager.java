package com.example.Event.Ticketing.System.Configuraion;

import com.google.gson.Gson;

import java.io.*;

public class ConfigurationManager {
    private static final String CONFIG_FILE = "config.json";
    private static final String TEXT_FILE_PATH = "config.txt";
    private static final Gson gson = new Gson();

    public static void saveConfigurationAsJson(Configuration config) throws IOException {
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

    // Method to load configuration from a plain text file
    public static Configuration loadConfigurationFromText() throws IOException {
        Configuration config = new Configuration();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEXT_FILE_PATH))) {
            config.setTotalTickets(Integer.parseInt(reader.readLine().split(": ")[1]));
            config.setTicketReleaseRate(Integer.parseInt(reader.readLine().split(": ")[1]));
            config.setCustomerRetrievalRate(Integer.parseInt(reader.readLine().split(": ")[1]));
            config.setMaxTicketCapacity(Integer.parseInt(reader.readLine().split(": ")[1]));
        }
        return config;
    }
}
