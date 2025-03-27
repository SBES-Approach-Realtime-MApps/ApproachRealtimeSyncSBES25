package br.unesp.rc.MSReservation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.MSReservation.model.Reservation;
import br.unesp.rc.MSReservation.model.Resident;
import br.unesp.rc.MSReservation.repository.ReservationRepository;
import br.unesp.rc.MSReservation.repository.ResidentRepository;

@Service
public class ReservationService {
    
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ResidentRepository residentRepository;

    public Reservation save(Reservation reservation) {
        Reservation newReservation = reservationRepository.save(reservation);
        return newReservation;
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation findById(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            throw new RuntimeException("Reservation not found");
        }
        return optionalReservation.get();
    }

    public List<Reservation> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations;
    }

    public List<Reservation> findByResident(Long idResident) {
        Resident resident = residentRepository.findById(idResident).get();
        return reservationRepository.findByResident(resident);
    }

    public Reservation update(Reservation reservation) {
        Reservation oldReservation = findById(reservation.getId());
        updateReservation(oldReservation, reservation);
        Reservation updatedReservation = reservationRepository.save(reservation); 
        return updatedReservation;
    }

    public void updateReservation(Reservation oldReservation, Reservation newReservation){
        if (newReservation.getArea() != null) {
            oldReservation.setArea(newReservation.getArea());
        }

        if (newReservation.getResident() != null) {
            oldReservation.setResident(newReservation.getResident());
        }

        if (newReservation.getStartDate() != null) {
            oldReservation.setStartDate(newReservation.getStartDate());
        }

        if (newReservation.getEndDate() != null) {
            oldReservation.setEndDate(newReservation.getEndDate());
        }

        if (newReservation.getDescription() != null) {
            oldReservation.setDescription(newReservation.getDescription());
        }

        oldReservation.setActive(newReservation.getActive());
    }
}
