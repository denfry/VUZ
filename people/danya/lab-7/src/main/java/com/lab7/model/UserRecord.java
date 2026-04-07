package com.lab7.model;

public class UserRecord {
    private final int id;
    private final String email;
    private final String role;
    private final String plan;
    private final int totalAnalyses;
    private final boolean active;
    private final String createdAt;

    public UserRecord(int id, String email, String role, String plan, int totalAnalyses, boolean active, String createdAt) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.plan = plan;
        this.totalAnalyses = totalAnalyses;
        this.active = active;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPlan() {
        return plan;
    }

    public int getTotalAnalyses() {
        return totalAnalyses;
    }

    public boolean isActive() {
        return active;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
