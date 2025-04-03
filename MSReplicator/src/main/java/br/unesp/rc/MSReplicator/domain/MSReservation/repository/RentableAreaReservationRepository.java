package br.unesp.rc.MSReplicator.domain.MSReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.MSReplicator.domain.MSReservation.model.RentableArea;

public interface RentableAreaReservationRepository extends JpaRepository<RentableArea, Long> {
    
}
