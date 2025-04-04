package br.unesp.rc.ReservationModel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.ReservationModel.model.RentableArea;


public interface RentableAreaRepository extends JpaRepository<RentableArea, Long> {
    
}
