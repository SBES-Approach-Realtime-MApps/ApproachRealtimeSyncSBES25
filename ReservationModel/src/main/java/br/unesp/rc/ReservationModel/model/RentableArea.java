package br.unesp.rc.ReservationModel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RentableArea {

    @Id
    private long id;

    private String name;
    private int size;
    private int capacity;
    private double value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    
    
}
