package com.lab7.model;

public class AnalysisRecord {
    private final int id;
    private final String taskId;
    private final String videoId;
    private final String sourceType;
    private final String status;
    private final double confidenceScore;
    private final boolean hasAdvertising;
    private final String createdAt;

    public AnalysisRecord(int id, String taskId, String videoId, String sourceType, String status,
                          double confidenceScore, boolean hasAdvertising, String createdAt) {
        this.id = id;
        this.taskId = taskId;
        this.videoId = videoId;
        this.sourceType = sourceType;
        this.status = status;
        this.confidenceScore = confidenceScore;
        this.hasAdvertising = hasAdvertising;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getStatus() {
        return status;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    public boolean isHasAdvertising() {
        return hasAdvertising;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}