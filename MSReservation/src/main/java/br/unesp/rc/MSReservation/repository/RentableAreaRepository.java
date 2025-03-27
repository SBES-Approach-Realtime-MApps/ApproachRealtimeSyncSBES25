package br.unesp.rc.MSReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.MSReservation.model.RentableArea;

public interface RentableAreaRepository extends JpaRepository<RentableArea, Long> {
    
}
