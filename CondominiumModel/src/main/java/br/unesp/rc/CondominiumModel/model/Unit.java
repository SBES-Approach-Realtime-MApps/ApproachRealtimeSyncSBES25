package br.unesp.rc.CondominiumModel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String location;
    private double sizeSM;

    @JoinColumn(name = "condominium_id")
    @ManyToOne
    private Condominium condominium;

    @JoinColumn(name = "resident_id")
    @ManyToOne
    private Resident resident;
    
    public long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getSizeSM() {
        return sizeSM;
    }

    public void setSizeSM(double sizeSM) {
        this.sizeSM = sizeSM;
    }

    public Condominium getCondominium() {
        return condominium;
    }

    public void setCondominium(Condominium condominium) {
        this.condominium = condominium;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }
}
