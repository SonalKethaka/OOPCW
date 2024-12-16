package com.example.Event.Ticketing.System;



import com.example.Event.Ticketing.System.Configuraion.ConfigService;
import com.example.Event.Ticketing.System.Configuraion.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200/control", "http://localhost:4200"})
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketPool ticketPool;
    private final TicketService ticketService;
    private final ConfigService configService;
    private boolean systemRunning = false;

    @Autowired
    public TicketController(TicketService ticketService, TicketPool ticketPool, ConfigService configService) {
        this.ticketPool = ticketPool;
        this.ticketService = ticketService;
        this.configService = configService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSystem() throws IOException {
        try {
            ticketService.startSystem();
            System.out.println("System started successfully.");
            return ResponseEntity.ok("System started successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to start the system: " + e.getMessage());
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        try {
            ticketService.stopSystem();
            getTotalTickets();
            System.out.println("System stopped successfully.");
            return ResponseEntity.ok("System stopped successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to stop the system: " + e.getMessage());
        }
    }


    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getSystemStatus () {
        Map<String, String> response = new HashMap<>();
        response.put("status", systemRunning ? "Running" : "Stopped");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total")
    public int getTotalTickets () {
        return ticketPool.getTicketsLeft();
    }

    @GetMapping("/totalTickets")
    public int getStartedTotalTickets () throws IOException {
        Configuration config = ConfigService.loadConfigurationFromJson();
        return config.getTotalTickets();
    }


    @PostMapping("/configure/regularCustomers")
    public ResponseEntity<Map<String, String>> adjustRegularCustomers(@RequestParam int numCustomers) {
        try {
            ticketService.adjustCustomers(numCustomers, false);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Regular customers count set to " + numCustomers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Error adjusting regular customers: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/configure/vipCustomers")
    public ResponseEntity<String> adjustVipCustomers(@RequestParam int numCustomers) {
        try {
            ticketService.adjustCustomers(numCustomers, true); // true indicates VIP customers
            return ResponseEntity.ok("VIP customers count set to " + numCustomers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adjusting VIP customers: " + e.getMessage());
        }
    }

    @PostMapping("/configure/vendors")
    public ResponseEntity<String> adjustVendors(@RequestParam int numVendors) {
        try {
            ticketService.adjustVendors(numVendors); // true indicates VIP customers
            return ResponseEntity.ok("Vendors count set to " + numVendors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adjusting Vendors: " + e.getMessage());
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<Map<String, Integer>> getCustomerCounts() {
        Map<String, Integer> customerCounts = new HashMap<>();
        customerCounts.put("regularCustomers", ticketService.getRegularCustomersCount());
        customerCounts.put("vipCustomers", ticketService.getVipCustomersCount());
        return ResponseEntity.ok(customerCounts);
    }

    @GetMapping("/vendors")
    public ResponseEntity<Map<String, Integer>> getVendorCounts() {
        Map<String, Integer> vendorCounts = new HashMap<>();
        vendorCounts.put("regularCustomers", ticketService.getTotalVendors());
        return ResponseEntity.ok(vendorCounts);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }
}
