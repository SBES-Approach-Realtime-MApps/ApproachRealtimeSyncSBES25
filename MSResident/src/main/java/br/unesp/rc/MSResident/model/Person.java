package br.unesp.rc.MSResident.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;



public class Person {

    @Id
    private String id;

    private String name;

    private Date birthDate;

    private Contact contact;

    private List<Address> address;

    public String getId() {
        return id;
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
    public Contact getContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }
   
    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    
}
