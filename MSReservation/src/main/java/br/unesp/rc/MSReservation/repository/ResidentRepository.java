package br.unesp.rc.MSReservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.MSReservation.model.Resident;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    
}
