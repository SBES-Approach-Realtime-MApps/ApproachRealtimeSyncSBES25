package br.unesp.rc.CondominiumModel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unesp.rc.CondominiumModel.model.RentableArea;

@Repository("condominiumRentableAreaRepository")
public interface RentableAreaRepository extends JpaRepository<RentableArea, Long> {
    
}
