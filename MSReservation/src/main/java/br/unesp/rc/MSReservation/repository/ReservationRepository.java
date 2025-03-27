package br.unesp.rc.MSReservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.MSReservation.model.Reservation;
import br.unesp.rc.MSReservation.model.Resident;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByResident(Resident resident);
}
