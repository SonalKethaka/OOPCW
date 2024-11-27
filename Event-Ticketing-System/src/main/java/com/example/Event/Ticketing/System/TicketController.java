package com.example.Event.Ticketing.System;



import com.example.Event.Ticketing.System.Configuraion.ConfigService;
import com.example.Event.Ticketing.System.Unsure.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
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
    }
