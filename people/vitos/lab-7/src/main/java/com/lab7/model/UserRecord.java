package com.lab7.model;

public class UserRecord {
    private final int id;
    private final String username;
    private final String riskProfile;
    private final String balance;
    private final int openOrders;
    private final boolean active;
    private final String createdAt;

    public UserRecord(int id, String username, String riskProfile, String balance, int openOrders, boolean active, String createdAt) {
        this.id = id;
        this.username = username;
        this.riskProfile = riskProfile;
        this.balance = balance;
        this.openOrders = openOrders;
        this.active = active;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRiskProfile() {
        return riskProfile;
    }

    public String getBalance() {
        return balance;
    }

    public int getOpenOrders() {
        return openOrders;
    }

    public boolean isActive() {
        return active;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
