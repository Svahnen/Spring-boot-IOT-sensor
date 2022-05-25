package com.assignment.sensor.Modules;

public class Temp {
    long id;
    float value;
    String timestamp;

    public Temp() {

    }

    public Temp(long id, float value, String timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
