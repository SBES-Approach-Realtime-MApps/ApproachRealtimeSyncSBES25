package br.unesp.rc.MSCondominium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.MSCondominium.model.RentableArea;

public interface RentableAreaRepository extends JpaRepository<RentableArea, Long> {
    
}
