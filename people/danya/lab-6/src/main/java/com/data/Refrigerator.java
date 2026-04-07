package com.data;

import javafx.beans.property.*;


public class Refrigerator {
    private final StringProperty manufacturer;  
    private final StringProperty color;         
    private final DoubleProperty height;        
    private final DoubleProperty width;         
    private final DoubleProperty depth;         

    public Refrigerator() {
        this(null, null, 0.0, 0.0, 0.0);
    }

    public Refrigerator(String manufacturer, String color, double height, double width, double depth) {
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.color = new SimpleStringProperty(color);
        this.height = new SimpleDoubleProperty(height);
        this.width = new SimpleDoubleProperty(width);
        this.depth = new SimpleDoubleProperty(depth);
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public StringProperty manufacturerProperty() {
        return manufacturer;
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public StringProperty colorProperty() {
        return color;
    }

    public double getHeight() {
        return height.get();
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public double getWidth() {
        return width.get();
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public double getDepth() {
        return depth.get();
    }

    public void setDepth(double depth) {
        this.depth.set(depth);
    }

    public DoubleProperty depthProperty() {
        return depth;
    }

    public double calculateVolume() {
        return (height.get() * width.get() * depth.get()) / 1000.0;
    }

    public String getInfo() {
        return String.format("Холодильник: Производитель=%s, Цвет=%s, Размеры=%.2f x %.2f x %.2f см, Объем=%.2f л",
                getManufacturer(), getColor(), getHeight(), getWidth(), getDepth(), calculateVolume());
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
