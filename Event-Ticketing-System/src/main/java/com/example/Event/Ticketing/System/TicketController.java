package com.example.Event.Ticketing.System;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSystem(@RequestParam int totalTickets,
                                              @RequestParam int ticketReleaseRate,
                                              @RequestParam int customerRetrievalRate,
                                              @RequestParam int numVendors,
                                              @RequestParam int numCustomers) {
        ticketService.startSystem(totalTickets, ticketReleaseRate, customerRetrievalRate, numVendors, numCustomers);
        return new ResponseEntity<>("System Started", HttpStatus.OK);
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        ticketService.stopSystem();
        return new ResponseEntity<>("System Stopped", HttpStatus.OK);
    }

    @GetMapping("/totalTickets")
    public ResponseEntity<Integer> getTotalTickets() {
        return new ResponseEntity<>(ticketService.getTotalTickets(), HttpStatus.OK);
    }
}