package br.unesp.rc.CondominiumModel.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Resident {

    @Id
    private String id;
    private String name;

    @Column(name = "birth_date", columnDefinition = "DATE")
    private Date birthDate;
    private ResidentType residentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public ResidentType getResidentType() {
        return residentType;
    }
    public void setResidentType(ResidentType residentType) {
        this.residentType = residentType;
    }

    
}
