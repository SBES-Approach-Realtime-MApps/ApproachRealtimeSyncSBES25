package br.unesp.rc.ReservationModel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.ReservationModel.model.Reservation;
import br.unesp.rc.ReservationModel.model.Resident;



public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByResident(Resident resident);
}
