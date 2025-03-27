package br.unesp.rc.MSCondominium.model;

import jakarta.persistence.Entity;

@Entity
public class Specific extends Area{
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
