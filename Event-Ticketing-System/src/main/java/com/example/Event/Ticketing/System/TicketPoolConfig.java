package com.example.Event.Ticketing.System;

import com.example.Event.Ticketing.System.Configuraion.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;

@Configuration
public class TicketPoolConfig {
    private final SimpMessagingTemplate messagingTemplate;

    public TicketPoolConfig(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Bean
    public TicketPool ticketPool() throws IOException {
        int maxTicketCapacity = 0;  // Set the desired max ticket capacity
        int totalTickets =0;
        return new TicketPool( maxTicketCapacity, totalTickets, messagingTemplate );
    }
}
