package br.unesp.rc.ReservationModel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unesp.rc.ReservationModel.model.RentableArea;


@Repository("reservationRentableAreaRepository")
public interface RentableAreaRepository extends JpaRepository<RentableArea, Long> {
    
}
