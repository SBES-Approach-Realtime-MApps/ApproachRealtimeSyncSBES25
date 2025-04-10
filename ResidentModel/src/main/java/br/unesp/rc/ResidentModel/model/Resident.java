package br.unesp.rc.ResidentModel.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "resident")
public class Resident extends Person {

    public Resident() {
        super();
    }

    private ResidentType residentType;
               

    public ResidentType getResidentType() {        
        return residentType;
    }

    public void setResidentType(ResidentType residentType) {
        this.residentType = residentType;
    }

    @Override
    public String toString() {
        return "Resident {\n\tid=" + super.getId() + "\n\tname=" + super.getName() + "\n\tbirthDate=" + super.getBirthDate() + "\n\tresidentType=" + residentType + "\n\tcontact=" + super.getContact() + "\n\taddress=" + super.getAddress() + "\n}";
    }
}
