package com.example.Event.Ticketing.System;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vendorName;
    private String customerName;
    private boolean isVip;
    private int quantity;

    @Column(nullable = false)
    private String operationType; // ADD or PURCHASE


    public Long getId() {
        return id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public boolean isVip() {
        return isVip;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}