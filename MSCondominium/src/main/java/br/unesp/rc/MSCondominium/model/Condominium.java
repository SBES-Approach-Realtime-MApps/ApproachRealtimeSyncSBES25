package br.unesp.rc.MSCondominium.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Condominium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private CondominiumType condominiumType;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CondominiumType getCondominiumType() {
        return condominiumType;
    }

    public void setCondominiumType(CondominiumType condominiumType) {
        this.condominiumType = condominiumType;
    }
    
}
