package br.unesp.rc.MSResident.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resident")
public class Resident extends Person {

    private ResidentType residentType;
               

    public ResidentType getResidentType() {        
        return residentType;
    }

    public void setResidentType(ResidentType residentType) {
        this.residentType = residentType;
    }
}
