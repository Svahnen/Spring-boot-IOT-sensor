package com.assignment.sensor.Modules;

import java.time.LocalDateTime;

public class Temp {
    int id;
    String value;
    LocalDateTime timestamp;

    public Temp() {

    }

    public Temp(int id, String value, LocalDateTime timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
