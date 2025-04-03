package br.unesp.rc.CondominiumModel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.CondominiumModel.model.RentableArea;


public interface RentableAreaRepository extends JpaRepository<RentableArea, Long> {
    
}
