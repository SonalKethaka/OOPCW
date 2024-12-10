package com.example.Event.Ticketing.System;



import com.example.Event.Ticketing.System.Configuraion.ConfigService;
import com.example.Event.Ticketing.System.Unsure.TicketService;
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

//    @PostMapping("/add")
//    public void addTickets(@RequestParam int tickets) {//Method Not NECESSARY
//        ticketPool.addTickets(tickets);
//    }

//    @PostMapping("/purchase")
//    public String purchaseTicket() {//Method Not NECESSARY
//        try {
//            ticketPool.removeTicket();
//            return "Ticket purchased successfully!";
//        } catch (InterruptedException e) {
//            return "Error: " + e.getMessage();
//        }
//    }

//    @PostMapping("/start")
//    public ResponseEntity<String> startSystem() {
//        systemRunning = true;
//        return ResponseEntity.ok("System started");
//    }

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

        // Add endpoint to get system status
    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getSystemStatus () {
        Map<String, String> response = new HashMap<>();
        response.put("status", systemRunning ? "Running" : "Stopped");
//        return ResponseEntity.ok().body(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total")
    public int getTotalTickets () {
        return ticketPool.getTicketsLeft();
    }


    // Adjust the number of regular customers
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

    // Adjust the number of VIP customers
    @PostMapping("/configure/vipCustomers")
    public ResponseEntity<String> adjustVipCustomers(@RequestParam int numCustomers) {
        try {
            ticketService.adjustCustomers(numCustomers, true); // true indicates VIP customers
            return ResponseEntity.ok("VIP customers count set to " + numCustomers);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adjusting VIP customers: " + e.getMessage());
        }
    }

    // Adjust the number of Vendors
    @PostMapping("/configure/vendors")
    public ResponseEntity<String> adjustVendors(@RequestParam int numVendors) {
        try {
            ticketService.adjustVendors(numVendors); // true indicates VIP customers
            return ResponseEntity.ok("Vendors count set to " + numVendors);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adjusting Vendors: " + e.getMessage());
        }
    }

    // Endpoint to get the current number of regular and VIP customers
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
