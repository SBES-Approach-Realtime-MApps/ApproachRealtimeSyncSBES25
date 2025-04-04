package br.unesp.rc.ReservationModel.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JoinColumn(name = "area_id")
    @OneToOne(cascade = CascadeType.ALL)
    private RentableArea area;

    @JoinColumn(name = "resident_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Resident resident;

    @Column(name = "start_date", columnDefinition = "DATE")
    private Date startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private Date endDate;
    private boolean active;
    private String description;

    @Transient
    private Notification notification;

    public RentableArea getArea() {
        return area;
    }

    public void setArea(RentableArea area) {
        this.area = area;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getActive(){
        return active; 
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public long getId() {
        return id;
    }

    
}
