package com.lab8.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class UserDirectoryItem {
    private final IntegerProperty id;
    private final StringProperty email;
    private final StringProperty role;
    private final StringProperty plan;
    private final IntegerProperty totalAnalyses;
    private final BooleanProperty active;
    private final ObjectProperty<LocalDateTime> createdAt;

    public UserDirectoryItem(int id, String email, String role, String plan, int totalAnalyses, boolean active, LocalDateTime createdAt) {
        this.id = new SimpleIntegerProperty(id);
        this.email = new SimpleStringProperty(email);
        this.role = new SimpleStringProperty(role);
        this.plan = new SimpleStringProperty(plan);
        this.totalAnalyses = new SimpleIntegerProperty(totalAnalyses);
        this.active = new SimpleBooleanProperty(active);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty roleProperty() {
        return role;
    }

    public StringProperty planProperty() {
        return plan;
    }

    public IntegerProperty totalAnalysesProperty() {
        return totalAnalyses;
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }
}

