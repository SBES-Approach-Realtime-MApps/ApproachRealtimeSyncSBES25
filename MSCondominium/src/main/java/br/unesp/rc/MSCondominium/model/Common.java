package br.unesp.rc.MSCondominium.model;

import jakarta.persistence.Entity;

@Entity
public class Common extends Area{
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
