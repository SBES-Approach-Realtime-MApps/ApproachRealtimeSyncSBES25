package br.unesp.rc.MSReplicator.domain.MSCondominium.model;


import jakarta.persistence.Entity;

@Entity
public class RentableArea extends Common {
    private double value;
    private int capacity;

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

    @Override
    public String toString() {
        return "RentableArea [id=" + super.getId() + ", name=" + super.getName() + ", size=" + super.getSize() + ", capacity=" + capacity + ", value=" + value + "]";
    }
}
