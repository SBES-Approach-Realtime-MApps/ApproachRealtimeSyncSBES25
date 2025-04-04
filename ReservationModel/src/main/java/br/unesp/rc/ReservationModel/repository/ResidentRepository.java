package br.unesp.rc.ReservationModel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.ReservationModel.model.Resident;



public interface ResidentRepository extends JpaRepository<Resident, Long> {
    
}
